import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.wtiger.inno.lab1var1.Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестирование класса Parser
 */
class ParserTest {
    private static final char[][] CHARS = {{'Г', 'д', 'е', '-', 'т', 'о', '\''},
            {'в', 'ы', 'х', 'о', 'д', '-'},
            {'e', 'n', 'd', '!'},
            {'1', '8', '4'}};
    private static final String[] WORDS = {"героизм", "IIV", "1123"};

    @BeforeAll
    static void beforeEach() {
        Parser p = new Parser();
        Assert.assertNotNull(p);
        Assert.assertEquals(Parser.class, p.getClass());
    }

    @org.junit.jupiter.api.Test
    void validate() {
        assertTrue(Parser.validate(WORDS[0]));
        assertFalse(Parser.validate(WORDS[1]));
        assertFalse(Parser.validate(WORDS[2]));
    }

    @org.junit.jupiter.api.Test
    void isDigital() {
        assertFalse(Parser.isDigital(WORDS[0]));
        assertFalse(Parser.isDigital(WORDS[1]));
        assertTrue(Parser.isDigital(WORDS[2]));
    }

    @org.junit.jupiter.api.Test
    void nextChar() {
        Parser p = new Parser();
        for (int i = 0; i < CHARS[0].length - 1; i++) {
            Assert.assertNull(p.nextChar(CHARS[0][i]));
        }
        String s = p.nextChar(CHARS[0][CHARS[0].length - 1]);
        Assert.assertNotNull(s);
        Assert.assertEquals(s, "где-то");
        Assert.assertNull(p.nextChar());
    }

    @org.junit.jupiter.api.Test
    void nextChar1() {
        Parser p = new Parser();
        Assert.assertNull(p.nextChar());
        p.nextChar('а');
        String s = p.nextChar();
        Assert.assertEquals(s, "а");
    }

}