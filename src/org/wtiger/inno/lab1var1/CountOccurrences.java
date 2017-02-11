package org.wtiger.inno.lab1var1;

import java.util.ArrayList;

/**
 * Основной исполняемый файл.
 * На вход принимает список ресурсов типа URL и путь к локальному файлу.
 * Подсчитывает количество повторяющихся слов в файлах и выводит результат в реальном времени.
 * В случает появления слов выходящих за границы обозначенного шаблона прекращает исполнение всех потоков.
 */

public class CountOccurrences {
    private static ArrayList<Thread> threadArrayList = new ArrayList<>(512);
    private static Thread threadPrinter;
    private static volatile boolean interrupted = false;

    public static void main(String[] args) {
        System.out.println("Запуск программы.");
//        String[] args2 = {"test1.txt", "https://vk.com/doc678329_442025940", "https://vk.com/doc678329_442025940"};
//        args = args2;
        threadPrinter = new Thread(Printer.getInstance());
        threadPrinter.start();
        for (String s :
                args) {
            if (Harvester.isStopped()) return;
            Thread thread = new Thread(new Harvester(s));
            threadArrayList.add(thread);
            thread.start();
        }

        for (Thread thread : threadArrayList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Printer.setDone();
        System.out.println("Завершение программы.");
    }


}
