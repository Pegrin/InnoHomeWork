package org.wtiger.inno.lab1var1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Посимвольный парсер
 */

public class Parser {
    private static final String VALIDATING_REGULAR = "^[а-яА-ЯёЁ\\-\u2010\u00AD]+$";
    private static final Pattern P_WORD = Pattern.compile(VALIDATING_REGULAR);
    private static final String D_VALIDATING_REGULAR = "^[1-90\\-–\u2010\u00AD]+$";
    private static final Pattern P_DIGITAL = Pattern.compile(D_VALIDATING_REGULAR);
    private static final String SIGN_WORDS_END = " ,.;:\"'`()?!\n\r\t\f\u00ab\u00bb\u2018\u2019\u201c\u201e";
    private StringBuffer word = new StringBuffer("");

    /**
     * Проверяет слово, на соответствие условиям задания.
     *
     * @param word Передаваемое слово.
     * @return Если слово соответствует, шаблону возвращает true.
     */
    public static boolean validate(String word) {
        Matcher m = P_WORD.matcher(word);
        return m.matches();
    }

    /**
     * Проверяет, является ли слово числом или тире.
     *
     * @param word Передаваемое слово.
     * @return Если слово является числом или тире, возвращает true.
     */
    public static boolean isDigital(String word) {
        Matcher m = P_DIGITAL.matcher(word);
        return m.matches();
    }

    /**
     * Принимает символы до тех пор, пока не соберет готовое слово.
     *
     * @param c Принимаемый символ.
     * @return Возвращает null если слово не готово, иначе возвращает слово.
     */
    public String nextChar(char c) {
        String result = null;
        if (SIGN_WORDS_END.indexOf(c) > -1) {
            if (word.length() > 0) {
                if (!isDigital(word.toString()))
                    result = word.toString().toLowerCase();
                word.setLength(0);
            }
        } else {
            word = word.append(String.valueOf(c));
        }
        return result;
    }

    /**
     * Аналог nextChar(char c), для получения последнего слова, если таковое имеется.
     *
     * @return Возвращает слово, если длинна собираемого слова больше нуля.
     */
    public String nextChar() {
        return word.length() > 0 ? word.toString() : null;
    }
}
