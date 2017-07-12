package main;

import java.io.*;
import java.nio.file.Files;

public class Search implements Runnable {
    private byte[] searchTxt;
    private File file;

    public Search(String searchTxt, File file) {
        this.searchTxt = searchTxt.getBytes();
        this.file = file;
    }

    @Override
    public void run() {
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            byte[] buf = new byte[4096];
            int remain = searchTxt.length;
            for (int c; (c = inputStream.read(buf)) != -1;) {
                for (int i = 0; i < c; i++) {
                    if (searchTxt[searchTxt.length - remain] == buf[i]) {
                        remain--;
                        if (remain == 0) {
                            System.out.println(file); //Результат поиска
                            return;
                        }
                    }
                    else {
                        remain = searchTxt.length;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
