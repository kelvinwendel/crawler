package com.kelvin.crawler.business;

import com.kelvin.crawler.entities.Article;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.kelvin.crawler.utilities.TypesEnum;
import java.util.Calendar;
import java.util.Date;

public class BasicCrawler {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64)"
            + "AppleWebKit/535.1 (KHTML, like Gecko) "
            + "Chrome/13.0.782.112 Safari/535.1";

    private static final String FILES_DESTINATION = "C:/files";

    private HashSet<String> links;

    private List<Article> articles;

    private List<String> frontiers;

    private TypesEnum type;

    private String evaluator;

    private String attributeKey;

    public BasicCrawler() {
        this.links = new HashSet<>();
        this.articles = new ArrayList<>();
        this.frontiers = new ArrayList<>();
    }

    public void addFrontier(String frontier) {
        frontiers.add(frontier);
    }

    public void crowlPage(String url, int depth) throws Exception {
        if (!links.contains(url) && depth > 0 && !url.trim().isEmpty()) {
            try {
                links.add(url);
                Document document = Jsoup.connect(url).userAgent(USER_AGENT)
                        .timeout(10000)
                        .get();

                switch (type) {
                    case LINK:
                        evaluator = "a[href]";
                        attributeKey = "abs:href";
                        break;

                    case IMAGE:
                        evaluator = "img";
                        attributeKey = "abs:src";
                        break;

                    default:
                        throw new Exception("Formato não suportado");
                }

                Elements elements = document.select(evaluator);

                for (Element page : elements) {
                    crowlPage(page.attr(attributeKey), --depth);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void crowlArticles() {
        links.forEach((url) -> {
            Document document;
            String title;
            try {
                document = Jsoup.connect(url).userAgent(USER_AGENT).get();
                Elements linksOnPage = document.select(evaluator);
                linksOnPage.forEach((element) -> {
                    Date dataAtual = Calendar.getInstance().getTime();
                    Article article = new Article(element.text(),
                            element.attr(attributeKey));
                    articles.add(article);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void crowl(int depth, TypesEnum type) {
        this.type = type;
        frontiers.forEach(url -> {
            try {
                crowlPage(url, depth);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        crowlArticles();

        switch (type) {
            case IMAGE:
                downloadData();
                break;
            case LINK:
                print();
                break;
        }
    }

    public void print() {
        articles.forEach((article) -> {
            System.out.println("Title: " + article.title);
            System.out.println("Url: " + article.url);
        });
    }

    public void downloadData() {
        articles.forEach((data) -> {
            String fileName = data.url.substring(data.url.lastIndexOf("/") + 1);
            System.out.println("Saving: " + fileName + "from:" + data.url);

            try {
                URLConnection connection = new URL(data.url).openConnection();
                connection.addRequestProperty("User-Agent", USER_AGENT);

                InputStream in = connection.getInputStream();

                byte[] buffer = new byte[4096];
                int n = -1;

                OutputStream out = new FileOutputStream(FILES_DESTINATION + "/"
                        + fileName);

                while ((n = in.read(buffer)) != -1) {
                    out.write(buffer, 0, n);
                }

                out.close();
                System.out.println("File Saved");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
