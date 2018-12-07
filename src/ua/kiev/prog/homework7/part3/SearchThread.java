package ua.kiev.prog.homework7.part3;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;

public class SearchThread implements Runnable {
    private ExecutorService executor;
    private File startDir;
    private String fileName;
    private Semaphore semaphore;

    public SearchThread(ExecutorService executor, File startDir, String fileName, Semaphore semaphore) {
        this.executor = executor;
        this.startDir = startDir;
        this.fileName = fileName;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            File[] files = startDir.listFiles();
            for (File file : files) {
                if (!file.isDirectory() && file.getName().equals(fileName)) {
                    System.out.println(file.getAbsolutePath());
                }
                if (file.isDirectory() && !Files.isSymbolicLink(file.toPath())) {
                    semaphore.addThread();
                    executor.submit(new SearchThread(executor, file, fileName, semaphore));
                }
            }
        } finally {
            semaphore.removeThread();
        }
    }
}
