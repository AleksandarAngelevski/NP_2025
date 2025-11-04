package laboratoriski.lab2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;
class ResizableArray<T>{
    private T[] array;
    private int size;
    @SuppressWarnings("unchecked")
    ResizableArray(){
        array = (T[]) new Object[10];
        size = 0;
    }
    @SuppressWarnings("unchecked")
    public void addElement(T e){
        if(size == array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size++] = e;

    }
    public int size(){
        return this.size;
    }
    @SuppressWarnings("unchecked")
    public boolean removeElement(T e){
        if(size == 0){
            return false;
        }
        for(int i=0; i<size; i++){
            if(array[i].equals(e)){
                for(int j=i+1; j<size; j++){
                    array[j-1] = array[j];
                }
                size--;
                if(size == array.length/2){
                    T[] newArray = (T[]) new Object[size + (size/2)];
                    System.arraycopy(array, 0, newArray, 0, size);
                    array=newArray;
                }
                return true;
            }
        }
        return false;
    }
    public boolean contains(T element){
        for(int i=0; i<size; i++){
            if(array[i].equals(element)){
                return true;
            }
        }
        return false;
    }
    public T[] toArray(){
        return Arrays.copyOf(array, size);
    }
    public boolean isEmpty(){
        return this.size == 0;
    }
    public int count(){
        return this.size;
    }
    public T elementAt(int index) throws ArrayIndexOutOfBoundsException {
        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException("Index: "+ index +", is out of bounds");
        }
        return array[index];
    }
    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src){
        int size = src.size();
        for(int i=0; i<size; i++){
            dest.addElement(src.elementAt(i));
        }
    }
}

class IntegerArray extends ResizableArray<Integer>{

    public double sum(){
        double s =0;
        for(int i=0;i<this.size();i++){
            s+=this.elementAt(i).doubleValue();
        }
        return s;
    }
    public double mean(){
        return sum()/this.size();
    }
    public int countNonZero(){
        int count = 0;
        for(int i=0; i<this.size(); i++){
            if(this.elementAt(i).intValue() != 0){
                count++;
            }
        }
        return count;
    }
    public IntegerArray distinct(){
        IntegerArray d = new IntegerArray();
        for(int i=0; i<this.size(); i++){
            Integer tempInteger = this.elementAt(i);
            if(!d.contains(tempInteger)){
                d.addElement(tempInteger);
            }
        }
        return d;
    }
    public IntegerArray increment(int offset){
        IntegerArray d = new IntegerArray();
        for(int i=0; i<this.size(); i++){
            Integer tempInteger = this.elementAt(i);
            tempInteger = tempInteger+offset;
            d.addElement(tempInteger);
        }
        return d;
    }
}

public class Zad4 {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if ( test == 0 ) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while ( jin.hasNextInt() ) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if ( test == 1 ) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for ( int i = 0 ; i < 4 ; ++i ) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if ( test == 2 ) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while ( jin.hasNextInt() ) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if ( a.sum() > 100 )
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if ( test == 3 ) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for ( int w = 0 ; w < 500 ; ++w ) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k =  2000;
                int t =  1000;
                for ( int i = 0 ; i < k ; ++i ) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for ( int i = 0 ; i < t ; ++i ) {
                    a.removeElement(k-i-1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}
