

import java.util.ArrayList;
import java.util.List;

interface FileSystemNode{
    void ls(int intended);
    void openAll(int intended);
    int getSize();
    FileSystemNode cd(String name);
    String getName();
    boolean isFolder();
}


class File implements FileSystemNode {

    String name;
    int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void ls(int intended) {
        String indentSpaces = " ".repeat(intended);
        System.out.println(indentSpaces + name);
    }

    @Override
    public void openAll(int intended) {
        String indentSpaces = " ".repeat(intended);
        System.out.println(indentSpaces + name);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public FileSystemNode cd(String name) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}

class Folder implements FileSystemNode {

    String name;
    List<FileSystemNode> children;


    public Folder(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    public void add(FileSystemNode child){
        children.add(child);
    }

    @Override
    public void ls(int intended) {
        String indentSpaces = " ".repeat(intended);

        for(FileSystemNode child : children){
            if(child.isFolder()){
                System.out.println(indentSpaces + "+ " + child.getName());
            }else{
                System.out.println(indentSpaces + child.getName());
            }
        }
    }

    @Override
    public void openAll(int intended) {
        String indentSpaces = " ".repeat(intended);
        System.out.println(indentSpaces + "+ " +this. name);

        for(FileSystemNode child : children){
            child.openAll(intended + 4);
        }
    }

    @Override
    public int getSize() {
        int size = 0;
        for(FileSystemNode child : children){
            size +=  child.getSize();
        }
        return size;
    }

    @Override
    public FileSystemNode cd(String name) {
        for(FileSystemNode child : children){
            if(child.isFolder() && child.getName().equals(name)){
                return child;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isFolder() {
        return true;
    }
}

public class CompositeDesignPattern {
    public static void main() {
        Folder root = new Folder("root");
        root.add(new File("file1.txt", 1));
        root.add(new File("file2.txt", 1));

        Folder docs = new Folder("docs");
        docs.add(new File("resume.pdf", 1));
        docs.add(new File("notes.txt", 1));
        root.add(docs);

        Folder images = new Folder("images");
        images.add(new File("photo.jpg", 1));
        root.add(images);

        root.ls(0);

        docs.ls(0);

        root.openAll(0);

        FileSystemNode cwd = root.cd("docs");
        if (cwd != null) {
            cwd.ls(0);
        } else {
            System.out.println("\nCould not cd into docs\n");
        }

        System.out.println(root.getSize());
    }
}
