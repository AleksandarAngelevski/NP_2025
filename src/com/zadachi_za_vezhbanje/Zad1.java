package zadachi_za_vezhbanje;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

class Helper{
    private String id;
    private Integer squares_count;
    private Integer perimeters;

    Helper(String id, String[] squares){
        this.id = id;
        this.squares_count = squares.length;
        this.perimeters = Arrays.stream(squares).mapToInt( e -> Integer.parseInt(e)*4).sum();
    }

    public String getId() {
        return id;
    }

    public Integer getSquares_count() {
        return squares_count;
    }

    public Integer getPerimeters() {
        return perimeters;
    }
}
class ShapesApplication{
    ArrayList<Helper> canvases = new ArrayList<>();
    ShapesApplication(){}
    int readCanvases(InputStream inputStream){
        int canvases_count = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try{
            while((line = reader.readLine()) != null){
                String[] lineArr = line.split(" ");
                canvases_count += lineArr.length-1;
                canvases.add(new Helper(lineArr[0], Arrays.copyOfRange(lineArr, 1, lineArr.length)));
            }}
        catch (IOException e){
            e.printStackTrace();
        }
        return canvases_count;
    }
    void printLargestCanvasTo(OutputStream outputStream){
        Optional<Helper> largest = canvases.stream().max(Comparator.comparingInt(c -> c.getPerimeters()));
        if(largest.isPresent()){
            Helper h = largest.get();
            String output = String.format("%s %d %d%n",h.getId(), h.getSquares_count(), h.getPerimeters());
            try{
                outputStream.write(output.getBytes());
                outputStream.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
public class Zad1{

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();
        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
