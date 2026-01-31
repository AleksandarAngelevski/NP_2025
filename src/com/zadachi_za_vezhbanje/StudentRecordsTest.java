package zadachi_za_vezhbanje;
import java.io.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;
import java.util.stream.Collectors;
import java.math.*;
class Student_info{
    String id;
    String nasoka;
    ArrayList<Integer> grades;
    Double gpa;
    public Student_info(String id, String nasoka, ArrayList<Integer> grades) {
        this.id = id;
        this.nasoka = nasoka;
        this.grades = grades;
        this.gpa = this.grades.stream().mapToInt(Integer::intValue).sum()*1.0/this.grades.size();
    }
    String getNasoka(){
        return this.nasoka;
    }
    String getId(){
        return this.id;
    }
    Double getGpa(){
        return this.gpa;
    }
    @Override
    public String toString() {
        return String.format("%s %.2f",this.id, this.gpa);
    }
}
class Distribution{
    private String nasoka;
    private Map<Integer,Integer> gradeCount;
    Distribution(String nasoka){
        this.nasoka = nasoka;
        this.gradeCount = new HashMap<>();
    }
    void addGrade(Integer i){
        gradeCount.computeIfPresent(i,(k,v)->v+1);
        gradeCount.computeIfAbsent(i,(k)->1);
    }
    String getNasoka(){
        return this.nasoka;
    }
    Integer get10s(){
        return this.gradeCount.getOrDefault(10,0);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.nasoka+"\n");
        Set<Map.Entry<Integer,Integer>> k = this.gradeCount.entrySet();
        for(Map.Entry<Integer,Integer> b : k){
            String character ="*";
            sb.append(String.format("%2d | %s(%d)\n",b.getKey(),character.repeat((b.getValue()+10-1)/10),b.getValue()));
        }
        return sb.toString();
    }

}
class StudentRecords{
    private HashMap<String, ArrayList<Student_info>> students;
    StudentRecords(){
        this.students = new HashMap<>();
    };
    int readRecords(InputStream inputStream){
        BufferedReader br =new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int count=0;
        try {
            while ((line = br.readLine()) != null) {
                count++;
                String[] data = line.split(" ");
                String[] grades_str = Arrays.copyOfRange(data,2,data.length);
                ArrayList<Integer> grades = Arrays.stream(grades_str).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
                Student_info si = new Student_info(data[0],data[1],grades);
                ArrayList<Student_info> arr = this.students.getOrDefault(data[1],new ArrayList<Student_info>());
                arr.add(si);
                this.students.put(data[1],arr);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return count;
    }
    public void writeTable(OutputStream os){
        PrintWriter pw = new PrintWriter(new BufferedOutputStream(os));
        ArrayList<String> nasoki = new ArrayList<>(this.students.keySet());
        nasoki.sort(String::compareTo);
        for(String i : nasoki){
            ArrayList<Student_info> si = this.students.get(i);
            si.sort(Comparator.comparing(Student_info::getGpa,Comparator.reverseOrder()).thenComparing(Student_info::getId));
            pw.println(i);
            for(Student_info s : si){
                pw.println(s);
            }
        }
        pw.flush();

    }
    public void writeDistribution(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        Set<Map.Entry<String,ArrayList<Student_info>>> temp = this.students.entrySet();
        ArrayList<Distribution> bla = new ArrayList<>();
        for(Map.Entry<String,ArrayList<Student_info>> kurac : temp){
            Distribution k = new Distribution(kurac.getKey());
            kurac.getValue().stream().forEach(studentInfo ->{
                studentInfo.grades.stream().forEach(grade -> k.addGrade(grade));
            });
            bla.add(k);
        }
        bla.sort(Comparator.comparing(Distribution::get10s).reversed());
        bla.forEach(pw::print);
        pw.flush();

    }

}

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest
{
        public static void main(String[] args) throws Exception{
            FileInputStream input = new FileInputStream("/home/aleksandar/Desktop/NP_2025/src/com/zadachi_za_vezhbanje/input.txt");
            System.out.println("=== READING RECORDS ===");
            StudentRecords studentRecords = new StudentRecords();
            int total = studentRecords.readRecords(input);
            System.out.printf("Total records: %d\n", total);
            System.out.println("=== WRITING TABLE ===");
            studentRecords.writeTable(System.out);
            System.out.println("=== WRITING DISTRIBUTION ===");
            studentRecords.writeDistribution(System.out);
        }
}

// your code here