package laboratoriski.lab5;
import javax.sound.midi.MetaMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
class Pair{
    private Integer left;
    private List<Member> right;
    public Pair(Integer left, List<Member> borrowedMembers) {
        this.left = left;
        this.right = borrowedMembers;
    }
    public Integer getLeft() {
        return this.left;
    }
    public List<Member> getRight() {
        return this.right;
    }
    public void decreaseRight(Member m ) {
        this.right.remove(m);
    }
    void increaseLeft(){
        this.left++;
    }
    void increaseRight(Member m){
        if(right.contains(m)){
            System.out.println("Book Already borrowed, return first!");
        }else{
            this.right.add(m);
        }

    }
}
class Book{
    private String isbn;
    private String title;
    private int publishDate;
    private String author;
    private Integer quantity;
    private Integer borrowedAmount;
    private Integer total;
    Book(String isbn, String title, int publishDate,String author){
        this.isbn = isbn;
        this.title = title;
        this.publishDate = publishDate;
        this.author = author;
        this.quantity = 1;
        this.borrowedAmount = 0;
        this.total=0;
    }
    String getIsbn() {
        return this.isbn;
    }
    public int get_total_borrowed(){
        return this.borrowedAmount;
    }
    public String getTitle() {
        return title;
    }
    public int get_total(){
        return this.total;
    }
    public void increaseQuantity(){
        this.quantity++;

    }
    public void takeBook() {
        this.borrowedAmount++;
        this.total++;
    }
    public void releaseBook(){
        this.borrowedAmount--;
    }
    @Override
    public boolean equals(Object obj) {
        return this.isbn.equals(((Book)obj).isbn);
    }
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
    public int getPublishDate() {
        return publishDate;
    }
    public String getAuthor() {
        return this.author;
    }
    @Override
    public String toString(){
        // B300 - "Solaris" by Lem (1961), available: 0, total borrows: 1
        return String.format("%s - \"%s\" by %s (%d), available: %d, total borrows: %d", this.isbn, this.title,this.author,this.publishDate, (this.quantity-this.borrowedAmount), this.borrowedAmount);
    }

}
class Member {
    private String id;
    private String name;
    private Integer borrowed_current;
    private Integer total_borrowed;
    private ArrayList<Book> borrowedBooks;
    Member(String id, String name){
        this.id = id;
        this.name = name;
        borrowedBooks = new ArrayList<>();
        borrowed_current = 0;
        total_borrowed = 0;
    }
    String getId(){
        return this.id;
    }


    public void borrowBook(){
        this.borrowed_current++;
        this.total_borrowed++;
    }
    public void returnBook(){
        this.borrowed_current--;
    }
    public Integer getBB(){
        return this.borrowed_current;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
//        Gorazd (id27) - borrowed now: 5, total borrows: 17
        return String.format("%s (%s) - borrowed now: %d, total borrows: %d", this.name, this.id,this.borrowed_current,this.total_borrowed);
    }
}

class LibrarySystem{
    private String name;
    private List<Member> members;
    private Map<Book,Pair> books;
    private List<Book> booksList;
    private Map<Book,Queue<Member>> bookQueue;
    LibrarySystem(String name){
        this.name = name;
        members = new ArrayList<>();
        books = new HashMap<>();
        booksList = new ArrayList<>();
        bookQueue = new HashMap<>();
    }
    void registerMember(String id,String fullName){
        Member member = new Member(id,fullName);
        members.add(member);
    }
    void addBook(String isbn,String title, String author, int year){
        Book b = new Book(isbn,title,year,author);

        if(this.books.containsKey(b)){
            b = this.booksList.stream().filter(bb -> bb.getIsbn().equals(isbn)).findFirst().get();
            Pair tmp = this.books.get(b);
            tmp.increaseLeft();
            b.increaseQuantity();
            this.books.put(b,tmp);

        }else{
            this.booksList.add(b);
            this.books.put(b,new Pair(1,new  ArrayList<>()));

        }
    }
    void borrowBook(String memberId, String isbn){
        Book btemp = this.booksList.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().get();

        if(this.books.containsKey(btemp)){

            // System.out.println("BORROWING");
            Book tb =this.books.keySet().stream().filter(b->b.getIsbn().equals(isbn)).findFirst().get();
            Pair qb = this.books.get(btemp);
            Member m = this.members.stream().filter(t->t.getId().equals(memberId)).findFirst().get();
            if(qb.getRight().size()>=qb.getLeft()){
                if(this.bookQueue.containsKey(btemp)){
                    this.bookQueue.get(btemp).add(m);
                }else{
                    this.bookQueue.put(tb,new LinkedList<>(Arrays.asList(m)));

                }
            }else{
                this.books.get(tb).increaseRight(m);
                btemp.takeBook();
                m.borrowBook();
            }
        }
    }

    void returnBook(String memberId, String isbn) {
        Member m = this.members.stream().filter(t -> t.getId().equals(memberId)).findFirst().get();
        Book tb =this.books.keySet().stream().filter(b->b.getIsbn().equals(isbn)).findFirst().get();
        tb.releaseBook();
        this.books.get(tb).decreaseRight(m);
        m.returnBook();
        if(this.bookQueue.containsKey(tb)){
            Member tmp = this.bookQueue.get(tb).remove();
            tmp.borrowBook();
            tb.takeBook();
            this.books.get(tb).getRight().add(tmp); 

        }
    }

    void printMembers(){
        this.members.sort((a,b)->{
            if(a.getBB()==b.getBB()){
                return a.getName().compareTo(b.getName());
            }
            return a.getBB()<b.getBB()?1:-1;
        });
        this.members.forEach(System.out::println);
    }
    void printBooks(){
        List<Book> b = new ArrayList<>(this.books.keySet());
        b.sort((b1,b2)->{
            int borrowCompare = Integer.compare(this.books.get(b2).getRight().size(), this.books.get(b1).getRight().size());
            if (borrowCompare != 0) {
                return borrowCompare;
            } else {
                return Integer.compare(b1.getPublishDate(), b2.getPublishDate());
            }
        });
        b.forEach(System.out::println);


    }
    void printBookCurrentBorrowers(String isbn){
        Book btemp = this.booksList.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().get();

        List<Member> s = this.books.get(btemp).getRight();
        s.sort(Comparator.comparing(Member::getId));
        String k = "";
        for(int i=0;i<s.size();i++){
            k+=s.get(i).getId();
            if(i+1<s.size()){
                k+=", ";
            }
        }
        System.out.println(k);
    }

    void printTopAuthors(){
        Map<String, Integer> authorBorrows = new HashMap<>();


        for (Book book : this.books.keySet()) {
            int totalBorrows = book.get_total();
            authorBorrows.put(book.getAuthor(),authorBorrows.getOrDefault(book.getAuthor(),0) + totalBorrows);
        }
        authorBorrows.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(b.getValue(), a.getValue());
                    if (cmp != 0) return cmp;
                    return a.getKey().compareTo(b.getKey());
                })
                .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
    }


}

public class LibraryTester {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String libraryName = br.readLine();
            //   System.out.println(libraryName); //test
            if (libraryName == null) return;

            libraryName = libraryName.trim();
            LibrarySystem lib = new LibrarySystem(libraryName);

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.equals("END")) break;
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");

                switch (parts[0]) {

                    case "registerMember": {
                        lib.registerMember(parts[1], parts[2]);
                        break;
                    }

                    case "addBook": {
                        String isbn = parts[1];
                        String title = parts[2];
                        String author = parts[3];
                        int year = Integer.parseInt(parts[4]);
                        lib.addBook(isbn, title, author, year);
                        break;
                    }

                    case "borrowBook": {
                        lib.borrowBook(parts[1], parts[2]);
                        break;
                    }

                    case "returnBook": {
                        lib.returnBook(parts[1], parts[2]);
                        break;
                    }

                    case "printMembers": {
                        lib.printMembers();
                        break;
                    }

                    case "printBooks": {
                        lib.printBooks();
                        break;
                    }

                    case "printBookCurrentBorrowers": {
                        lib.printBookCurrentBorrowers(parts[1]);
                        break;
                    }

                    case "printTopAuthors": {
                        lib.printTopAuthors();
                        break;
                    }

                    default:
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

