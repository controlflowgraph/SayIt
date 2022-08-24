package sayit.pattern.matcher;

import sayit.code.TokenSupplier;
import sayit.pattern.matcher.match.Match;

import java.util.List;

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
        while(supplier.has())
        {
            builder.append(supplier.next()).append(' ');
        }
        System.out.println("ERROR: " + builder);

        return null;
    }
}
