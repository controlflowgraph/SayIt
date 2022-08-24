package sayit.pattern;

import java.util.Arrays;
import java.util.List;

public class PatternTokenizer
{
    public static List<String> tokenize(String pattern)
    {
        return Arrays.asList(pattern
             .replace("(", " ( ")
             .replace(")", " ) ")
             .replace("*", " * ")
             .replace("+", " + ")
             .replace("?", " ? ")
             .replaceAll("[ \t\r\n]+", " ")
             .split(" ")
        );
    }

    private PatternTokenizer() { }
}
