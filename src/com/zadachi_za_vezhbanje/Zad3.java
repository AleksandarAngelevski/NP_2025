package zadachi_za_vezhbanje;
import java.util.*;
import java.util.stream.Collectors;

interface IFile{
    String getFileName();
    long getFileSize();
    String getFileInfo(String indent);
    void sortBySize();
    IFile findLargestFile();
}
class File implements IFile{
    private String name;
    private long fileSize;

    File(String name,long fileSize){
        this.name = name;
        this.fileSize = fileSize;
    }

    @Override
    public boolean equals(Object obj) {
        IFile f = (IFile) obj;
        return this.name.equals(f.getFileName());
    }

    @Override
    public String getFileName() {
        return this.name;
    }

    @Override
    public long getFileSize() {
        return this.fileSize;
    }

    @Override
    public String getFileInfo(String indent) {
        String fileNameSpacing = "";
        for(int i =0;i<=10-this.name.length();i++){
            fileNameSpacing+=" ";
        }
        String fileSizeSpacing = "";
        for(int i =0;i<=10-String.valueOf(this.fileSize).length();i++){
            fileSizeSpacing+=" ";
        }
        String out =indent+"File name:"+fileNameSpacing+this.name+" File size:"+fileSizeSpacing+this.fileSize;

        return out;
    }

    @Override
    public void sortBySize() {

    }
    @Override
    public IFile findLargestFile() {
        return this;
    }
}
class Folder implements IFile{
    private String name;
    private long fileSize;
    private List<IFile> files;

    Folder(String name){
        this.name = name;
        this.fileSize = 0;
        this.files = new ArrayList<>();
    }
    @Override
    public boolean equals(Object obj) {
        IFile f = (IFile) obj;
        return this.name.equals(f.getFileName());
    }
    void addFile(IFile file)  throws FileNameExistsException{
        if(this.files.contains(file)){
            throw new FileNameExistsException(file.getFileName(),this.name);
        }
        this.fileSize+=file.getFileSize();
        files.add(file);
    }



    @Override
    public String getFileName() {
        return this.name;
    }

    @Override
    public long getFileSize() {
        return this.fileSize;
    }

    @Override
    public String getFileInfo(String indent) {
        String fileNameSpacing = "";
        for(int i =0;i<=10-this.name.length();i++){
            fileNameSpacing+=" ";
        }
        String fileSizeSpacing = "";
        for(int i =0;i<=10-String.valueOf(this.fileSize).length();i++){
            fileSizeSpacing+=" ";
        }
        String out=indent;
        out +="Folder name:"+fileNameSpacing+this.name+" Folder size:"+fileSizeSpacing+this.fileSize;

        for(int i=0; i<files.size();i++){
            out+="\n";
            out+=files.get(i).getFileInfo(indent+"    ");

        }
        return out;
    }

    @Override
    public void sortBySize() {
        Collections.sort(this.files,Comparator.comparingLong(IFile::getFileSize));
        this.files.forEach(IFile::sortBySize);
    }
    @Override
    public IFile findLargestFile() {

        IFile tmp=null;
        for(IFile file:this.files){
//            System.out.println(file.getFileInfo(""));
            if(tmp==null && file instanceof File)tmp=file;
            if(file instanceof Folder){
                IFile f = file.findLargestFile();
                if(tmp!=null && f.getFileSize()>tmp.getFileSize())tmp=f;
                else if(tmp==null)tmp=f;
            }else if(tmp.getFileSize()<file.getFileSize() && file instanceof File){
                tmp = file;
            }
        }


        return tmp;
    }

}
class FileNameExistsException  extends Exception{
    FileNameExistsException(String file,String folder){
        super(String.format("There is already a file named %s in the folder %s",file,folder));
    }
}

class FileSystem{
    Folder rootDirectory;
    FileSystem(){
        this.rootDirectory = new Folder("root");
    }
    void addFile(IFile file)throws FileNameExistsException{
        this.rootDirectory.addFile(file);
    }
    long findLargestFile(){
        return this.rootDirectory.findLargestFile().getFileSize();
    }
    void sortBySize(){
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        String out = this.rootDirectory.getFileInfo("")+"\n";
        return out;
    }
}
public class Zad3 {

    public static Folder readFolder (Scanner sc)  {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i=0;i<totalFiles;i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String [] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args)  {

        //file reading from input.txt

        Scanner sc = new Scanner (System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());




    }
}