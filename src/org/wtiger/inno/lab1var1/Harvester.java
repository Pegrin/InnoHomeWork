package org.wtiger.inno.lab1var1;

import java.io.BufferedReader;
import java.util.regex.Pattern;

/**
 * Created by olymp on 09.02.2017.
 */
 class Harvester implements Runnable {
    private static final String FILE_VALIDATING_REGULAR = "^((http:\\/\\/)|(https:\\/\\/))[\\w.]{4,}$";
    private static final Pattern P_FILE = Pattern.compile(FILE_VALIDATING_REGULAR);
    private static final String URL_VALIDATING_REGULAR = "^(/)|(./)[\\w.]{1,}$";
    private static final Pattern P_URL = Pattern.compile(FILE_VALIDATING_REGULAR);
    private static volatile boolean stopped = false;
    private String fileOrLink;
    enum typeOfLink {URL, FILE, UNKNOWN}

    Harvester(String fileOrLink) {
        this.fileOrLink = fileOrLink;
    }

    @Override
    public void run() {

    }

    private void readFromFile(){

    }

    private void  readFromURL(){

    }

    public static void stop() {
        stopped = true;
    }

    public static boolean isStopped() {
        return stopped;
    }
}
