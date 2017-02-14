package org.wtiger.inno.lab1var1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Этот трудяга выводит на печать все, что ему скормят.
 */

class Printer implements Runnable {
    static ReentrantLock printerLock = new ReentrantLock();
    private static final Logger logger = Logger.getLogger(Printer.class);
    static {
        DOMConfigurator.configure("src/resources/log_conf.xml");
    }

    private static volatile boolean stopped = false;
    private static volatile boolean done = false;
    private static volatile String stopMessage = "";
    private static volatile Queue<String> queue = new ArrayDeque<>();
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
        logger.info("Начало печати.");
        while (true) {
            if (Printer.queue.size() > 0) {
                if (isStopped()) {
                    System.out.println(stopMessage);
                    break;
                }
                printStr();
            } else {
                if (isDone()) break;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    logger.error("Сон системы печати был прерван...", e);
                }
            }
        }
        logger.info("Завершение печати.");
    }

    /**
     * Добавляет строку в очередь на печать
     *
     * @param str Строка в очередь на печать
     */
    static void addStr(String str) {
        printerLock.lock();
        Printer.queue.add(str);
        printerLock.unlock();
    }

    /**
     * Печатает одну строку из очереди на печать
     */
    private static void printStr() {
        String s = Printer.queue.poll();
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
