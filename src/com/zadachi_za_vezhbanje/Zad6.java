package zadachi_za_vezhbanje;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}
interface Scalable{
    void scale(float sccaleFactor);
}
interface Stackable{
    float weight();
}
abstract class Shape1 implements Scalable, Stackable, Comparable<Shape1> {
    protected String id;
    protected Color color;
    Shape1(String id, Color color){
        this.id = id;
        this.color = color;
    }
    public String getId(){
        return this.id;
    }

    public Color getColor(){
        return this.color;
    }
}
class Circle1 extends Shape1{
    private float radius;
    Circle1(String id, Color color, float radius){
        super(id,color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        this.radius *= scaleFactor;
    }


    @Override
    public float weight() {
        return (float) (Math.PI* Math.pow(this.radius,2.0));
    }

    @Override
    public int compareTo(Shape1 shape1) {

        return Float.compare(this.weight(), shape1.weight());
    }

    @Override
    public String toString() {
//        C: [id:5 места од лево] [color:10 места од десно] [weight:10.2 места од десно] ако е круг

        return String.format("C: %-5s %10s %10.2f\n",this.getId(),this.getColor(),this.weight());
    }
}
class Rectangle extends Shape1{
    private float width;
    private float height;
    Rectangle(String id, Color color, float width, float height){
        super(id,color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float sccaleFactor) {
        this.width *= sccaleFactor;
        this.height *= sccaleFactor;
    }

    @Override
    public float weight() {
        return this.height*this.width;
    }
    @Override
    public int compareTo(Shape1 shape1) {

        return Float.compare(this.weight(), shape1.weight());
    }
//    R: [id:5 места од лево] [color:10 места од десно] [weight:10.2 места од десно] ако е правоаголник

    @Override
    public String toString() {
        return String.format("R: %-5s %10s %10.2f\n",this.getId(),this.getColor(),this.weight());
    }
}
class Canvas1 {
    List<Shape1> shapes;
    Canvas1(){
        this.shapes = new ArrayList<>();
    }
    void add(String id, Color color, float radius){
        Shape1 newShape = new Circle1(id,color,radius);
        insertShape(newShape);
    }
    void add(String id, Color color, float width, float height){
        Shape1 newShape = new Rectangle(id,color,width,height);
        insertShape(newShape);
    }
    void scale(String id, float scaleFactor){
        Shape1 temp = this.shapes.stream().filter(e -> e.getId().equals(id)).findFirst().get();
        this.shapes.remove(temp);
        temp.scale(scaleFactor);
        insertShape(temp);
    }
    void insertShape(Shape1 newShape){
        for(int i=0; i< this.shapes.size(); i++){
            if(newShape.compareTo(this.shapes.get(i))>0 || newShape.compareTo(this.shapes.get(i))==0 && newShape.getId().compareTo(this.shapes.get(i).getId())<=0){ // Sort by id i equal
                Shape1 temp = this.shapes.get(i);
                this.shapes.set(i,newShape);
                newShape = temp;

            }
        }
        this.shapes.add(newShape);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.shapes.forEach(e -> sb.append(e.toString()));
        return sb.toString();
    }
}

public class Zad6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas1 canvas = new Canvas1();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

