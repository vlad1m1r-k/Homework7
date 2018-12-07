package ua.kiev.prog.homework7.part2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
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

    private static void startCopy(File sourceFile, File targetFile) throws ExecutionException, InterruptedException {
        long length = sourceFile.length();
        long step = 10 * 1024 * 1024;
        ExecutorService eService = Executors.newFixedThreadPool(10);
        List<Future<?>> taskList = new ArrayList<>();
        Progress progress = new Progress(length / step);
        try (FileChannel sourceFileChannel = (FileChannel) Files.newByteChannel(sourceFile.toPath(), StandardOpenOption.READ);
             FileChannel targetFileChannel = (FileChannel) Files.newByteChannel(targetFile.toPath(), EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ))) {
            try (RandomAccessFile rFile = new RandomAccessFile(targetFile, "rw")) {
                rFile.setLength(length);
            }
            for (long i = 0; i < length; i += step) {
                if (i + step > length) {
                    taskList.add(eService.submit(new CopyThread(sourceFileChannel, targetFileChannel, i, length - i, progress)));
                    break;
                }
                taskList.add(eService.submit(new CopyThread(sourceFileChannel, targetFileChannel, i, step, progress)));
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
