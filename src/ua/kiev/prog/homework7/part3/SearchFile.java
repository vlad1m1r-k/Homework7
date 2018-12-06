package ua.kiev.prog.homework7.part3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchFile {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: SearchFile StartDirectory FileName");
        } else {
            File startDir = new File(args[0]);
            String fileName = args[1];
            if (!startDir.isDirectory()) {
                System.out.println("Wrong start point.");
            } else {
                ExecutorService eService = Executors.newFixedThreadPool(30);
                List<Future<?>> list = new ArrayList<>();
                list.add(eService.submit(new SearchThread(eService, startDir, fileName, list)));
//                for (Future<?> future : list) {
//                        future.get();
//                }
                boolean finish = true;
                while (finish) {
                    for (Future<?> future : list) {
                        if (!future.isDone()) {
                            //future.get();
                            break;
                        }
                        finish = false;
                    }
                }
                eService.shutdown();
            }
        }
    }
}
