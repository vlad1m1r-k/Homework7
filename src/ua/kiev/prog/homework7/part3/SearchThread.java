package ua.kiev.prog.homework7.part3;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SearchThread implements Runnable {
    private ExecutorService executor;
    private File startDir;
    private String fileName;
    private List<Future<?>> list;

    public SearchThread(ExecutorService executor, File startDir, String fileName, List<Future<?>> list) {
        this.executor = executor;
        this.startDir = startDir;
        this.fileName = fileName;
        this.list = list;
    }

    @Override
    public void run() {
        File[] files = startDir.listFiles();
        for (File file : files) {
            if (!file.isDirectory() && file.getName().equals(fileName)) {
                System.out.println(file.getAbsolutePath());
            }
            if (file.isDirectory() && !Files.isSymbolicLink(file.toPath())) {
                list.add(executor.submit(new SearchThread(executor, file, fileName, list)));
            }
        }
    }
}
