package org.wtiger.inno.lab1var1;

import java.util.ArrayList;

/**
 * Основной исполняемый класс.
 * На вход принимает список ресурсов типа URL и пути к локальному файлу.
 * Подсчитывает количество повторяющихся слов в файлах и выводит результат в реальном времени.
 * В случает появления слов выходящих за границы обозначенного шаблона прекращает исполнение всех потоков.
 *
 * @author Marat Khayrutdinov
 */

public class CountOccurrences {
    private static ArrayList<Thread> threadArrayList = new ArrayList<>(512);
    private static Thread threadPrinter;
    private static volatile boolean interrupted = false;

    /**
     * Эта функция руководит процессом парсинга оказанных ресурсов.
     *
     * @param args На вход подается набор строк, каждая из которых URL или локальный путь к файлу.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Нет параметров на входе, запускаю тестовый набор.");
            args = new String[]{"test1.txt", "test2.txt", "test3.txt", "test4.txt",
                    "https://vk.com/doc678329_442025940", "https://vk.com/doc678329_442025940"};
        }
        threadPrinter = new Thread(Printer.getInstance());
        threadPrinter.start();
        for (String s :
                args) {
            if (Harvester.isStopped()) return;
            Thread thread = new Thread(new Harvester(s));
            thread.setName(s);
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
    }
}
