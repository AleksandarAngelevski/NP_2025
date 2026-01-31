package laboratoriski.lab7;
// import java.lang.classfile.instruction.OperatorInstruction;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
// import java.lang.classfile.instruction.OperatorInstruction;


public class BankBalanceB {

    public static class BankAccount {
        private int balance;
        private final Lock lock = new ReentrantLock();

        public BankAccount(int initialBalance) {
            this.balance = initialBalance;
        }

        public boolean deposit(int amount) {
            lock.lock();
            try {
                balance += amount;
                return true;
            } finally {
                lock.unlock();
            }
        }

        public boolean withdraw(int amount) {
            lock.lock();
            try {
                if (balance >= amount) {
                    balance -= amount;
                    return true;
                }
                return false;
            } finally {
                lock.unlock();
            }
        }

        public int getBalance() {
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }
    }


    public static class OperationResult {
        public final int operationId;
        public final boolean success;
        public final String type;
        public final Integer amount;
        public OperationResult(int operationId, boolean success,String tpye,Integer amount) {
            this.operationId = operationId;
            this.success = success;
            this.type = tpye;
            this.amount = amount;
        }
    }



    public static void main(String[] args) throws Exception {
        int counter=0;
        Scanner sc = new Scanner(System.in);
        int initialBalance = sc.nextInt();
        int n = sc.nextInt();
        BankAccount account = new BankAccount(initialBalance);
        List<Callable<OperationResult>> tasks = new ArrayList<>();
        long lockTimeoutMs = 100;

        List<OperationResult> allResults = Collections.synchronizedList(new ArrayList<>());
        ConcurrentHashMap<Integer,OperationResult> res2 = new ConcurrentHashMap<>();
        for (int i = 0; i < n; i++) {
            String type = sc.next();
            int amount = sc.nextInt();
            int operationId = i + 1;
            tasks.add(() -> {
                Thread.sleep(1000);
                boolean success;
                if (type.equals("deposit")) {
                    success = account.deposit(amount);
                } else {
                    success = account.withdraw(amount);
                }
                OperationResult or =new OperationResult(operationId, success,type,amount);

                allResults.add(or);
                return or;
            });
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(4);
        List<Future<OperationResult>> futures = executor.invokeAll(tasks);
        List<OperationResult> results = new ArrayList<>();

        for (Future<OperationResult> f : futures) {
            f.get();
        }
        executor.shutdown();
        List<OperationResult> sortedRes = new ArrayList<>(allResults);
        sortedRes.sort(Comparator.comparingInt(r-> r.operationId));
        int ids=1;
        for( OperationResult or : sortedRes){
            System.out.println("OPERATION "+ or.operationId+" : "+(or.success?"success" : "failed"));
            ids++;
        }
        System.out.println("FINAL_BALANCE " + account.getBalance());
    }
}