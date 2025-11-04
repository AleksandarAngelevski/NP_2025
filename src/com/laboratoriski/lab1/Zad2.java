package laboratoriski.lab1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;



enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}


interface Movable{
    void moveUp() throws ObjectCanNotBeMovedException;
    void moveDown() throws ObjectCanNotBeMovedException;
    void moveLeft() throws ObjectCanNotBeMovedException;
    void moveRight() throws ObjectCanNotBeMovedException;
    int getCurrentXPosition();
    int getCurrentYPosition();
}
class MovingPoint implements Movable{
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    public MovingPoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    @Override
    public void moveUp() throws ObjectCanNotBeMovedException{
        int newY = this.y + this.ySpeed;
        if(newY>MovablesCollection.y_MAX){
            throw new ObjectCanNotBeMovedException("Point ("+this.x+","+newY+") is out of bounds");
        }
        this.y = newY;
    }
    @Override
    public void moveDown() throws ObjectCanNotBeMovedException{
        int newY = this.y - this.ySpeed;
        if(newY<0){
            throw new ObjectCanNotBeMovedException("Point ("+this.x+","+newY+") is out of bounds");
        }
        this.y = newY;
    }
    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException{
        int newX = this.x - this.xSpeed;
        if(newX<0){
            throw new ObjectCanNotBeMovedException("Point ("+newX+","+this.y+") is out of bounds");
        }
        this.x = newX;
    }
    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        int newX = this.x + this.xSpeed;
        if(newX>MovablesCollection.x_MAX){
            throw new ObjectCanNotBeMovedException("Point ("+newX+","+this.y+") is out of bounds");
        }
        this.x = newX;
    }

    @Override
    public String toString() {
        return "Movable point with coordinates ("+this.x+","+this.y+")";
    }

    @Override
    public int getCurrentXPosition() {
        return this.x;
    }
    @Override
    public int getCurrentYPosition() {
        return this.y;
    }
}
class MovingCircle implements Movable{
    private int radius;
    private MovingPoint center;
    public MovingCircle(int radius, MovingPoint center) {
        this.radius = radius;
        this.center = center;
    }
    @Override
    public void moveUp() throws ObjectCanNotBeMovedException{
        this.center.moveUp();
    }
    @Override
    public void moveDown() throws ObjectCanNotBeMovedException{
        this.center.moveDown();
    }
    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException{
        this.center.moveLeft();
    }
    @Override
    public void moveRight() throws ObjectCanNotBeMovedException{
        this.center.moveRight();
    }
    public int getRadius() {
        return this.radius;
    }
    @Override
    public String toString() {
        return "Movable circle with center coordinates ("+this.center.getCurrentXPosition()+","+this.center.getCurrentYPosition()+") and radius "+this.getRadius();
    }
    @Override
    public int getCurrentXPosition() {
        return this.center.getCurrentXPosition();
    }
    @Override
    public int getCurrentYPosition() {
        return this.center.getCurrentYPosition();
    }
}
class MovablesCollection{
    private int size;
    private List<Movable> movables;
    public static int x_MAX;
    public static int y_MAX;
    public MovablesCollection(){}
    public MovablesCollection(int x_MAX, int y_MAX){
        MovablesCollection.x_MAX = x_MAX;
        MovablesCollection.y_MAX = y_MAX;
        movables = new ArrayList<Movable>();
    }
    void addMovableObject(Movable m) throws MovableObjectNotFittableException{
        if(m instanceof MovingPoint){
            if(m.getCurrentXPosition()<=this.x_MAX && m.getCurrentXPosition()>=0 && m.getCurrentYPosition()<=this.y_MAX && m.getCurrentYPosition()>=0){

            }else{
                throw new MovableObjectNotFittableException("Movable circle with center ("+m.getCurrentXPosition()+","+m.getCurrentYPosition()+") can not be fitted into the collection");
            }
        }else{
            MovingCircle e = (MovingCircle)m;
            if(m.getCurrentXPosition()+e.getRadius()<=this.x_MAX && m.getCurrentXPosition()-e.getRadius()>=0 && m.getCurrentYPosition()+e.getRadius()<=this.y_MAX && m.getCurrentYPosition()-e.getRadius()>=0){

            }else{
                throw new MovableObjectNotFittableException("Movable circle with center ("+m.getCurrentXPosition()+","+m.getCurrentYPosition()+") and radius "+e.getRadius()+" can not be fitted into the collection");
            }
        }
        movables.add(m);
    }
    void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction)throws ObjectCanNotBeMovedException{
        if(this.movables==null){

        }else{
            for(int i=0;i<movables.size();i++){
                // System.out.println("Before moving " +direction+" " +movables.get(i));
                if(movables.get(i) instanceof MovingPoint && type.equals(TYPE.POINT)){
                    if(direction.equals(DIRECTION.UP)){
                        try{
                            movables.get(i).moveUp();
                        }catch(ObjectCanNotBeMovedException e){
                            System.out.println(e.getMessage());
                        }
                    }else if(direction.equals(DIRECTION.DOWN)){
                        try{
                            movables.get(i).moveDown();
                        }catch(ObjectCanNotBeMovedException e){
                            System.out.println(e.getMessage());
                        }
                    } else if (direction.equals(DIRECTION.LEFT)) {
                        try{
                            movables.get(i).moveLeft();
                        }catch(ObjectCanNotBeMovedException e){
                            System.out.println(e.getMessage());
                        }
                    }else{
                        try{
                            movables.get(i).moveRight();
                        }catch(ObjectCanNotBeMovedException e){
                            System.out.println(e.getMessage());
                        }
                    }
                }else if(movables.get(i) instanceof MovingCircle && type.equals(TYPE.CIRCLE)){
                    if(direction.equals(DIRECTION.UP)){
                        try{
                            movables.get(i).moveUp();
                        }catch(ObjectCanNotBeMovedException e){
                            System.out.println(e.getMessage());
                        }
                    }else if(direction.equals(DIRECTION.DOWN)){
                        try{
                            movables.get(i).moveDown();
                        }catch(ObjectCanNotBeMovedException e){
                            System.out.println(e.getMessage());
                        }
                    } else if (direction.equals(DIRECTION.LEFT)) {
                        try{
                            movables.get(i).moveLeft();
                        }catch(ObjectCanNotBeMovedException e){
                            System.out.println(e.getMessage());
                        }
                    }else{
                        try{
                            movables.get(i).moveRight();
                        }catch(ObjectCanNotBeMovedException e){
                            System.out.println(e.getMessage());
                        }
                    }
                }
                // System.out.println("After moving " +direction+" " +movables.get(i));
            }

        }
    }


    public static void setxMax(int x){
        MovablesCollection.x_MAX = x;
    }
    public static void setyMax(int y){
        MovablesCollection.y_MAX = y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Collection of movable objects with size %d:\n", movables.size()));
        for (int i = 0; i < movables.size(); i++) {
            sb.append(movables.get(i).toString());
            if (i < movables.size() - 1) sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

}
class ObjectCanNotBeMovedException extends Exception{
    public ObjectCanNotBeMovedException() {}
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}
class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(){}
    public MovableObjectNotFittableException(String message){
        super(message);
    }
}






public class Zad2 {

    public static void main(String[] args) {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);
            try{
                if (Integer.parseInt(parts[0]) == 0) { //point
                    collection.addMovableObject(new MovingPoint(x, y, xSpeed, ySpeed));
                } else { //circle
                    int radius = Integer.parseInt(parts[5]);
                    collection.addMovableObject(new MovingCircle(radius, new MovingPoint(x, y, xSpeed, ySpeed)));
                }}
            catch(MovableObjectNotFittableException e){
                System.out.println(e.getMessage());
            }

        }

        System.out.println(collection.toString());
        try{
            System.out.println("MOVE POINTS TO THE LEFT");
            collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);

        }catch(ObjectCanNotBeMovedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(collection.toString());

        try{
            System.out.println("MOVE CIRCLES DOWN");
            collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);

        }catch(ObjectCanNotBeMovedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        try{
            System.out.println("MOVE POINTS TO THE RIGHT");
            collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);

        }catch(ObjectCanNotBeMovedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(collection.toString());

        try{
            System.out.println("MOVE CIRCLES UP");
            collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);

        }catch(ObjectCanNotBeMovedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(collection.toString());


    }


}
