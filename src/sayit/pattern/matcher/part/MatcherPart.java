package sayit.pattern.matcher.part;

import sayit.code.TokenSupplier;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.MatcherContext;
import sayit.pattern.matcher.PlaceholderCollector;

import java.util.List;
import java.util.Map;

public interface MatcherPart
{
    default Map<String, List<Match>> match(MatcherContext context, TokenSupplier supplier)
    {
        throw new RuntimeException("Not implemented!");
    }

    default void collect(PlaceholderCollector collector)
    {
        throw new RuntimeException("Not implemented!");
    }
}
