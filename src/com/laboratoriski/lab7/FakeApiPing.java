package laboratoriski.lab7;

import java.util.*;
import java.util.concurrent.*;

public class FakeApiPing {

    // Result holder
    public static class ApiResult {
        public final int requestId;
        public final boolean success;
        public final String value;

        public ApiResult(int requestId, boolean success, String value) {
            this.requestId = requestId;
            this.success = success;
            this.value = value;
        }

        @Override
        public String toString() {
            return "ApiResult{" +
                    "requestId=" + requestId +
                    ", success=" + success +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static class Api {
        public static ApiResult get(int requestId, int parameter) throws InterruptedException {
            long delayMillis = parameter * 100L;
            Thread.sleep(delayMillis);

            String response = "VALUE_" + parameter;
            return new ApiResult(requestId, true, response);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // number of API calls

        List<Callable<ApiResult>> tasks = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int parameter = sc.nextInt();

            // requestId is the loop index
            int requestId = i+1;
            //TODO add a Callable that invokes the API get method in the tasks list
            tasks.add(()->{
                return Api.get(requestId,parameter);
            });
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<ApiResult>> futures = new ArrayList<>();
        //TODO submit all callables to the executer and get the Futures
        for( Callable<ApiResult> task : tasks){
            futures.add(executor.submit(task));
        }


        List<ApiResult> results = new ArrayList<>();
        long timeoutMillis = 200;

        //TODO get the ApiResult from all the futures and allow a max timeout of timeoutMillis
        for (Future<ApiResult> future : futures){
            try{
                ApiResult result = future.get(timeoutMillis, TimeUnit.MILLISECONDS);
                results.add(result);
            }catch (TimeoutException e){
                int index = futures.indexOf(future)+1;
                results.add(new ApiResult(index,false,"TIMEOUT"));
                future.cancel(true);
            }catch (Exception e) {
                // Catch other exceptions as failures
                int index = futures.indexOf(future)+1;
                results.add(new ApiResult(index, false, "ERROR"));
            }
        }

        executor.shutdown();

        // Sorting by requestId
        results.sort(Comparator.comparingInt(r -> r.requestId));

        // Output
        for (ApiResult r : results) {
            System.out.printf(
                    "%d %s %s%n",
                    r.requestId,
                    r.success ? "OK" : "FAILED",
                    r.value
            );
        }
    }
}
