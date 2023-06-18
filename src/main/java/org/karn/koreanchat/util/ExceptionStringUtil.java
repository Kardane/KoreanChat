package org.karn.koreanchat.util;

import eu.pb4.styledchat.StyledChatEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;

public class ExceptionStringUtil {
    private final static KoreanConvertorUtil englishToKorean = new KoreanConvertorUtil();
    public static final boolean STYLED_CHAT = FabricLoader.getInstance().isModLoaded("styledchat");

    public static String getString(String string){
        int index = -1;
        String result = string;
        List<Integer> exceptNum = new ArrayList<>();

        if(!string.contains("\"")){
            result = englishToKorean.engToKor(string);
        }
        else{
            //""의 인덱스 위치를 구합니다.
            while((index = string.indexOf("\"", index + 1)) >= 0) {
                exceptNum.add(index);
            }


            List<String> ExceptString = new ArrayList<>();
            //구한 인덱스를 가지고 "{context}"에서 context를 구합니다.
            for(int i = 0;i < exceptNum.size() - 1; i += 2){
                int StartExecpt = exceptNum.get((i));
                int EndExecpt = exceptNum.get((i + 1));

                String extractString = string.substring(StartExecpt + 1,EndExecpt);
                //context를 리스트에 담습니다.
                if(!extractString.isEmpty() || !extractString.isBlank()){
                    ExceptString.add(extractString);
                }
            }
            //글자를 번역합니다
            String translateString = englishToKorean.engToKor(string);
            /*
             * 여기부턴 글자를 바꾸는 역활을 합니다.
             * */
            //안에 내용 클리어
            exceptNum.clear();
            while((index = translateString.indexOf("\"", index + 1)) >= 0) {
                exceptNum.add(index);
            }
            //
            for(int i = 0;i < exceptNum.size() - 1; i += 2){
                int StartExecpt = exceptNum.get((i));
                int EndExecpt = exceptNum.get((i + 1));

                String extractString = translateString.substring(StartExecpt + 1,EndExecpt);
                //context를 리스트에 담습니다.
                try{
                    result = translateString.replace(String.format("\"%s\"",extractString),ExceptString.get(i));
                }
                catch (IndexOutOfBoundsException E){
                    return result;
                }
            }
        }
        return result;
    }
}

