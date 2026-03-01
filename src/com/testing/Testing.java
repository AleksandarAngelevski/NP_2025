package testing;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
public class Testing{
    public static void main(String[] args) throws Exception{
        ExecutorService kurac = Executors.newFixedThreadPool(25);
        List<Callable<Integer>> tasks = new ArrayList<>();
        for(int i=0;i<1000;i++){
            tasks.add(()->{
                System.out.println("Calculationg");
                Thread.sleep(500);
                return 10*5;
            });
        }

        List<Future<Integer>> futures = kurac.invokeAll(tasks);
        for(Future<Integer> f: futures){
            System.out.println(f.get());
        }
        kurac.shutdown();
    }
}