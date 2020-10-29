package com.kelvin.crawler.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    
    protected EntityManager entityManager;
    private static JPAUtil instance; 
    
    public static JPAUtil getInstance(){
        if(instance == null){
            instance = new JPAUtil();
        }
        
        return instance;
    }
    
    public EntityManager getEntityManager(){
        if(entityManager == null){
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("crawler");
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
}
