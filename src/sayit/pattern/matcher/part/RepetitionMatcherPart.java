package sayit.pattern.matcher.part;


import sayit.code.TokenSupplier;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.MatcherContext;
import sayit.pattern.matcher.PlaceholderCollector;

import java.util.*;

public record RepetitionMatcherPart(Repetition repetition, MatcherPart part) implements MatcherPart
{
    @Override
    public void collect(PlaceholderCollector collector)
    {
        if(this.repetition == Repetition.ARBITRARY || this.repetition == Repetition.MULTIPLE)
        {
            collector.push(this.repetition);
            this.part.collect(collector);
            collector.pop();
        }
        else
        {
            this.part.collect(collector);
        }
    }

    @Override
    public Map<String, List<Match>> match(MatcherContext context, TokenSupplier supplier)
    {
        int repetitions = 0;
        int start = supplier.get();
        Map<String, List<Match>> matches = new HashMap<>();
        boolean matching = true;
        while(matching && supplier.has())
        {
            Map<String, List<Match>> match = this.part.match(context, supplier);
            if(match == null)
            {
                matching = false;
            }
            else
            {
                start = supplier.get();
                match.forEach((a, b) -> matches.computeIfAbsent(a, k -> new ArrayList<>()).addAll(b));
                repetitions++;
            }
        }
        supplier.set(start);
        if(this.repetition != Repetition.MULTIPLE || repetitions > 0)
        {
            return matches;
        }
        return null;
    }
}
