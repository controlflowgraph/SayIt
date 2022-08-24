package sayit.eval.argument;

import sayit.eval.EvaluationEnvironment;
import sayit.pattern.matcher.match.Match;

public class SingleArgument implements Argument
{
    private final Match match;

    public SingleArgument(Match match)
    {
        this.match = match;
    }

    @Override
    public Object compute(EvaluationEnvironment environment)
    {
        return this.match.compute(environment);
    }
}
