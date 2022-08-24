package sayit.code;

import java.util.Arrays;
import java.util.List;

public class CodeTokenizer
{
    public static List<String> tokenize(String text)
    {
        return Arrays.asList(text
             .replace("(", " ( ")
             .replace(")", " ) ")
             .replace(".", " . ")
             .replace(",", " , ")
             .replaceAll("[ \t\r\n]+", " ")
             .split(" ")
        );
    }

    private CodeTokenizer() { }
}
