package sayit.code;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeTokenizer
{
    private static final List<Pattern> CATEGORIES = List.of(
            Pattern.compile("\"(\\\\.|[^\"])*\""),
            Pattern.compile("'(\\\\.|.)'"),
            Pattern.compile("[_a-zA-Z][_a-zA-Z0-9-]*"),
            Pattern.compile("\\d+\\.\\d+"),
            Pattern.compile("\\d+"),
            Pattern.compile("[.+\\-*/%&|<=>?!]+"),
            Pattern.compile("[;:,()\\[\\]{}]")
    );
    public static List<String> tokenize(String text)
    {
        return new CodeTokenizer(text).lex();
    }

    private final int last;
    private final List<Matcher> matchers;

    private CodeTokenizer(String text)
    {
        this.last = text.length();
        this.matchers = CATEGORIES.stream()
                .map(c -> c.matcher(text))
                .toList();
    }

    private List<String> lex()
    {
        List<String> tokens = new ArrayList<>();
        int start = 0;
        while(start <= this.last)
        {
            int index = getBestMatcherIndex(start);
            if(index == -1)
                break;
            Matcher matcher = this.matchers.get(index);
            String text = matcher.group();
            tokens.add(text);
            start = matcher.end();
        }
        return tokens;
    }

    private int getBestMatcherIndex(int start)
    {
        int bestStart = Integer.MAX_VALUE;
        int index = -1;
        for(int i = 0; i < this.matchers.size(); i++)
        {
            Matcher matcher = this.matchers.get(i);
            if(matcher.find(start) && matcher.start() < bestStart)
            {
                bestStart = matcher.start();
                index = i;
            }
        }
        return index;
    }
}
