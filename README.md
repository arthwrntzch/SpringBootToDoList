# Spring Boot To-Do List API

Простое RESTful API для управления задачами и пользователями.  
Реализовано с использованием **Spring Boot 3**, **Spring Data JPA**, **Hibernate** и **Lombok**.

---

## 🚀 Возможности

- Создание пользователей
- Создание, обновление и удаление задач
- Привязка задач к пользователям
- Получение всех задач или задач конкретного пользователя
- Работа со статусами задач (`TODO`, `IN_PROGRESS`, `COMPLETE`)

---

## 🛠 Технологии

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA (Hibernate)
- Lombok
- PostgreSQL

---

## ⚙️ Установка и запуск

### 1. Клонирование проекта
```bash
git clone https://github.com/username/springboot-todolist.git
cd springboot-todolist
```

### 2. Сборка и запуск
```bash
./mvnw spring-boot:run
```
или (если установлен Maven):
```bash
mvn spring-boot:run
```

### 3. Открытие в браузере
После запуска приложение будет доступно по адресу:  
```
http://localhost:8080
```

---

## 📂 Архитектура проекта

```
src/main/java/com/arthwrntzch/SpringBootToDoList
│
├── controller      # REST-контроллеры (TaskController, UserController)
├── dto             # DTO для задач (TaskDto)
├── entity          # Сущности JPA (Task, User)
├── enums           # Перечисления (TaskStatus)
├── repository      # Репозитории Spring Data JPA
├── service         # Сервисы (TaskService, UserService)
```

---

## 📌 REST API

### 👤 Пользователи

#### Создать пользователя
```http
POST /api/users
Content-Type: application/json
```
```json
{
  "id": 0
}
```

#### Получить всех пользователей
```http
GET /api/users
```

#### Получить пользователя по ID
```http
GET /api/users/{id}
```

---

### ✅ Задачи

#### Получить все задачи
```http
GET /api/tasks/list
```

#### Получить задачу по ID
```http
GET /api/tasks/{id}
```

#### Получить все задачи пользователя
```http
GET /api/tasks/user/{userId}
```

#### Создать задачу
```http
POST /api/tasks/create/task/{userId}
Content-Type: application/json
```
```json
{
  "name": "Купить продукты",
  "description": "Хлеб, молоко, овощи",
  "status": "TODO",
  "dueDate": "2025-10-05",
  "userId": 1
}
```

#### Обновить задачу
```http
PUT /api/tasks/{taskId}
Content-Type: application/json
```
```json
{
  "name": "Купить продукты и напитки",
  "description": "Хлеб, молоко, овощи, сок",
  "status": "IN_PROGRESS",
  "dueDate": "2025-10-07",
  "userId": 1
}
```

#### Удалить задачу
```http
DELETE /api/tasks/{taskId}
```

---

## 🗄 Статусы задач

В проекте определено перечисление `TaskStatus`:
- `TODO`
- `IN_PROGRESS`
- `COMPLETE`

---

## 📝 Лицензия
Этот проект создан для учебных целей. Можно свободно использовать и модифицировать.
