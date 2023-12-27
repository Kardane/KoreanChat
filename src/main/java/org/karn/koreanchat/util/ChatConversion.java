package org.karn.koreanchat.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatConversion {
    private final static KoreanConvertorUtil englishToKorean = new KoreanConvertorUtil();

    private static boolean hasKorAndEng(String string){
        boolean hasKorean = false;
        boolean hasEnglish = false;

        for (char c : string.toCharArray()) {
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES
                    || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_JAMO) {
                hasKorean = true;
            } else if (Character.isLetter(c)) {
                hasEnglish = true;
            }

            if (hasKorean && hasEnglish) {
                break;
            }
        }

        return hasKorean && hasEnglish;
    };

    public static String convertChat(String string){
        if(hasKorAndEng(string))
            return string;

        if(!string.contains("\""))
            return englishToKorean.engToKor(string);

        List<String> list = Arrays.stream(string.split("\"")).toList();
        String finalString = "";
        for(var i=0;i<list.size();i++){
            if(i%2==0)
                finalString += englishToKorean.engToKor(list.get(i));
            else
                finalString += list.get(i);
        }
        return finalString;
    };
}
