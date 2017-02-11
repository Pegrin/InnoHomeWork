package org.wtiger.inno.lab1var1;


import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by olymp on 07.02.2017.
 */
class Printer implements Runnable {
    private static volatile boolean stopped = false;
    private static Queue<String> queue = new ArrayDeque<>();

    private Printer(){}

    @Override
    public void run() {
        while (true) {
            if (queue.size() > 0) {
                printStr();
            } else {
                if (isStopped()) break;
            }
        }
    }

    public static synchronized void addStr(String str) {
        queue.add(str);
    }

    private static synchronized void printStr() {
        String s = queue.poll();
        if (s != null) System.out.println(s);
    }

    public static void stop() {
        Printer.stopped = true;
    }

    public static boolean isStopped() {
        return stopped;
    }
}
