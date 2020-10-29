package com.kelvin.crawler.dao;

import com.kelvin.crawler.entities.Article;
import java.util.List;

public class BasicArticle {

    private static BasicArticle instance;

    public BasicArticle() {

    }

    public static BasicArticle getInstance() {
        if (instance == null) {
            instance = new BasicArticle();
        }

        return instance;
    }

    public Article findByID(long id) {
        return (Article) GenericDAO.getInstance().findByID(id, Article.class);
    }

    public List<Article> findAll() {
        return GenericDAO.getInstance().findAll(Article.class);
    }

    public void insert(Article article) {
        try {
            GenericDAO.getInstance().insert(article);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void edit(Article article) {
        try {
            GenericDAO.getInstance().edit(article);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Article article) {
        try {
            GenericDAO.getInstance().remove(article);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeByID(long id) {
        try {
            GenericDAO.getInstance().removeById(id, Article.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
