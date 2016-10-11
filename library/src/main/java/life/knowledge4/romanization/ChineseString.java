package life.knowledge4.romanization;

import java.io.IOException;
import java.util.HashSet;

/**
 * Provides means of romanizing a String with Chinese characters using PinYinTable.
 */

public class ChineseString {
    private String src;
    private PinYinTable pinYinTable;

    public static final int MODE_NUMBERED = 0;
    public static final int MODE_NO_TONES = 1;
    public static final int MODE_ACCENTED = 2;

    public ChineseString( String src) {
        this.src = src;
        try {
            pinYinTable = PinYinTable.getInstance();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.src;
    }

    public String[] fromChar(char key, int mode) {
        String[] value = pinYinTable.fromChar(key);
        if (value == null) {
            throw new IllegalArgumentException("key is not a Chinese character.");
        }

        switch (mode) {
            case MODE_NUMBERED:
                return value;

            case MODE_NO_TONES:
                // Remove numbers from results
                HashSet<String> stringSet = new HashSet<String>();
                for (int i = 0; i < value.length; i++)
                    stringSet.add(value[i].replaceAll("[0-9]", ""));
                // Remove duplicates
                value = new String[stringSet.size()];
                stringSet.toArray(value);
                return value;

            default:
                throw new RuntimeException("Romanization mode not available.");
        }
    }

    public String[] getRomanized() {
        // When available, MODE_ACCENTED should be default
        return getRomanized(MODE_NUMBERED);
    }

    public String[] getRomanized(int mode) {
        Object[] romanized = new Object[this.src.length()];
        // Assembles a list of possibilities which include either a char or an array of Strings
        for (int i = 0; i < this.src.length(); i++) {
            char current = this.src.charAt(i);
            try {
                romanized[i] = fromChar(current, mode);
            } catch (IllegalArgumentException exc) {
                // Non-Chinese character; simply append
                romanized[i] = current;
            }
        }

        return buildResults(romanized, null, 0);
    }

    private String[] buildResults(Object[] list, String[] assembled, int position) {
        if (assembled == null)
            assembled = new String[] {""};
        if (position >= list.length)
            return assembled;

        if (list[position] instanceof String[]) {
            String[] current = (String[]) list[position];
            if (current.length == 0 || (current.length == 1 && current[0].length() == 0)) {
                // No string or empty string: Does nothing
            } else if (current.length == 1) {
                for (int i = 0; i < assembled.length; i++) {
                    assembled[i] += current[0];
                }
            } else {
                String[] temp = new String[assembled.length * current.length];
                int resultPos = 0;
                for (int i = 0; i < assembled.length; i++) {
                    for (int j = 0; j < current.length; j++) {
                        temp[resultPos++] = assembled[i] + current[j];
                    }
                }
                assembled = temp;
            }
        } else {
            char current = (char) list[position];
            for (int i = 0; i < assembled.length; i++) {
                assembled[i] += current;
            }
        }
        return buildResults(list, assembled, position + 1);
    }
}
