package ua.kiev.prog.homework7.part2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CopyFile {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: CopyFile SourceFile TargetFile");
        } else {
            File sourceFile = new File(args[0]);
            File targetFile = new File(args[1]);
            if (!sourceFile.exists()) {
                System.out.println("Source file not exists.");
            } else {
                if (targetFile.isDirectory()) {
                    System.out.println("Target file is a directory.");
                } else {
                    startCopy(sourceFile, targetFile);
                }
            }
        }
    }

    public static synchronized void write(RandomAccessFile target, long seek, byte[] data) throws IOException {
        target.seek(seek);
        target.write(data);
    }

    public static synchronized byte[] read(RandomAccessFile source, long seek, long size) throws IOException {
        byte[] data = new byte[(int) size];
        source.seek(seek);
        source.read(data);
        return data;
    }

    private static void startCopy(File sourceFile, File targetFile) throws ExecutionException, InterruptedException {
        long length = sourceFile.length();
        long step = 1024;
        ExecutorService eService = Executors.newFixedThreadPool(10);
        List<Future<?>> taskList = new ArrayList<>();
        try (RandomAccessFile randomSource = new RandomAccessFile(sourceFile, "r");
             RandomAccessFile randomTarget = new RandomAccessFile(targetFile, "rw")) {
            for (long i = 0; i < length; i += step) {
                if (i + step > length) {
                    taskList.add(eService.submit(new CopyThread(randomSource, randomTarget, i, length - i)));
                    break;
                }
                taskList.add(eService.submit(new CopyThread(randomSource, randomTarget, i, step)));
            }
            for (Future<?> future : taskList) {
                future.get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        eService.shutdown();
    }
}
