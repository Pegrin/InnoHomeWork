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

    private Printer() {
    }

    static Printer getInstance() {
        return Printer.PRINTER;
    }

    /**
     * Печатает все, что попадет в очередь печати до команды отбой.
     */
    @Override
    public void run() {
        System.out.println("Начало печати.");
        while (true) {
            if (queue.size() > 0) {
                if (isStopped()) {
                    System.out.println(stopMessage);
                    break;
                }
                printStr();
            } else {
                if (isDone()) break;
            }
        }
        System.out.println("Завершение печати.");
    }

    /**
     * Добавляет строку в очередь на печать
     *
     * @param str Строка в очередь на печать
     */
    static synchronized void addStr(String str) {
        queue.add(str);
    }

    /**
     * Печатает одну строку из очереди на печать
     */
    private static void printStr() {
        String s = queue.poll();
        if (s != null) System.out.println(s);
    }

    /**
     * Переключает переключатель в режим экстьренного завершения
     * печати, сообщает последнюю фразу для вывода на печать.
     *
     * @param msg Фраза для вывода на печать перед завершением работы печатателя.
     */
    static void stop(String msg) {
        stopMessage = msg;
        Printer.stopped = true;
    }

    /**
     * Сообщает принтеру, что на сегодня работа окончена.
     */
    static void setDone() {
        done = true;
    }

    private static boolean isDone() {
        return done;
    }

    private static boolean isStopped() {
        return stopped;
    }
}
