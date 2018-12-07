package ua.kiev.prog.homework7.part3;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchFile {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: SearchFile StartDirectory FileName");
        } else {
            File startDir = new File(args[0]);
            String fileName = args[1];
            if (!startDir.isDirectory()) {
                System.out.println("Wrong start point.");
            } else {
                ExecutorService eService = Executors.newFixedThreadPool(30);
                Semaphore semaphore = new Semaphore();
                semaphore.addThread();
                eService.submit(new SearchThread(eService, startDir, fileName, semaphore));
                semaphore.waitThreads();
                eService.shutdown();
            }
        }
    }
}
