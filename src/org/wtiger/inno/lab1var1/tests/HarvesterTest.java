import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.wtiger.inno.lab1var1.Harvester;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование класса Harvester
 */
class HarvesterTest {
    private static final String PATH_FILE_1 = "test1.txt";
    private static final String PATH_FILE_2 = "/root/test1.txt";
    private static final String PATH_URL_1 = "https://vk.com/doc678329_442025940";
    private static final String PATH_URL_2 = "http://vk.com/doc678329_442025940/";


    @BeforeAll
    static void beforeAll() {
        Harvester harvester = new Harvester(PATH_FILE_1);
        assertNotNull(harvester);
        assertEquals(Harvester.class, harvester.getClass());
    }

    @Test
    void run() {
        Harvester harvester = new Harvester(PATH_FILE_1);
        assertTrue(harvester instanceof Runnable);
        Thread thread = new Thread(harvester);
        thread.run();
        try {
            thread.join();
        } catch (InterruptedException e) {
            assertTrue(false);
        }
        assertTrue(true);
    }

    @Test
    void isAnURL() {
        Harvester harvester = new Harvester(PATH_URL_1);
        assertTrue(Harvester.isAnURL(PATH_URL_1));
        assertTrue(Harvester.isAnURL(PATH_URL_2));
        assertFalse(Harvester.isAnURL(PATH_FILE_1));
        assertFalse(Harvester.isAnURL(PATH_FILE_2));
    }

    @Test
    void isAFilePath() {
        Harvester harvester = new Harvester(PATH_URL_1);
        assertFalse(Harvester.isAFilePath(PATH_URL_1));
        assertFalse(Harvester.isAFilePath(PATH_URL_2));
        assertTrue(Harvester.isAFilePath(PATH_FILE_1));
        assertTrue(Harvester.isAFilePath(PATH_FILE_2));
    }

}