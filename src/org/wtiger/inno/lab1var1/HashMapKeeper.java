package org.wtiger.inno.lab1var1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Хранит хэшмапы (внезапно) со словами.
 */
class HashMapKeeper {
    private HashMap<String, Integer> foundWords = new HashMap<>(128);
    private static ArrayList<HashMapKeeper> keepers;
    static {
        keepers = new ArrayList<>(8);
        HashMapKeeper keeper = new HashMapKeeper();
        for (int i = 1; i <= 2; i++) keepers.add(keeper);
        for (int i = 3; i <= 5; i++) keepers.add(new HashMapKeeper());
        keeper = new HashMapKeeper();
        for (int i = 6; i <= 7; i++) keepers.add(keeper);
        keepers.add(new HashMapKeeper()); // 8й
    }

    public static void putWordSomeWhere(String word) {
        int wordSize = word.length();
        HashMapKeeper keeper = keepers.get((wordSize > keepers.size()) ? keepers.size() - 1 : wordSize - 1);
        Printer.addStr(keeper.putWord(word));
    }

    private synchronized String putWord(String word) {
        Integer curCounter = foundWords.get(word);
        curCounter = (curCounter == null) ? 1 : ++curCounter;
        foundWords.put(word, curCounter);
        return word + " - " + curCounter;
    }
}
