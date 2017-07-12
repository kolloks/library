package main;

import java.io.File;
import java.util.*;

public class Crawling {

    public List<File> crawling(File root) {
        File[] rootDir = root.listFiles();
        List<File> result = new ArrayList<>();
        Queue<File> fileTree = new PriorityQueue<>();

        try {
            //Exception in thread "main" java.io.UncheckedIOException: java.nio.file.AccessDeniedException: C:\$Recycle.Bin\S-1-5-20
            if (rootDir[0].getAbsolutePath().contains("$Recycle.Bin")) rootDir[0] = new File("");
            //
            Collections.addAll(fileTree, rootDir);
            while (!fileTree.isEmpty()) {
                File currentFile = fileTree.remove();
                if (currentFile.isDirectory()) {
                    Collections.addAll(fileTree, currentFile.listFiles());
                } else {
                    result.add(currentFile);
                }
            }
        } catch (NullPointerException e){/*NOP*/}

        return result;
    }
}
