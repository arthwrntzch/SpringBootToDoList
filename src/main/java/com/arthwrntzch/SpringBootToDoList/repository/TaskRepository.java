package com.arthwrntzch.SpringBootToDoList.repository;

import com.arthwrntzch.SpringBootToDoList.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(task);
            session.getTransaction().commit();
        }
    }

    public void updateTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(task);
            session.getTransaction().commit();
        }
    }

    public void deleteTask(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Task task = session.find(Task.class, id);
            session.remove(task);
            session.getTransaction().commit();
        }
    }

    public Task getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Task.class, id);
        }
    }

    public List<Task> getAllTasks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Task").list();
        }
    }
}
