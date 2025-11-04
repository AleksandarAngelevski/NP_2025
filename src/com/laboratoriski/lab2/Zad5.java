package laboratoriski.lab2;


import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

class Timestamp<T> implements Comparable<Timestamp<?>>{
    private final LocalDateTime time;
    private final T element;

    Timestamp(LocalDateTime time, T element){
        this.time = time;
        this.element = element;
    }
    public LocalDateTime getTime(){
        return this.time;
    }
    public T getElement(){
        return this.element;
    }



    public int compareTo(Timestamp<?> t){
        if(this.time.isAfter(t.getTime())){
            return 1;
        }else if(this.time.isBefore(t.getTime())){
            return -1;
        }
        return 0;
    }
    public boolean equals(Object o){
        Timestamp<?> t=(Timestamp<?>)o;

        return this.getTime().isEqual(t.getTime());
    }

    @Override
    public String toString() {
        return this.time.toString() +" "+ this.element.toString();
    }
}
class Scheduler<T>{
    ArrayList<Timestamp<T>> list = new ArrayList<>();
    Scheduler(){

    }
    public void add(Timestamp<T> t){
        this.list.add(t);
    }
    public boolean remove(Timestamp<T> t){
        return this.list.remove(t);
    }
    public Timestamp<T> next(){
        LocalDateTime now = LocalDateTime.now();
        Timestamp<T> t = this.list.get(0);
        for(Timestamp<T> e : this.list){
            if(e.getTime().isBefore(t.getTime()) && e.getTime().isAfter(now) || t.getTime().isBefore(now) && e.getTime().isAfter(now)){
//                System.out.println("Current later time" + t.getTime());
//                System.out.println("Later time" + e.getTime());
//                System.out.println("Current time" + now);
                t=e;
            }
        }
        return t;
    }
    public Timestamp<T> last(){
        LocalDateTime now = LocalDateTime.now();
        Timestamp<T> t = this.list.get(0);
        for(Timestamp<T> e : this.list){
            if(e.getTime().isAfter(t.getTime()) && e.getTime().isBefore(now) || e.getTime().isBefore(t.getTime()) && t.getTime().isAfter(now)){
                t=e;
            }
        }
        return t;
    }
    public List<Timestamp<T>> getAll(LocalDateTime begin, LocalDateTime end){
        List<Timestamp<T>> list = this.list.stream().filter((e)->
            e.getTime().isAfter(begin) && e.getTime().isBefore(end)
        ).toList();
        return list;
    }
}



public class Zad5 {

    static final LocalDateTime TIME = LocalDateTime.of(2016, 10, 25, 10, 15);

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Timestamp with String
            Timestamp<String> t = new Timestamp<>(TIME, jin.next());
            System.out.println(t);
            System.out.println(t.getTime());
            System.out.println(t.getElement());
        }
        if (k == 1) { //test Timestamp with ints
            Timestamp<Integer> t1 = new Timestamp<>(TIME, jin.nextInt());
            System.out.println(t1);
            System.out.println(t1.getTime());
            System.out.println(t1.getElement());
            Timestamp<Integer> t2 = new Timestamp<>(TIME.plusDays(10), jin.nextInt());
            System.out.println(t2);
            System.out.println(t2.getTime());
            System.out.println(t2.getElement());
            System.out.println(t1.compareTo(t2));
            System.out.println(t2.compareTo(t1));
            System.out.println(t1.equals(t2));
            System.out.println(t2.equals(t1));
        }
        if (k == 2) {//test Timestamp with String, complex
            Timestamp<String> t1 = new Timestamp<>(ofEpochMS(jin.nextLong()), jin.next());
            System.out.println(t1);
            System.out.println(t1.getTime());
            System.out.println(t1.getElement());
            Timestamp<String> t2 = new Timestamp<>(ofEpochMS(jin.nextLong()), jin.next());
            System.out.println(t2);
            System.out.println(t2.getTime());
            System.out.println(t2.getElement());
            System.out.println(t1.compareTo(t2));
            System.out.println(t2.compareTo(t1));
            System.out.println(t1.equals(t2));
            System.out.println(t2.equals(t1));
        }
        if (k == 3) { //test Scheduler with String
            Scheduler<String> scheduler = new Scheduler<>();
            LocalDateTime now = LocalDateTime.now();
            scheduler.add(new Timestamp<>(now.minusHours(2), jin.next()));
            scheduler.add(new Timestamp<>(now.minusHours(1), jin.next()));

            scheduler.add(new Timestamp<>(now.minusHours(4), jin.next()));
            scheduler.add(new Timestamp<>(now.plusHours(2), jin.next()));
            scheduler.add(new Timestamp<>(now.plusHours(4), jin.next()));
            scheduler.add(new Timestamp<>(now.plusHours(1), jin.next()));
            scheduler.add(new Timestamp<>(now.plusHours(5), jin.next()));
            System.out.println(scheduler.next().getElement());
            System.out.println(scheduler.last().getElement());

            System.out.println("____");
            System.out.println(now);
            System.out.println(scheduler.next().getTime());
            System.out.println(scheduler.last().getTime());
            List<Timestamp<String>> result = scheduler.getAll(now.minusHours(3), now.plusHours(4).plusMinutes(15));
            String out = result.stream()
                    .sorted()
                    .map(Timestamp::getElement)
                    .collect(Collectors.joining(", "));
            System.out.println(out);
        }
        if (k == 4) {//test Scheduler with ints complex
            Scheduler<Integer> scheduler = new Scheduler<>();
            int counter = 0;
            ArrayList<Timestamp<Integer>> forRemoval = new ArrayList<>();
            while (jin.hasNextLong()) {
                Timestamp<Integer> ti = new Timestamp<>(ofEpochMS(jin.nextLong()), jin.nextInt());
                if ((counter & 7) == 0) {
                    forRemoval.add(ti);
                }
                scheduler.add(ti);
                ++counter;
            }
            jin.next();

            while (jin.hasNextLong()) {
                LocalDateTime left = ofEpochMS(jin.nextLong());
                LocalDateTime right = ofEpochMS(jin.nextLong());
                List<Timestamp<Integer>> res = scheduler.getAll(left, right);
                Collections.sort(res);
                System.out.println(left + " <: " + print(res) + " >: " + right);
            }
            System.out.println("test");
            List<Timestamp<Integer>> res = scheduler.getAll(ofEpochMS(0), ofEpochMS(Long.MAX_VALUE));
            Collections.sort(res);
            System.out.println(print(res));
            forRemoval.forEach(scheduler::remove);
            res = scheduler.getAll(ofEpochMS(0), ofEpochMS(Long.MAX_VALUE));
            Collections.sort(res);
            System.out.println(print(res));
        }
    }

    private static LocalDateTime ofEpochMS(long ms) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(ms), ZoneId.systemDefault());
    }

    private static <T> String print(List<Timestamp<T>> res) {
        if (res == null || res.size() == 0) return "NONE";
        return res.stream()
                .map(each -> each.getElement().toString())
                .collect(Collectors.joining(", "));
    }

}

