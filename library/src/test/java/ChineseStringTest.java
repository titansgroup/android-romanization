import org.junit.Test;

import life.knowledge4.romanization.ChineseString;

import static org.junit.Assert.assertArrayEquals;

public class ChineseStringTest {

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
        assertStringsEquals(new ChineseString("亂哩").getRomanized(), "luan4li1");
        assertStringsEquals(new ChineseString("呈呓").getRomanized(), "cheng2yi4");
    }

    @Test
    public void testRomanizedChineseTwoCharsNoTone() {
        assertStringsEquals(new ChineseString("亂哩").getRomanized(ChineseString.MODE_NO_TONES), "luanli");
        assertStringsEquals(new ChineseString("呈呓").getRomanized(ChineseString.MODE_NO_TONES), "chengyi");
    }

    @Test
    public void testRomanizedMultipleSounds() {
        assertStringsEquals(new ChineseString("员呛").getRomanized(),
                "yuan2qiang1",
                "yuan2qiang4",
                "yun2qiang1",
                "yun2qiang4",
                "yun4qiang1",
                "yun4qiang4");
    }

    @Test
    public void testRomanizedMultipleSoundsNoTone() {
        assertStringsEquals(new ChineseString("员呛").getRomanized(ChineseString.MODE_NO_TONES),
                "yuanqiang",
                "yunqiang");
    }
}
