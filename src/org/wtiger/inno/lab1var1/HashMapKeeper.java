package org.wtiger.inno.lab1var1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс хранит хэшмапы (внезапно) со словами и данным о том,
 * сколько раз эти слова встретились в обработанных текстах.
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

    /**
     * Помещает переданное слово в одно из хранилищ, пересчитывает,
     * сколько раз слово встретилось, и передает информацию классу Printer.
     *
     * @param word Слово, которое необходимо занести в хранилище
     */
    static void putWordSomeWhere(String word) {
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
