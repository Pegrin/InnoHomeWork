package org.wtiger.inno.lab1var1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Основной исполняемый класс.
 * На вход принимает список ресурсов типа URL и пути к локальному файлу.
 * Подсчитывает количество повторяющихся слов в файлах и выводит результат в реальном времени.
 * В случает появления слов выходящих за границы обозначенного шаблона прекращает исполнение всех потоков.
 *
 * @author Marat Khayrutdinov
 */

public class CountOccurrences {

    private static final Logger logger = Logger.getLogger(CountOccurrences.class);
    static {
        DOMConfigurator.configure("src/resources/log_conf.xml");
    }

    private static ArrayList<Thread> threadArrayList = new ArrayList<>(512);
    private static Thread threadPrinter;
    private static volatile boolean interrupted = false;

    /**
     * Эта функция руководит процессом парсинга оказанных ресурсов.
     *
     * @param args На вход подается набор строк, каждая из которых URL или локальный путь к файлу.
     */
    public static void main(String[] args) {
        logger.info("Запуск задачи. Переданно входных строк: "+args.length);
        if (args.length == 0) {
            logger.info("Нет параметров на входе, запуск тестового набора.");
            args = new String[]{"test1.txt", "test2.txt", "test3.txt", "test4.txt",
                    "https://vk.com/doc678329_442025940", "https://vk.com/doc678329_442025940"};
        }
        threadPrinter = new Thread(Printer.getInstance());
        threadPrinter.start();
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (String s :
                args) {
            if (Harvester.isStopped()) return;
            Thread thread = new Thread(new Harvester(s));
            thread.setName(s);
            threadArrayList.add(thread);
            es.execute(thread);
        }
        es.shutdown();
        try {
            es.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.error("Ошибка прерывания основного потока! Странная фигня.", e);
        }
        Printer.setDone();

        logger.info("Все потоки завершены. Завершение выполнения программы.");
    }
}
