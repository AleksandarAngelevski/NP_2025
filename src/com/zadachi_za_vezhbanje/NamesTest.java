package zadachi_za_vezhbanje;
import java.util.*;
import java.util.stream.Collectors;

class Names{
    Map<String, Integer> nameCount;
    Names(){
        this.nameCount = new HashMap<>();
    }
    public void addName(String name){
        Integer count = this.nameCount.compute(name,(k,v)->
                (v==null)?1:v+1
        );
        this.nameCount.put(name,count);
    }
    public void printN(int n){
        this.nameCount.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .filter(e -> e.getValue() >=n)
                .forEach(e-> System.out.println(String.format("%s (%d) %d",e.getKey(),e.getValue(),e.getKey().toLowerCase().chars().distinct().count())));
    };
    String findName(int len, int index){
        ArrayList<String> kurac = this.nameCount.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .filter(e -> e.getKey().length()<len)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));

        return kurac.get(index%kurac.size());
    }
}
public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde
