import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import life.knowledge4.romanization.ChineseString;
import life.knowledge4.romanization.PinYinTable;

import static org.junit.Assert.assertArrayEquals;

public class ChineseStringTest {

    @Before
    public void initializePinYin() throws IOException {
        // Only the ones used in this test suite
        String src = "亂=luan4\n哩=li1\n呈=cheng2\n呓=yi4\n员=yuan2,yun2,yun4\n呛=qiang1,qiang4\n";
        ByteArrayInputStream bais = new ByteArrayInputStream(src.getBytes("UTF-8"));
        PinYinTable.initialize(bais);
    }

    private static void assertStringsEquals(String[] value, String... expected) {
        assertArrayEquals(value, expected);
    }

    @Test
    public void testRomanizedNoChineseChars() {
        assertStringsEquals(new ChineseString("abcdef").getRomanized(), "abcdef");
        assertStringsEquals(new ChineseString("Hi there").getRomanized(), "Hi there");
    }

    @Test
    public void testRomanizedChineseSingleChar() {
        assertStringsEquals(new ChineseString("亂").getRomanized(), "luan4");
        assertStringsEquals(new ChineseString("哩").getRomanized(), "li1");
    }

    @Test
    public void testRomanizedMixedChars() {
        assertStringsEquals(new ChineseString("This is luan: 亂").getRomanized(), "This is luan: luan4");
    }


    @Test
    public void testRomanizedChineseSingleCharNoTone() {
        assertStringsEquals(new ChineseString("亂").getRomanized(ChineseString.MODE_NO_TONES), "luan");
        assertStringsEquals(new ChineseString("哩").getRomanized(ChineseString.MODE_NO_TONES), "li");
    }

    @Test
    public void testRomanizedChineseTwoChars() {
        assertStringsEquals(new ChineseString("亂哩").getRomanized(), "luan4 li1");
        assertStringsEquals(new ChineseString("呈呓").getRomanized(), "cheng2 yi4");
    }

    @Test
    public void testRomanizedChineseTwoCharsDelimited() {
        assertStringsEquals(new ChineseString("亂哩").getRomanized("-"), "luan4-li1");
        assertStringsEquals(new ChineseString("呈呓").getRomanized("-"), "cheng2-yi4");
    }

    @Test
    public void testRomanizedChineseTwoCharsNoTone() {
        assertStringsEquals(new ChineseString("亂哩").getRomanized(ChineseString.MODE_NO_TONES), "luan li");
        assertStringsEquals(new ChineseString("呈呓").getRomanized(ChineseString.MODE_NO_TONES), "cheng yi");
    }

    @Test
    public void testRomanizedMultipleSounds() {
        assertStringsEquals(new ChineseString("员呛").getRomanized(""),
                "yuan2qiang1",
                "yuan2qiang4",
                "yun2qiang1",
                "yun2qiang4",
                "yun4qiang1",
                "yun4qiang4");
    }

    @Test
    public void testRomanizedMultipleSoundsNoTone() {
        assertStringsEquals(new ChineseString("员呛").getRomanized(ChineseString.MODE_NO_TONES, ""),
                "yuanqiang",
                "yunqiang");
    }
}
