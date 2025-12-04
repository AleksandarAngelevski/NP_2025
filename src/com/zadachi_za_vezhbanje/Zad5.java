package zadachi_za_vezhbanje;
import java.util.Scanner;
class MinMax<T extends Comparable<T>> {
    T min;
    T max;
    int count=0;
    int minCount=0;
    int maxCount=0;
    MinMax(){}
    void update(T elements){
        if(min == null || min.compareTo(elements) >=1){
            if(min!=null && min.equals(max)){
                maxCount+=minCount;
            }
            min=elements;
            minCount=0;
        }
        if(max == null || max.compareTo(elements) <=-1){
            if(max != null && max.equals(min)){
                minCount+=maxCount;
            }
            max=elements;
            maxCount=0;
        }
        if(min.compareTo(elements)==0){
            minCount++;
        }else if(max.compareTo(elements)==0){
            maxCount++;
        }
        count++;

    }
    T max(){
        return this.max;
    }
    T man(){
        return this.min;
    }

    @Override
    public String toString() {
        return this.min+" "+this.max+" "+(this.count-(this.minCount+this.maxCount))+"\n";
    }
}

public class Zad5 {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}