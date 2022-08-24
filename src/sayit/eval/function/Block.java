package sayit.eval.function;

import sayit.eval.EvaluationEnvironment;
import sayit.pattern.matcher.match.Match;

import java.util.List;

public class Block
{
    private final EvaluationEnvironment environment;
    private final List<Match> matches;
    public Block(EvaluationEnvironment environment, List<Match> matches)
    {
        this.environment = environment;
        this.matches = matches;
    }

    public void run()
    {
        for (Match match : this.matches)
        {
            match.compute(this.environment);
        }
    }

    public EvaluationEnvironment getEnvironment()
    {
        return this.environment;
    }
}
