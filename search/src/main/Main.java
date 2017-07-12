package main;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static String searchTxt;
    private static ExecutorService executor;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        if (args.length == 0 || args[0].isEmpty()) {
            throw new IllegalArgumentException();
        }

        searchTxt = args[0];
        int s = Runtime.getRuntime().availableProcessors() * 2;
        executor = Executors.newFixedThreadPool(s);
        Queue<File> paths = new ConcurrentLinkedDeque<>();

        for (File file : File.listRoots()) {
            paths.addAll(new Crawling().crawling(file)); //Получаем файлы
            while (!paths.isEmpty()) {
                executor.submit(new Search(searchTxt, paths.remove())); //Ищем searchTxt
            }
        }
        executor.shutdown();
        System.out.println(System.currentTimeMillis() - startTime);
    }

}
