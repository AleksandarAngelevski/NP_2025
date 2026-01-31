package designPatterns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Article{
    private String author;
    private String category;
    private String text;

    public Article(String author, String category, String text) {
        this.author = author;
        this.category = category;
        this.text = text;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getCategory(){
        return this.category;
    }
    public void print(){
        System.out.println(this.text);
    }
}
class User{
    private ArrayList<Article> articles;
    private String username;
    private Set<String> authors;
    private Set<String> categories;
    User(String username){
        this.username = username;
        this.articles = new ArrayList<>();
        this.authors = new HashSet<>();
        this.categories = new HashSet<>();
    }

    public void updateArticles(Article a){
//        System.out.println(this.categories.contains(a.getCategory()));
//        System.out.println(a.getCategory());
        if(this.authors.contains(a.getAuthor()) || this.categories.contains(a.getCategory())){
            this.articles.add(a);
        }
    }
    public void printArticles(){
//        System.out.println("Printing user articles");
//        System.out.println(this.articles.size());
        for( Article a : this.articles){
            a.print();
        }
    }
    public void subscribeAuthor(String author){
//        System.out.println("Adding author:"+ author);
        this.authors.add(author);
    }
    public void subscribeCategory(String category){
//        System.out.println("Adding category:"+ category);
        this.categories.add(category);
    }
    public void unsubscribeAuthor(String author){
        this.authors.remove(author);
    }
    public void unsubscribeCategory(String category){
        this.categories.remove(category);
    }

}

class Agency{
    private List<User> users = new ArrayList<>();
    private List<Article> articles = new ArrayList<>();

    Agency(){
        this.users = new ArrayList<>();
        this.articles = new ArrayList<>();
    }
    public void addUser(User user){
        this.users.add(user);
    }
    public void addArticle(Article a){
        this.articles.add(a);
        this.users.forEach(u -> u.updateArticles(a));
    }

}

public class Observer {
    public static void main(String[] args) {
        Agency agency = new Agency();

        // Create users
        User john = new User("John");
        User mary = new User("Mary");

        // Subscriptions
        john.subscribeAuthor("Alice");
        mary.subscribeCategory("Science");

        // Add users to agency
        agency.addUser(john);
        agency.addUser(mary);

        // Create articles
        Article article1 = new Article( "Alice", "Programming", "Python 101");
        Article article2 = new Article("Bob", "Science","SpaceX Launch" );

        // Add articles to agency
        agency.addArticle(article1);
        agency.addArticle(article2);

        // Print articles for users
        john.printArticles(); // Should show "Python 101"
        mary.printArticles(); // Should show "SpaceX Launch"
    }
}
