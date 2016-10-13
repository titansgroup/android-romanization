# android-romanization

This library adds functions to romanize (convert a different character set into
a Latin alphabet phonetic representation) strings from all languages.

It is mainly intended for comparison of **Speech Recognition** results (which
Android returns as Unicode characters) with their Latin romanized form.

## Supported languages

Currently, only Chinese characters are supported (in the form of Pinyin).

The Unicode-Pinyin table can be found [here](ftp://ftp.cuhk.hk/pub/chinese/ifcss/software/data/Uni2Pinyin.gz).

## How to use

1. Build this repository into a .aar library
2. In your app's `build.gradle` file, include the following line in the
`dependencies` group:

    ```
dependencies {
    compile(name: 'android-romanization-0.1', ext: 'aar')
```

3. Use the class `ChineseString` to obtain the string in its romanized form:

    ```
> ChineseString("人").getRomanized()
{"ren2"}
```

    Another possibility is to return the string without the tone indicator:

    ```
> ChineseString("人").getRomanized(ChineseString.MODE_NO_TONES)
{"ren"}
```

    Please note that, because some characters may have different pronunciations,
    and thus may be represented by different Latin strings, the function will
    return all possible values. Thus, you shouldn't create a `ChineseString`
    instance with too many characters.

    ```
> ChineseString("中文").getRomanized()
{"zhong1 wen2",
 "zhong1 wen4",
 "zhong4 wen2",
 "zhong4 wen4"}
```

## TODO (contributions accepted)

* Pinyin diacriticals (ā, á, ǎ, à)
* More intelligent string romanization
* Other alphabets and languages
