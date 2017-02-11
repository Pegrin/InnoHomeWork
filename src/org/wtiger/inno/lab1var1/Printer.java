package org.wtiger.inno.lab1var1;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Этот трудяга выводит на печать все, что ему скормят.
 */

class Printer implements Runnable {
    private static volatile boolean stopped = false;
    private static volatile boolean done = false;
    private static volatile String stopMessage = "";
    private static Queue<String> queue = new ArrayDeque<>();
    private static final Printer PRINTER = new Printer();

    private Printer(){}
    static Printer getInstance(){
        return Printer.PRINTER;
    }
    @Override
    public void run() {
        System.out.println("Запуск принтера");
        while (!isDone()) {
            if (queue.size() > 0) {
                printStr();
            } else {
                if (isStopped()){
                    System.out.println(stopMessage);
                    break;
                }
            }
        }
        System.out.println("Остановка принтера.");
    }

    static synchronized void addStr(String str) {
        queue.add(str);
    }

    private static void printStr() { //вернуть синхронайзед если все пойдет по
        String s = queue.poll();
        if (s != null) System.out.println(s);
    }

    static void stop(String msg) {
        stopMessage = msg;
        Printer.stopped = true;
    }

    static void setDone() {
        done = true;
    }

    private static boolean isDone(){
        return done;
    }

    private static boolean isStopped() {
        return stopped;
    }
}
