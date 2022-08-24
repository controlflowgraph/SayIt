package sayit.pattern.matcher.part;

import sayit.code.TokenSupplier;
import sayit.code.TokenUtil;
import sayit.eval.function.Block;
import sayit.pattern.matcher.MatcherContext;
import sayit.pattern.matcher.PlaceholderCollector;
import sayit.pattern.matcher.match.BlockMatch;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.part.MatcherPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockMatcherPart implements MatcherPart
{
    private final String name;
    public BlockMatcherPart(String name)
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
        supplier.next();
        List<String> strings = TokenUtil.collectUntilEnd(")", supplier);
        TokenSupplier sup = new TokenSupplier(strings);
        List<Match> matches = new ArrayList<>();
        while(sup.has())
        {
            List<String> lst = TokenUtil.collectUntilEnd(".", sup);
            Match match = context.match(new TokenSupplier(lst));
            if(match == null)
                return null;
            matches.add(match);
        }
        return Map.of(this.name, List.of(new BlockMatch(matches)));
    }
}
