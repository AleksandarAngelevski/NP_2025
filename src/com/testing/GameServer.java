package testing;

import javax.swing.plaf.basic.BasicTreeUI;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

class GameRoom{
    GameRoom(){

    }

}


public class GameServer {
    private static ReentrantLock lock = new ReentrantLock();
    private static final int timeout_time = 500;
    public static void main(String[] args) throws Exception {
        BlockingDeque<String> inputQueue = new LinkedBlockingDeque<>();
        ArrayList<String> inputList = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        String line;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable printer = () -> {
            try{
                while(true){
                    String input = inputQueue.take();
                    if(input.equalsIgnoreCase("exit")){
                        break;
                    }
                    if(lock.tryLock(timeout_time,TimeUnit.MILLISECONDS)){
                        System.out.println("Thread: "+Thread.currentThread().getName()+" reading last string");
                        System.out.println(inputList.getLast());
                        Thread.sleep(2000);
                        lock.unlock();
                    }
                    Thread.sleep(500);
//                    System.out.println(input);
                }
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        };
        executor.submit(printer);
        while( (line=scan.nextLine())!=null){
            System.out.println("Trying lock...");
            if(!lock.tryLock(timeout_time,TimeUnit.MILLISECONDS)) {
                System.out.println("Lock wait passed...");
                continue;
            };
            inputQueue.put(line);
            inputList.add(line);
            System.out.println("Thread: "+Thread.currentThread().getName()+" adding");
            lock.unlock();
//            if(line.equalsIgnoreCase("exit"))break;
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Program terminated");
    }
}
