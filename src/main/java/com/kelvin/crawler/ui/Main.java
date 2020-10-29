package com.kelvin.crawler.ui;

import com.kelvin.crawler.dao.BasicArticle;
import com.kelvin.crawler.entities.Article;

public class Main {

    public static void main(String[] args) {
        //Date dataAtual = Calendar.getInstance().getTime();
        //Article article = new Article("wendel", "kkkk.", dataAtual);
        Article art2 = BasicArticle.getInstance().findByID(7);
        art2.setTitle("Erick");
        BasicArticle.getInstance().edit(art2);
        //List<Article> articles = BasicArticle.getInstance().findAll();
//
//        manager.getTransaction().begin();
//        manager.persist(article);
//        manager.getTransaction().commit();
//        System.out.println("ID:" + article.getID());
    }
}
