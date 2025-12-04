package zadachi_za_vezhbanje;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
class IrregularCanvasException extends Exception {
    IrregularCanvasException(String canvas_id,double max_area) {
        super("Canvas "+canvas_id+" has a shape with area larger than "+String.format("%.2f",max_area));
    }
}
abstract class Shape implements Comparable<Shape>{
    protected double area;
    abstract double getArea();
    Shape(double area) {
        this.area = area;
    }
    @Override
    public int compareTo(Shape o) {
        if(area > o.area){
            return 1;
        }else if(area < o.area){
            return -1;
        }
        return 0;

    }
}
class Circle extends Shape{
    private int radius;

    Circle(int radius) {
        super(Math.PI*radius*radius);
        this.radius = radius;
    }
    @Override
    public double getArea() {
        return this.area;
    }
}
class Square extends Shape{
    private int side;
    Square(int side) {
        super(Math.pow(side,2));
        this.side =side;

    }
    @Override
    public double getArea() {
        return this.area;
    }
}
class Canvas implements Comparable<Canvas>{
    String canvas_id;
    ArrayList<Shape> shapes = new ArrayList<>();
    private int totalShapes;
    private double shapes_area_sum;
    Canvas(ArrayList<Shape> shapes,String canvas_id){
        this.canvas_id = canvas_id;
        this.shapes = shapes;
        this.totalShapes = shapes.size();
        this.shapes_area_sum = shapes.stream().mapToDouble(Shape::getArea).sum();
    }
    public int getCircles(){
        return shapes.stream().filter(e -> e instanceof Circle).collect(Collectors.toList()).size();
    }
    public int getSquares(){
        return shapes.stream().filter(e -> e instanceof Square).collect(Collectors.toList()).size();
    }
    public int getTotalShapes() {
        return totalShapes;
    }

    public double getShapes_area_sum() {
        return shapes_area_sum;
    }
    @Override
    public int compareTo(Canvas o) {
        if(this.shapes_area_sum > o.getShapes_area_sum()){
            return 1;
        }else if(this.shapes_area_sum < o.getShapes_area_sum()){
            return -1;
        }
        return 0;
    }
    String getId(){
        return this.canvas_id;
    }
    ArrayList<Shape> getShapes(){
        return this.shapes;
    }
}
class ShapesApplication2{
    double maxArea;
    ArrayList<Canvas> canvases = new ArrayList<>();
    ShapesApplication2(double maxArea){
        this.maxArea = maxArea;
    }
    void readCanvases(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String canvas_id = line.split(" ")[0];
                String[] shapes = Arrays.copyOfRange(line.split(" "),1,line.split(" ").length);
                ArrayList<Shape> shapesList = new ArrayList<>();
                try {
                    for (int i = 0; i < shapes.length - 1; i += 2) {
                        Shape shape;
                        shape = ShapesApplication2.getShape(shapes[i], shapes[i + 1]);
                        if (shape.getArea() > maxArea) {
                            throw new IrregularCanvasException(canvas_id, maxArea);
                        }
                        shapesList.add(shape);

                    }
                    Canvas canvas = new Canvas(shapesList,canvas_id);
                    canvases.add(canvas);
                }
                catch (IrregularCanvasException e){System.out.println(e.getMessage());}

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    void printCanvases(OutputStream os){
        Collections.sort(this.canvases);
        Collections.reverse(this.canvases);
        try {
            for (Canvas canvas : canvases) {
                String line = canvas.getId() + " " + canvas.getTotalShapes() + " " + canvas.getCircles() + " " + canvas.getSquares() + " " + String.format("%.2f",Collections.min(canvas.getShapes()).getArea()) + " " + String.format("%.2f",Collections.max(canvas.getShapes()).getArea()) + " " + String.format("%.2f",canvas.getShapes_area_sum() / canvas.getTotalShapes())+"\n";
                os.write(line.getBytes());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    static Shape getShape(String type,String size){
        if(type.equals("S")){
            return new Square(Integer.valueOf(size));
        }else{
            return new  Circle(Integer.valueOf(size));
        }
    }
}


public class Zad2 {

    public static void main(String[] args) {

        ShapesApplication2 shapesApplication = new ShapesApplication2(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}
