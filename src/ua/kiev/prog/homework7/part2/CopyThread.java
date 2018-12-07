package ua.kiev.prog.homework7.part2;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CopyThread implements Runnable {
    private FileChannel source;
    private FileChannel target;
    private Progress progress;
    private long from;
    private long step;

    public CopyThread(FileChannel source, FileChannel target, long from, long step, Progress progress) {
        this.source = source;
        this.target = target;
        this.from = from;
        this.step = step;
        this.progress = progress;
    }

    @Override
    public void run() {
        try {
            MappedByteBuffer readBuffer = source.map(FileChannel.MapMode.READ_ONLY, from, step);
            MappedByteBuffer writeBuffer = target.map(FileChannel.MapMode.READ_WRITE, from, step);
            writeBuffer.put(readBuffer);
            progress.step();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
