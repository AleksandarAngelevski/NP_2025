package laboratoriski.lab3;
import java.io.*;
import java.util.*;
import java.util.Arrays;
class Movie{
    private String title;
    private String genre;
    private int year;
    private double avgRating;
    Movie(){}

    public Movie(String title, String genre, int year, double avgRating) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.avgRating = avgRating;
    }


    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public double getAvgRating() {
        return avgRating;
    }

    @Override
    public String toString() {
        return this.title+", "+this.genre+", "+this.year+", "+String.format("%.2f",this.avgRating);
    }
}
class MovieTheater{
    private ArrayList<Movie> movies;

    void readMovies(InputStream is) throws IOException{
        if(movies==null)
            movies = new ArrayList<Movie>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Integer number=Integer.valueOf(br.readLine());
        for(int i =0;i<number;i++){
            String title = br.readLine();
            String genre = br.readLine();
            int year = Integer.valueOf(br.readLine());
            String[] ratings = br.readLine().split(" ");
            double rating = Arrays.stream(ratings).mapToDouble(Double::parseDouble).sum()/ ratings.length;
            Movie m = new Movie(title,genre,year,rating);
            movies.add(m);
            // System.out.println(m);
        }

    }

    void printByGenreAndTitle(){
//        Comparator<Movie> compareByGenre = new Comparator<Movie>() {
//            @Override
//            public int compare(Movie o1, Movie o2) {
//                return o1.getGenre().compareTo(o2.getGenre());
//            }
//        };
//        Comparator<Movie> compareByTitle = (o1, o2) -> {
//            int genreCompare = compareByGenre.compare(o1,o2);
//            if(genreCompare != 0){
//                return genreCompare;
//            }
//            return o1.getTitle().compareTo(o2.getTitle());
//        };
        ArrayList<Movie> sortedByTitleAndGenre = new ArrayList<Movie>(this.movies);
        sortedByTitleAndGenre.sort(Comparator.comparing(Movie::getGenre).thenComparing(Movie::getTitle));
        for(Movie m : sortedByTitleAndGenre){
            System.out.println(m);
        }

    }
    void printByYearAndTitle() {
        ArrayList<Movie> sortedByYearAndTitle = new ArrayList<Movie>(this.movies);
        sortedByYearAndTitle.sort(Comparator.comparing(Movie::getYear).thenComparing(Movie::getTitle));
        for(Movie m : sortedByYearAndTitle){
            System.out.println(m);
        }
    }
    void printByRatingAndTitle(){
        ArrayList<Movie> sortedByRatingAndTitle = new ArrayList<Movie>(this.movies);
        sortedByRatingAndTitle.sort(Comparator.comparing(Movie::getAvgRating).reversed().thenComparing(Movie::getTitle));
        for(Movie m : sortedByRatingAndTitle){
            System.out.println(m);
        }
    }


}
public class Zad1 {
    public static void main(String[] args) {
        MovieTheater mt = new MovieTheater();
        try {
            mt.readMovies(System.in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("SORTING BY RATING");
        mt.printByRatingAndTitle();
        System.out.println("\nSORTING BY GENRE");
        mt.printByGenreAndTitle();
        System.out.println("\nSORTING BY YEAR");
        mt.printByYearAndTitle();
    }
}