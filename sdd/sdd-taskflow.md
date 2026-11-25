# TaskFlow — Software Design Description (дополнение ЛР6)

**Статус:** проектная редакция ЛР6 на основе SRS v0.2 и SDD ЛР5. Не утверждает, что код уже реализован.

## 1. Архитектурный контекст
Монолитное Web-приложение: интерфейс → Java/Spring Boot HTTP API → сервис задач → репозиторий и постоянное хранилище. Разделение логическое, не микросервисное. SRS задаёт HTTP методы GET/POST/PUT/DELETE/PATCH и JSON. Экспорт возвращает CSV в UTF-8.

## 2. Поведенческие представления
- Варианты использования: `../uml/models/use-cases.puml`; спецификации — `../uml/use-case-model.md`.
- Создание задачи: `../uml/models/activity-create-task.puml`, `../uml/models/sequence-create-task.puml` (UC-02, UC-10, UC-11).
- Завершение задачи с повтором: `../uml/models/activity-complete-task.puml`, `../uml/models/sequence-complete-task.puml` (UC-04, UC-09).
- Состояния задачи: `../uml/models/state-task.puml` (TODO, IN_PROGRESS, DONE); `../uml/models/state-recurrence.puml` (процесс генерации повтора).

## 3. Компоненты
- `Web UI` — доска и формы, отображение результатов и ошибок.
- `TaskController` — HTTP API и DTO.
- `TaskValidator` — проверка входных данных и допустимости повторения.
- `TaskService` — создание, редактирование, изменение статуса, правило повтора.
- `TaskRepository` — постоянное хранение задач.
- `CsvExporter` — UTF-8 CSV из сохранённых задач.

## 4. Данные
Task: id, title, description, priority, status, dueDate, createdAt, updatedAt, recurrenceRule. Первые восемь полей заданы SRS, recurrenceRule добавлен REQ-FUNC-013. Проектные дополнения ЛР6: `previousTaskId` (необязательная ссылка на предшественника) и `recurrenceGenerated` (устойчивый маркер создания повтора). Маркер хранится на исходной задаче и не сбрасывается при удалении следующей задачи или повторном открытии исходной.

## 5. Трассировка
См. `../uml/traceability.md` (ЛР5) и `../uml/static-traceability.md` (ЛР6). REQ-FUNC-012 о сортировке отложено после версии 1.0 и не моделируется.

## 6. Диаграмма классов
![Диаграмма классов](../uml/images/class-diagram.png)

Исходники: [PlantUML](../uml/models/class-diagram.puml), [draw.io](../uml/models/class-diagram.drawio). Пакеты являются логическими именами, а не подтверждёнными Java package.

| Элемент | Назначение |
|---|---|
| web::TaskController | HTTP-операции и обращение к сервису и экспорту |
| task::TaskInput | Входные данные создания/изменения |
| task::TaskService | CRUD, изменение статуса, создание повтора |
| task::TaskValidator | Проверка входных данных |
| task::Task | Сущность и состояние |
| persistence::TaskRepository | Контракт хранения |
| persistence::PersistentTaskRepository | Проектная реализация контракта |
| export::CsvExporter | Формирование CSV |
| task::TaskStatus, RecurrenceRule, Priority | Перечисления |

Ассоциации показывают сохраняемые ссылки; зависимости «use» и «create» — использование типов и создание объектов; реализация — соответствие PersistentTaskRepository контракту TaskRepository. Самоассоциация Task обозначает не более одного предшественника. Искусственные наследование, агрегация и композиция не вводятся.

При завершении повторяющейся задачи сервис в одной транзакции блокирует или версионирует запись исходной задачи, проверяет прежний статус и маркер, переводит её в DONE, создаёт следующий экземпляр с новым id, статусом TODO и датой +1/+7 дней от исходной даты, устанавливает маркер. Повторное завершение не создаёт дубль. Для хранилища предусматривается ограничение уникальности previousTaskId среди непустых значений; удаление предшественника не должно каскадно удалять следующие задачи. Все записи откатываются при ошибке. Политика хранения связи при удалении предшественника (например, SET NULL) уточняется при реализации.

## 7. Диаграмма компонентов
![Диаграмма компонентов](../uml/images/component-diagram.png)

Исходники: [PlantUML](../uml/models/component-diagram.puml), [draw.io](../uml/models/component-diagram.drawio). Подсистема TaskFlow API содержит пять логических компонентов. Web UI использует REST; порт HTTP делегирует запросы контроллеру. Порт Storage делегирует доступ к хранилищу репозиторию.

| Компонент | Предоставляет | Требует |
|---|---|---|
| Web UI | Интерфейс пользователю | REST |
| TaskController | REST | TaskApi, CsvApi |
| TaskService | TaskApi | ValidationApi, RepositoryApi |
| TaskValidator | ValidationApi | — |
| TaskRepository | RepositoryApi | Storage API |
| CsvExporter | CsvApi | RepositoryApi |
| Persistent storage | Storage API | — |

Имена интерфейсов в этой диаграмме — логические контракты компонентов; не требуется вводить отдельный Java interface на каждый контракт.

## 8. Диаграмма развёртывания
![Диаграмма развёртывания](../uml/images/deployment-diagram.png)

Исходники: [PlantUML](../uml/models/deployment-diagram.puml), [draw.io](../uml/models/deployment-diagram.drawio). Проектное минимальное развёртывание: устройство пользователя с браузером и сервер с JVM/Spring Boot. `taskflow.jar` манифестирует подсистему TaskFlow API. Статические ресурсы Web UI доставляются браузеру приложением. Постоянное хранилище на сервере содержит данные задач. Тип СУБД, версию Java, порт и фактическое имя JAR следует сверить с репозиторием; Docker, внешний сервер уведомлений и микросервисы не предполагаются.

## 9. Согласованность и решения
Пакет web → TaskController; task → TaskService, TaskValidator; persistence → TaskRepository; export → CsvExporter. Все пять компонентов манифестируются одним JAR. См. [ADR-0002](../adr/0002-uml-static-structure.md). Модели ЛР5 остаются действующими; имена операций в источниках следует сверить при интеграции.

## 10. Допущения для проверки
Типы Long, LocalDate, Instant, значения LOW/MEDIUM/HIGH, NONE и имена операций — проектные предложения, не подтверждённые исходным кодом. Необязательная дата разрешена для неповторяющейся задачи; для DAILY/WEEKLY требуется дата. Переходы статуса должны соответствовать автомату ЛР5. План ЛР4 и репозиторий не предоставлены, поэтому выполнение календарного плана и публикация проверяются отдельно.
