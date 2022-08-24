package sayit.pattern.matcher.part;

import sayit.code.TokenSupplier;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.MatcherContext;
import sayit.pattern.matcher.PlaceholderCollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record MultiMatcherPart (List<MatcherPart> parts) implements MatcherPart
{
    @Override
    public void collect(PlaceholderCollector collector)
    {
        this.parts.forEach(p -> p.collect(collector));
    }

    @Override
    public Map<String, List<Match>> match(MatcherContext context, TokenSupplier supplier)
    {
        Map<String, List<Match>> match = new HashMap<>();
        for (MatcherPart part : this.parts)
        {
            Map<String, List<Match>> sub = part.match(context, supplier);
            if(sub == null)
                return null;
            sub.forEach((a, b) -> match.computeIfAbsent(a, k -> new ArrayList<>()).addAll(b));
        }
        return match;
    }
}
