package org.wtiger.inno.lab1var1;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сборщик слов из ресурсов
 */
class Harvester implements Runnable {
    private static final String FILE_VALIDATING_REGULAR;
    private static final String URL_VALIDATING_REGULAR;

    static {
        URL_VALIDATING_REGULAR = "^((http:\\/\\/)|(https:\\/\\/)).{4,}$";
    }

    static {
        FILE_VALIDATING_REGULAR = "^((((\\/)|(.\\/))|([a-zA-Z]:\\\\)).{1,})|(.+\\.txt)$";
    }

    private static final Pattern P_FILE = Pattern.compile(FILE_VALIDATING_REGULAR);
    private static final Pattern P_URL = Pattern.compile(URL_VALIDATING_REGULAR);
    private static volatile boolean stopped = false;
    private String fileOrLink;

    enum typeOfLink {URL, FILE, UNKNOWN}

    Harvester(String fileOrLink) {
        this.fileOrLink = fileOrLink;
    }

    @Override
    public void run() {
        try {
            if (isAnURL(fileOrLink)) {
                readFromURL(fileOrLink);
            } else if (isAFilePath(fileOrLink)) {

                readFromFile(fileOrLink);
            } else {
                Harvester.stop();
                Printer.stop("Формат файла не удалось определить: "+fileOrLink);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAnURL(String string) {
        Matcher m = P_URL.matcher(string);
        return m.matches();
    }

    public boolean isAFilePath(String string) {
        Matcher m = P_FILE.matcher(string);
        return m.matches();
    }

    private void readFromFile(String path) throws IOException {
        if (Harvester.isStopped()) return;
        try (FileReader fr = new FileReader(new File(path))) {
            int ch;
            String word;
            Parser p = new Parser();
            while ((ch = fr.read()) != -1) {
                if (Harvester.isStopped()) break;
                if ((word = p.nextChar((char) ch)) != null) {
                    if (Parser.validate(word)){
                        HashMapKeeper.putWordSomeWhere(word);
                    } else {
                        Harvester.stop();
                        Printer.stop("---- Внимание!!! ----"+"\n"
                                     +"Недопустимое слово: "+word+"\n"
                                     +"Ресурс: "+path);
                    }
                }
            }
        }
    }

    private void readFromURL(String path) throws IOException {
        URL url = new URL(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            int ch;
            String word;
            Parser p = new Parser();
            while ((ch = reader.read()) != -1) {
                if ((word = p.nextChar((char) ch)) != null) {
                    HashMapKeeper.putWordSomeWhere(word);
                }
            }
        }
    }

    private static void stop() {
        stopped = true;
    }

    static boolean isStopped() {
        return stopped;
    }
}
