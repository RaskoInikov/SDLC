# TaskFlow — Software Design Description (дополнение ЛР5)

**Статус:** проектное дополнение к SRS v0.2; не утверждает, что код уже реализован.

## 1. Архитектурный контекст
Монолитное Web-приложение: интерфейс → Java/Spring Boot HTTP API → сервис задач → репозиторий и постоянное хранилище. Разделение логическое, не микросервисное. SRS задаёт HTTP методы GET/POST/PUT/DELETE/PATCH и JSON.

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
Task: id, title, description, priority, status, dueDate, createdAt, updatedAt, recurrenceRule. Первые восемь полей заданы SRS, recurrenceRule добавлен требованием REQ-FUNC-013. Для защиты от повторного создания экземпляра рекомендуется дополнительная связь предыдущей и следующей задачи либо уникальный ключ операции завершения; точный способ не определён SRS.

## 5. Трассировка
См. `../uml/traceability.md`. Требование REQ-FUNC-012 о сортировке отложено после версии 1.0 и не моделируется.
