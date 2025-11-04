package testing;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.*;
public class Testing {
    public static void main(String[] args) {
        ArrayList<String> test = new ArrayList<>();
        Consumer<String> fillArray = s -> test.add(s);

        Scanner scan = new Scanner(System.in);
        while(true){
            String line = scan.nextLine();
            if(line.equals("exit")){
                scan.close();
                break;
            }
            fillArray.accept(line);
        }
        for(String s : test){
            System.out.println(s);
        }

    }
}
