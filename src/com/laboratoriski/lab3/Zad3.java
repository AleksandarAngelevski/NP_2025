package laboratoriski.lab3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Applicant{
    private int id;
    private String name;
    private double gpa;
    private List<SubjectWithGrade> subjectWithGrade;
    private StudyProgramme studyProgramme;

    public Applicant(int id, String name,double gpa,StudyProgramme studyProgramme) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.subjectWithGrade = new ArrayList<>();
        this.studyProgramme = studyProgramme;
    }
    void addSubjectAndGrade(String subject, int grade){
        this.subjectWithGrade.add(new SubjectWithGrade(subject,grade));

    }
    double calculatePoints(Faculty faculty){
        double p= this.gpa*12;
        double e_p = this.subjectWithGrade.stream().mapToDouble((e)->{
            if(faculty.getAppropriateSubjects().contains(e.getSubject())) return e.getGrade()*2;
            else return e.getGrade()*1.2;
        }).sum();
        return p+e_p;
    }

    @Override
    public String toString() {
//        Id: 1, Name: Ana, GPA: 4.9
        return "Id: "+this.id+", Name: "+this.name+", GPA: "+this.gpa;
    }
}

class StudyProgramme{

    private String code;
    private String name;
    private int numPublicQuota;
    private int numPrivateQuota;
    private int enrolledInPublicQuota;
    private int enrolledInPrivateQuota;
    private List<Applicant> applicants;
    private Faculty faculty;
    StudyProgramme(String code,String name,Faculty faculty,int numPublicQuota,int numPrivateQuota){
        this.code = code;
        this.name = name;
        this.numPublicQuota = numPublicQuota;
        this.numPrivateQuota = numPrivateQuota;
        this.applicants = new ArrayList<>();
        this.faculty = faculty;
        this.enrolledInPublicQuota=0;
        this.enrolledInPrivateQuota=0;
    }
    void calculateEnrollmentNumbers(){

        Map<Applicant,Double> scores = new HashMap<>();
        for(Applicant applicant : applicants){
            scores.put(applicant,applicant.calculatePoints(this.faculty));
        }
        List<Applicant> sorted_with_points = this.applicants.stream().sorted((o2,o1)->Double.compare(scores.get(o1),scores.get(o2))).collect(Collectors.toList());
        applicants=sorted_with_points;
        for(int i=0;i<applicants.size();i++){
            if(enrolledInPublicQuota<numPublicQuota){
                enrolledInPublicQuota++;
            }else if(enrolledInPrivateQuota<numPrivateQuota){
                enrolledInPrivateQuota++;
            }
        }
    }
    double getPrecentageOfEnrolled(){
        return (this.enrolledInPublicQuota+this.enrolledInPrivateQuota)/((this.numPublicQuota+this.numPrivateQuota)*1.00);
    }
    public String getCode(){
        return this.code;
    }
    public String getName(){
        return this.name;
    }
    public void addApplicant(Applicant a){
        this.applicants.add(a);
    }
    public Faculty getFaculty(){
        return this.faculty;
    }

    @Override
    public String toString() {
//        Faculty: FINKI
//        Subjects: [Mother Tongue, Mathematics, Informatics]
//        Study Programmes:
//        Name: Information Technology
//        Public Quota:
//        Id: 7, Name: Ivana, GPA: 4.6 - 89.19999999999999
//        Id: 2, Name: Boris, GPA: 4.5 - 83.6
//        Private Quota:
//        Rejected:
        List<Applicant> applicants = this.applicants;
        String applicantsString = "";
        // System.out.println("MAX POUBLIC "+this.enrolledInPublicQuota);
        applicantsString+="Public Quota:\n";
        for(int i=0;i<enrolledInPublicQuota;i++){
            applicantsString+=applicants.get(i).toString()+" - "+ applicants.get(i).calculatePoints(this.faculty)+"\n";
        }
        applicantsString+="Private Quota:\n";
        for(int i=enrolledInPublicQuota;i<enrolledInPrivateQuota+enrolledInPublicQuota;i++){
            applicantsString+=applicants.get(i).toString()+" - "+ applicants.get(i).calculatePoints(this.faculty)+"\n";
        }
        applicantsString+="Rejected:\n";
        for(int i=(enrolledInPrivateQuota+enrolledInPublicQuota);i<this.applicants.size();i++){
            applicantsString+=applicants.get(i).toString()+" - "+ applicants.get(i).calculatePoints(this.faculty)+"\n";
        }
        return "Name: " + this.name+"\n"+applicantsString;    }
}
class Faculty{
    private String shortName;
    private List<String> appropriateSubjects;
    private List<StudyProgramme> studyProgrammes;
    Faculty(String shortName){
        this.shortName = shortName;
        this.appropriateSubjects = new ArrayList<>();
        this.studyProgrammes = new ArrayList<>();
    }
    public void addSubject(String subject){
        this.appropriateSubjects.add(subject);
    }
    public void addStudyProgramme(StudyProgramme studyProgramme){
        this.studyProgrammes.add(studyProgramme);
    }

    public String getShortName() {
        return shortName;
    }
    public int getNumOfAppSubjects(){
        return this.appropriateSubjects.size();
    }

    public List<String> getAppropriateSubjects() {
        return appropriateSubjects;
    }

    public List<StudyProgramme> getStudyProgrammes() {
        return this.studyProgrammes.stream().sorted(Comparator.comparingDouble(StudyProgramme::getPrecentageOfEnrolled).reversed()).collect(Collectors.toList());
    }




}
class SubjectWithGrade
{
    private String subject;
    private int grade;
    public SubjectWithGrade(String subject, int grade) {
        this.subject = subject;
        this.grade = grade;
    }
    public String getSubject() {
        return subject;
    }
    public int getGrade() {
        return grade;
    }
}

class EnrollmentsIO {
    public static void printRanked(List<Faculty> faculties) {
//        Faculty: FINKI
//        Subjects: [Mother Tongue, Mathematics, Informatics]
//        Study Programmes:
//        Name: Information Technology
//        Public Quota:
//        Id: 7, Name: Ivana, GPA: 4.6 - 89.19999999999999
//        Id: 2, Name: Boris, GPA: 4.5 - 83.6
//        Private Quota:
//        Rejected:
        faculties.sort(Comparator.comparingInt(Faculty::getNumOfAppSubjects));
        // Collections.reverse(faculties);

//        faculties.sort((o1,o2)->{
//            return Integer.compare(o1.getNumOfAppSubjects(),o2.getNumOfAppSubjects());
//        });
        for (Faculty faculty : faculties) {
            System.out.println("Faculty: "+faculty.getShortName());
            System.out.println("Subjects: " +faculty.getAppropriateSubjects());
            System.out.println("Study Programmes: ");
            faculty.getStudyProgrammes().forEach(System.out::println);
        }
    }

    public static void readEnrollments(List<StudyProgramme> studyProgrammes, InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                StudyProgramme sp = studyProgrammes.stream().filter((s)->s.getCode().equals(data[data.length-1])).findFirst().get();
                Applicant a = new Applicant(Integer.parseInt(data[0]),data[1],Double.parseDouble(data[2]),sp);
                String[] grades = Arrays.copyOfRange(data,3,data.length-1);
                for(int i=0;i<grades.length-1;i+=2){
                    a.addSubjectAndGrade(grades[i],Integer.parseInt(grades[i+1]));
                }
                sp.addApplicant(a);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

public class Zad3 {

    public static void main(String[] args) {
        Faculty finki = new Faculty("FINKI");
        finki.addSubject("Mother Tongue");
        finki.addSubject("Mathematics");
        finki.addSubject("Informatics");

        Faculty feit = new Faculty("FEIT");
        feit.addSubject("Mother Tongue");
        feit.addSubject("Mathematics");
        feit.addSubject("Physics");
        feit.addSubject("Electronics");

        Faculty medFak = new Faculty("MEDFAK");
        medFak.addSubject("Mother Tongue");
        medFak.addSubject("English");
        medFak.addSubject("Mathematics");
        medFak.addSubject("Biology");
        medFak.addSubject("Chemistry");

        StudyProgramme si = new StudyProgramme("SI", "Software Engineering", finki, 4, 4);
        StudyProgramme it = new StudyProgramme("IT", "Information Technology", finki, 2, 2);
        finki.addStudyProgramme(si);
        finki.addStudyProgramme(it);

        StudyProgramme kti = new StudyProgramme("KTI", "Computer Technologies and Engineering", feit, 3, 3);
        StudyProgramme ees = new StudyProgramme("EES", "Electro-energetic Systems", feit, 2, 2);
        feit.addStudyProgramme(kti);
        feit.addStudyProgramme(ees);

        StudyProgramme om = new StudyProgramme("OM", "General Medicine", medFak, 6, 6);
        StudyProgramme nurs = new StudyProgramme("NURS", "Nursing", medFak, 2, 2);
        medFak.addStudyProgramme(om);
        medFak.addStudyProgramme(nurs);

        List<StudyProgramme> allProgrammes = new ArrayList<>();
        allProgrammes.add(si);
        allProgrammes.add(it);
        allProgrammes.add(kti);
        allProgrammes.add(ees);
        allProgrammes.add(om);
        allProgrammes.add(nurs);

        EnrollmentsIO.readEnrollments(allProgrammes, System.in);

        List<Faculty> allFaculties = new ArrayList<>();
        allFaculties.add(finki);
        allFaculties.add(feit);
        allFaculties.add(medFak);

        allProgrammes.stream().forEach(StudyProgramme::calculateEnrollmentNumbers);

        EnrollmentsIO.printRanked(allFaculties);

    }


}
