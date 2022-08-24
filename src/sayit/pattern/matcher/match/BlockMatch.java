package sayit.pattern.matcher.match;

import sayit.eval.function.Block;
import sayit.eval.EvaluationEnvironment;

import java.util.List;

public class BlockMatch implements Match
{
    private final List<Match> matches;

    public BlockMatch(List<Match> matches)
    {
        this.matches = matches;
    }

    @Override
    public Object compute(EvaluationEnvironment environment)
    {
        return new Block(environment, this.matches);
    }
}
