package sayit.pattern.matcher;

import sayit.code.TokenSupplier;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.part.ExpressionMatcherPart;

import java.util.List;
import java.util.Map;

public class MatcherContext
{
    private final List<PatternMatcher> matchers;

    public MatcherContext(List<PatternMatcher> matchers)
    {
        this.matchers = matchers;
    }

    public Match match(TokenSupplier supplier)
    {
        int start = supplier.get();
        for (PatternMatcher matcher : this.matchers)
        {
            supplier.set(start);
            Match match = matcher.tryMatch(this, supplier);
            if(match != null && !supplier.has())
            {
                return match;
            }
        }

        StringBuilder builder = new StringBuilder();
        supplier.set(start);
        int remaining = 0;
        while(supplier.has())
        {
            supplier.next();
            remaining++;
        }

        supplier.set(start);
        if(remaining == 1)
        {
            Map<String, List<Match>> v = new ExpressionMatcherPart("$").match(this, supplier);
            if(v != null)
            {
                return v.get("$").get(0);
            }
        }

        supplier.set(start);
        while(supplier.has())
        {
            builder.append(supplier.next()).append(' ');
        }
        System.out.println("ERROR: " + builder);

        return null;
    }
}
