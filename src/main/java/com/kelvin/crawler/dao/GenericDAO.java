package com.kelvin.crawler.dao;

import com.kelvin.crawler.jpa.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;

public class GenericDAO<T> {

    protected EntityManager manager;

    private static GenericDAO instance;

    public GenericDAO() {
        manager = JPAUtil.getInstance().getEntityManager();
    }

    public static GenericDAO getInstance() {
        if (instance == null) {
            instance = new GenericDAO();
        }

        return instance;
    }

    public void insert(T entity) {
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();
            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            manager.getTransaction().rollback();
            manager.close();
        }
    }

    public T findByID(long id, T entity) {
        return manager.find((Class<T>) entity, id);
    }

    public List<T> findAll(Class t) {
        return manager.createQuery("FROM " + t.getSimpleName())
                .getResultList();
    }

    public void edit(T entity) {
        try {
            manager.getTransaction().begin();
            manager.merge(entity);
            manager.getTransaction().commit();
            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            manager.getTransaction().rollback();
        }
    }

    public void remove(T entity) {
        try {
            manager.getTransaction().begin();
            manager.remove(entity);
            manager.getTransaction().commit();
            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            manager.getTransaction().rollback();
        }
    }

    public void removeById(long id, T entity) {
        try {
            T dto = findByID(id, entity);
            manager.getTransaction().begin();
            manager.remove(dto);
            manager.getTransaction().commit();
            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            manager.getTransaction().rollback();
        }
    }
}
