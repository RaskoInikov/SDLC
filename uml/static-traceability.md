# Трассировка статической структуры TaskFlow (ЛР6)

| Источник ЛР5 / SRS | Классы и операции | Компоненты | Развёртывание |
|---|---|---|---|
| UC-02, UC-10, UC-11; создание и валидация | TaskController.create, TaskService.create, TaskValidator.validate, TaskRepository.save | TaskController, TaskService, TaskValidator, TaskRepository | taskflow.jar, JVM, хранилище |
| UC-04, UC-09; завершение и повтор; REQ-FUNC-013 | TaskService.changeStatus/generateNext, TaskStatus, RecurrenceRule, TaskRepository.save | TaskController, TaskService, TaskRepository | taskflow.jar, JVM, хранилище |
| CRUD (SRS v0.2) | list, create, update, delete | TaskController, TaskService, TaskRepository | taskflow.jar, JVM, хранилище |
| Kanban (SRS v0.2), state-task | Task.status, TaskStatus, changeStatus | Web UI, TaskController, TaskService | браузер, taskflow.jar |
| CSV (SRS v0.2) | CsvExporter.export, TaskRepository.findAll | TaskController, CsvExporter, TaskRepository | taskflow.jar, JVM, хранилище |
| REQ-FUNC-012 | Отложено | — | — |