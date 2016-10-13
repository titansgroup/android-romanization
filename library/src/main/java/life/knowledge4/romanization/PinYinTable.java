package life.knowledge4.romanization;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
* Implements the unicode to Pinyin character table that can be found in
* ftp://ftp.cuhk.hk/pub/chinese/ifcss/software/data/Uni2Pinyin.gz
*/
public class PinYinTable {
    private static final String ASSET_FILENAME = "raw/pinyin.properties";
    private static PinYinTable instance;
    private HashMap<Character, String[]> charmap;

    public static void initialize(InputStream is) throws IOException {
        if (instance == null)
            instance = new PinYinTable(is);
    }

    public static void initialize(Context context) throws IOException {
        if (instance == null)
            instance = new PinYinTable(context);
    }

    public static PinYinTable getInstance() throws IOException {
        if (instance == null)
            throw new RuntimeException("PinYinTable not initialized!");
        return instance;
    }

    private PinYinTable(InputStream is) throws IOException {
        this.charmap = new HashMap<>();
        load(is);
    }

    private PinYinTable(Context context) throws IOException {
        this(context.getAssets().open(ASSET_FILENAME));
    }

    private void load(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.length() == 0 || line.charAt(0) == '#')
                continue;
            if (line.indexOf('=') != 1) continue;
            charmap.put(line.charAt(0), line.substring(2).split(","));
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
