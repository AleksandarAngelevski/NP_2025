package second_midterm;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
interface FileInterface{
    void addFile(String current,String[] paths);
    String print(String indent);
    void removeFile(String current, String[] paths,String target);
    public String getName();
    public List<FileInterface> getChildren();
}
class Directory implements FileInterface{
    String name;
    Directory(String name){
        this.name = name;
    }
    @Override
    public void addFile(String current,String[] paths){

    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String print(String indent) {
        return indent+this.name+"\n";
    }
    @Override
    public void removeFile(String current, String[] path, String target){

    }
    public String getName(){
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FileInterface){
            return ((FileInterface) obj).getName().equals(name);
        }
        return false;
    }

    @Override
    public List<FileInterface> getChildren() {
        return List.of();
    }
}
class SubDirectory implements FileInterface{
    String name;
    List<FileInterface> children;
    SubDirectory(String name){
        this.name = name;
        this.children = new ArrayList<>();
    }
    @Override
    public void addFile(String current,String[] paths){
//        System.out.println(paths.length);
        FileInterface child = generateNode(current);
        try {
            if (children.contains(child)) {
                children.get(children.indexOf(child)).addFile(paths[0], Arrays.copyOfRange(paths, 1, paths.length));
            } else {
                children.add(child);
                child.addFile(paths[0], Arrays.copyOfRange(paths, 1, paths.length));
            }
        }
        catch (ArrayIndexOutOfBoundsException e){

        }
    }


    public static FileInterface generateNode(String name){
        if(name.contains(".")){
            return new Directory(name);
        }else{
            return new SubDirectory(name);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append((this.name+"/\n"));
        for(FileInterface fi : children){
            sb.append(fi.toString());
        }
        return sb.toString();
    }

    @Override
    public String print(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append((indent+this.name+"/\n"));
        for(FileInterface fi : children){
            sb.append(fi.print(indent+"    "));
        }
        return sb.toString();
    }

    @Override
    public void removeFile(String current, String[] paths, String target) {
        FileInterface toRemove = null;

        for( FileInterface fi : children) {
            if(fi.getName().equals(current)){
                if(paths.length > 0){
                    fi.removeFile(paths[0],Arrays.copyOfRange(paths,1,paths.length),target);

                    if(fi.getChildren().isEmpty()){
                        toRemove = fi;
                    }
                }else{
                    toRemove = fi;
                }
                break;
            }

        }
        if(toRemove != null){
            children.remove(toRemove);
        }
    }

    public String getName(){
        return this.name;
    }
    public List<FileInterface> getChildren(){
        return this.children;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FileInterface){
            return ((FileInterface) obj).getName().equals(name);
        }
        return false;
    }
}
class Bucket{
    FileInterface root;

    Bucket(String name) {
        this.root = new SubDirectory(name);
    }

    public void addObject(String key){
//        System.out.println(key);
        String[] parts = key.split("/");
//        System.out.println(parts.length);
        root.addFile(parts[0],Arrays.copyOfRange(parts,1,parts.length));
    }
    public void removeObject(String key){
        String[] parts = key.split("/");

        root.removeFile(parts[0],Arrays.copyOfRange(parts,1,parts.length),parts[parts.length-1]);
    }

    @Override
    public String toString() {
        return this.root.print("");
    }
}

public class BucketTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // bucket name is fixed
        Bucket bucket = new Bucket("bucket");

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String command = parts[0];

            if (command.equalsIgnoreCase("ADD")) {
                bucket.addObject(parts[1]);
            } else if (command.equalsIgnoreCase("REMOVE")) {
                bucket.removeObject(parts[1]);
            } else if (command.equalsIgnoreCase("PRINT")) {
                System.out.print(bucket);
            }
        }
    }
}

