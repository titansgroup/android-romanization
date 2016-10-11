package life.knowledge4.romanization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

/**
* Implements the unicode to Pinyin character table that can be found in
* ftp://ftp.cuhk.hk/pub/chinese/ifcss/software/data/Uni2Pinyin.gz
*/
public class PinYinTable {
    private static final String ASSET_FILENAME = "pinyin.properties";
    private static PinYinTable instance;
    private HashMap<Character, String[]> charmap;

    public static PinYinTable getInstance() throws IOException {
        if (instance == null)
            instance = new PinYinTable();
        return instance;
    }

    private PinYinTable() throws IOException {
        this.charmap = new HashMap<>();
        load(ClassLoader.getSystemResourceAsStream(ASSET_FILENAME));
    }

    private void load(InputStream is) throws IOException {
        Properties p = new Properties();
        p.load(new InputStreamReader(is, "UTF-8"));
        for (String key : p.stringPropertyNames()) {
            charmap.put(key.charAt(0), p.getProperty(key).split(","));
        }
    }

    public boolean isChineseChar(char key) {
        return charmap.containsKey(key);
    }

    public String[] fromChar(char key) {
        try {
            return charmap.get(key).clone();
        } catch (NullPointerException exc) {
            return null;
        }
    }
}
