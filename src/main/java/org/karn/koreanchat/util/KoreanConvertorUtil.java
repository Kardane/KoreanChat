package org.karn.koreanchat.util;
import java.util.Arrays;
        import java.util.List;

public class KoreanConvertorUtil {
    enum CodeType {
        chosung, jungsung, jongsung
    }

    public static class EngChar {
        private List<String> possibleList;

        public EngChar(List<String> list) {
            possibleList = list;
        }

        public boolean isMyball(String eng) {
            if (possibleList == null) {
                return false;
            }
            for (String s : possibleList) {
                if (s.equals(eng)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final String jauems = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ";
    private static final String mouems = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ";

    // 초성
    private static final List<EngChar> initial = Arrays.asList(
            new EngChar(Arrays.asList("r")),
            new EngChar(Arrays.asList("R")),
            new EngChar(Arrays.asList("s", "S")),
            new EngChar(Arrays.asList("e")),
            new EngChar(Arrays.asList("E")),
            new EngChar(Arrays.asList("f", "F")),
            new EngChar(Arrays.asList("a", "A")),
            new EngChar(Arrays.asList("q")),
            new EngChar(Arrays.asList("Q")),
            new EngChar(Arrays.asList("t")),
            new EngChar(Arrays.asList("T")),
            new EngChar(Arrays.asList("d", "D")),
            new EngChar(Arrays.asList("w")),
            new EngChar(Arrays.asList("W")),
            new EngChar(Arrays.asList("c", "C")),
            new EngChar(Arrays.asList("z", "Z")),
            new EngChar(Arrays.asList("x", "X")),
            new EngChar(Arrays.asList("v", "V")),
            new EngChar(Arrays.asList("g", "G"))

    );
    // 중성
    private static final List<EngChar> mid = Arrays.asList(
            new EngChar(Arrays.asList("k", "K")),
            new EngChar(Arrays.asList("o")),
            new EngChar(Arrays.asList("i", "I")),
            new EngChar(Arrays.asList("O")),
            new EngChar(Arrays.asList("j", "J")),
            new EngChar(Arrays.asList("p")),
            new EngChar(Arrays.asList("u", "U")),
            new EngChar(Arrays.asList("P")),
            new EngChar(Arrays.asList("h", "H")),
            new EngChar(Arrays.asList("hk", "HK", "hK", "Hk")),
            new EngChar(Arrays.asList("ho", "Ho")),
            new EngChar(Arrays.asList("hl", "HL", "Hl", "hL")),
            new EngChar(Arrays.asList("y", "Y")),
            new EngChar(Arrays.asList("n", "N")),
            new EngChar(Arrays.asList("nj", "NJ", "nJ", "Nj")),
            new EngChar(Arrays.asList("np", "Np")),
            new EngChar(Arrays.asList("nl", "NL", "Nl", "nL")),
            new EngChar(Arrays.asList("b", "B")),
            new EngChar(Arrays.asList("m", "M")),
            new EngChar(Arrays.asList("ml", "ML", "Ml", "mL")),
            new EngChar(Arrays.asList("l", "L"))
    );
    // 종성
    private static final List<EngChar> fin = Arrays.asList(
            new EngChar(Arrays.asList("r")),
            new EngChar(Arrays.asList("R")),
            new EngChar(Arrays.asList("rt")),
            new EngChar(Arrays.asList("s", "S")),
            new EngChar(Arrays.asList("sw", "Sw")),
            new EngChar(Arrays.asList("sg", "SG", "Sg", "sG")),
            new EngChar(Arrays.asList("e")),
            new EngChar(Arrays.asList("f", "F")),
            new EngChar(Arrays.asList("fr", "Fr")),
            new EngChar(Arrays.asList("fa", "Fa", "FA", "fA")),
            new EngChar(Arrays.asList("fq", "Fq")),
            new EngChar(Arrays.asList("ft", "Ft")),
            new EngChar(Arrays.asList("fx", "Fx", "FX", "fX")),
            new EngChar(Arrays.asList("fv", "FV", "Fv", "fV")),
            new EngChar(Arrays.asList("fg", "FG", "Fg", "fG")),
            new EngChar(Arrays.asList("a", "A")),
            new EngChar(Arrays.asList("q")),
            new EngChar(Arrays.asList("qt")),
            new EngChar(Arrays.asList("t")),
            new EngChar(Arrays.asList("T")),
            new EngChar(Arrays.asList("d", "D")),
            new EngChar(Arrays.asList("w")),
            new EngChar(Arrays.asList("c", "C")),
            new EngChar(Arrays.asList("z", "Z")),
            new EngChar(Arrays.asList("x", "X")),
            new EngChar(Arrays.asList("v", "V")),
            new EngChar(Arrays.asList("g", "G"))
    );

    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    /**
     * 영어를 한글로...
     */
    public String engToKor(String eng) {
        StringBuffer sb = new StringBuffer();
        int initialCode = 0, medialCode = 0, finalCode = 0;
        int tempMedialCode, tempFinalCode;

        for (int i = 0; i < eng.length(); i++) {

            // 특수문자를 검사
            if (!isAlpha(eng.substring(i, i + 1))) {
                sb.append(eng.substring(i, i + 1));
                continue;
            }

            // 초성코드 추출
            initialCode = getCode(CodeType.chosung, eng.substring(i, i + 1));
            if (initialCode >= 0) {
                i++; // 다음문자로
            }

            // 중성코드 추출
            tempMedialCode = getDoubleMedial(i, eng);   // 두 자로 이루어진 중성코드 추출
            if (tempMedialCode >= 0) {
                medialCode = tempMedialCode;
                i += 2;
            } else {            // 없다면,
                medialCode = getSingleMedial(i, eng);   // 한 자로 이루어진 중성코드 추출
                if (medialCode >= 0) {
                    i++;
                }
            }


            // 종성코드 추출
            if (initialCode < 0 || medialCode < 0) {
                //종성코드고 자시고 초성이나 중성이 없으면 소용이 없다.
                i--;
                finalCode = -1;
            } else {
                tempFinalCode = getDoubleFinal(i, eng);    // 두 자로 이루어진 종성코드 추출
                if (tempFinalCode >= 0) {
                    finalCode = tempFinalCode;
                    // 그 다음의 중성 문자에 대한 코드를 추출한다.
                    tempMedialCode = getSingleMedial(i + 2, eng);
                    if (tempMedialCode >= 0) {      // 코드 값이 있을 경우
                        finalCode = getSingleFinal(i, eng);   // 종성 코드 값을 저장한다.
                    } else {
                        i++;
                    }
                } else {            // 코드 값이 없을 경우 ,
                    tempMedialCode = getSingleMedial(i + 1, eng);  // 그 다음의 중성 문자에 대한 코드 추출.
                    if (tempMedialCode >= 0) {      // 그 다음에 중성 문자가 존재할 경우,
                        finalCode = -1;        // 종성 문자는 없음.
                        i--;
                    } else {
                        if (i < eng.length() && !isAlpha(eng.substring(i, i + 1))) { // 다음글자가 특수문자일 경우 종성문자는 없음
                            finalCode = -1;
                            i--;
                        }
                        finalCode = getSingleFinal(i, eng);   // 종성 문자 추출
                    }
                }
            }
            if (initialCode >= 0 && medialCode >= 0) {
                // 추출한 초성 문자 코드, 중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
                sb.append((char) (0xAC00 + initialCode * 21 * 28 + medialCode * 28 + finalCode + 1));
            } else if (initialCode >= 0 && medialCode < 0) {
                //초성만
                sb.append(jauems.substring(initialCode, initialCode + 1));
            } else if (initialCode < 0 && medialCode >= 0) {
                //중성만
                sb.append(mouems.substring(medialCode, medialCode + 1));
            } else {
                //존재하나 이런경우가??
                sb.append(eng.substring(i, i + 1));
            }
        }
        return sb.toString();
    }

    /**
     * EngChar리스트에서 String에 해당하는 EngChar의 index를 리턴한다.
     *
     * @param list  EngChar리스트
     * @param c     해당 문자
     */
    private int getEngCharIndex(List<EngChar> list, String c) {
        if (list == null) {
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            EngChar ec = list.get(i);
            if (ec.isMyball(c)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 해당 문자에 따른 코드를 추출한다.
     *
     * @param type 초성 : chosung, 중성 : jungsung, 종성 : jongsung 구분
     * @param c 해당 문자
     */
    private int getCode(CodeType type, String c) {
        switch (type) {
            case chosung:
                return getEngCharIndex(initial, c);
            case jungsung:
                return getEngCharIndex(mid, c);
            case jongsung:
                return getEngCharIndex(fin, c);
            default:
                System.out.println("잘못된 타입 입니다");
        }

        return -1;
    }

    // 한 자로 된 중성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    private int getSingleMedial(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode(CodeType.jungsung, eng.substring(i, i + 1));
        } else {
            return -1;
        }
    }

    // 두 자로 된 중성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    private int getDoubleMedial(int i, String eng) {
        int result;
        if ((i + 2) > eng.length()) {
            return -1;
        } else {
            return getCode(CodeType.jungsung, eng.substring(i, i + 2));
        }
    }

    // 한 자로된 종성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    private int getSingleFinal(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode(CodeType.jongsung, eng.substring(i, i + 1));
        } else {
            return -1;
        }
    }

    // 두 자로된 종성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    private int getDoubleFinal(int i, String eng) {
        if ((i + 2) > eng.length()) {
            return -1;
        } else {
            return getCode(CodeType.jongsung, eng.substring(i, i + 2));
        }
    }
}