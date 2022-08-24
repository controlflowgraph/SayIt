package sayit.pattern.matcher.part;

import sayit.code.TokenSupplier;
import sayit.code.TokenUtil;
import sayit.eval.location.Variable;
import sayit.pattern.matcher.match.FunctionMatch;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.MatcherContext;
import sayit.pattern.matcher.PlaceholderCollector;

import java.util.List;
import java.util.Map;

public class VariableMatcherPart implements MatcherPart
{
    private final String name;
    public VariableMatcherPart(String name)
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
                Match match = new FunctionMatch(e -> new Variable(e, strings.get(0)));
                return Map.of(this.name, List.of(match));
            }
            return null;
        }
        else
        {
            Match match = new FunctionMatch(e -> new Variable(e, next));
            return Map.of(this.name, List.of(match));
        }
    }
}
