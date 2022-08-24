package sayit.pattern;

import sayit.code.TokenSupplier;
import sayit.code.TokenUtil;
import sayit.pattern.matcher.Matcher;
import sayit.pattern.matcher.PlaceholderCollector;
import sayit.pattern.matcher.part.*;

import java.util.ArrayList;
import java.util.List;

public class PatternParser
{
    public static Matcher parse(String str)
    {
        return new PatternParser().parse(PatternTokenizer.tokenize(str));
    }

    private Matcher parse(List<String> token)
    {
        MultiMatcherPart part = parseMultiPart(new TokenSupplier(token));
        PlaceholderCollector collector = new PlaceholderCollector();
        part.collect(collector);
        List<Placeholder> lst = collector.order();
        return new Matcher(lst, part);
    }

    private MultiMatcherPart parseMultiPart(TokenSupplier supplier)
    {
        List<MatcherPart> parts = new ArrayList<>();
        while(supplier.has())
        {
            parts.add(parsePart(supplier));
        }
        return new MultiMatcherPart(parts);
    }

    private MatcherPart parsePart(TokenSupplier supplier)
    {
        String current = supplier.peek();
        if(current.equals("("))
        {
            return parseGroup(supplier);
        }
        else if(current.startsWith("'"))
        {
            return parseVariable(supplier);
        }
        else if(current.startsWith("!"))
        {
            return parseExpression(supplier);
        }
        else if(current.startsWith("#"))
        {
            return parseBlock(supplier);
        }
        else
        {
            return parseWord(supplier);
        }
    }

    private MatcherPart parseExpression(TokenSupplier supplier)
    {
        return new ExpressionMatcherPart(supplier.next());
    }

    private MatcherPart parseBlock(TokenSupplier supplier)
    {
        return new BlockMatcherPart(supplier.next());
    }

    private MatcherPart parseVariable(TokenSupplier supplier)
    {
        return new VariableMatcherPart(supplier.next());
    }

    private RepetitionMatcherPart parseGroup(TokenSupplier supplier)
    {
        supplier.next();
        Repetition repetition = Repetition.getRepetition(supplier.next());
        MatcherPart part = parseMultiPart(new TokenSupplier(TokenUtil.collectUntilEnd(")", supplier)));
        return new RepetitionMatcherPart(repetition, part);
    }

    private WordMatcherPart parseWord(TokenSupplier supplier)
    {
        return new WordMatcherPart(supplier.next());
    }
}
