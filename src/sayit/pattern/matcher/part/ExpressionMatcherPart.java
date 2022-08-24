package sayit.pattern.matcher.part;

import sayit.code.TokenSupplier;
import sayit.code.TokenUtil;
import sayit.pattern.matcher.match.FunctionMatch;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.MatcherContext;
import sayit.pattern.matcher.PlaceholderCollector;

import java.util.List;
import java.util.Map;

public class ExpressionMatcherPart implements MatcherPart
{
    private final String name;
    public ExpressionMatcherPart(String name)
    {
        this.name = name;
    }

    @Override
    public void collect(PlaceholderCollector collector)
    {
        collector.add(this.name);
    }

    @Override
    public Map<String, List<Match>> match(MatcherContext context, TokenSupplier supplier)
    {
        if(!supplier.has())
            return null;

        String next = supplier.next();
        if(next.equals("("))
        {
            List<String> strings = TokenUtil.collectUntilEnd(")", supplier);
            if(strings.size() == 1)
            {
                return match(strings.get(0));
            }
            else
            {
                Match match = context.match(new TokenSupplier(strings));
                if(match == null)
                    return null;
                return Map.of(this.name, List.of(match));
            }
        }
        else
        {
            return match(next);
        }
    }

    private Map<String, List<Match>> match(String str)
    {
        if(str.matches("\\d+(\\.\\d+)?"))
        {
            Match match = new FunctionMatch(e -> Double.parseDouble(str));
            return Map.of(this.name, List.of(match));
        }
        else
        {
            Match match = new FunctionMatch(e -> e.get(str));
            return Map.of(this.name, List.of(match));
        }
    }
}
