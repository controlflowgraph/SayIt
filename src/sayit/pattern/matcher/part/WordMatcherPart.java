package sayit.pattern.matcher.part;

import sayit.code.TokenSupplier;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.MatcherContext;
import sayit.pattern.matcher.PlaceholderCollector;

import java.util.List;
import java.util.Map;

public record WordMatcherPart(String word) implements MatcherPart
{
    @Override
    public void collect(PlaceholderCollector collector)
    {

    }

    @Override
    public Map<String, List<Match>> match(MatcherContext context, TokenSupplier supplier)
    {
        if(!supplier.has())
            return null;

        if(supplier.next().equals(this.word))
            return Map.of();
        return null;
    }
}
