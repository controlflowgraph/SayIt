package sayit.eval.argument;

import sayit.eval.EvaluationEnvironment;
import sayit.pattern.matcher.match.Match;

import java.util.ArrayList;
import java.util.List;

public class MultiArgument implements Argument
{
    private final List<Match> matches;

    public MultiArgument(List<Match> matches)
    {
        this.matches = matches;
    }

    @Override
    public Object compute(EvaluationEnvironment environment)
    {
        List<Object> arguments = new ArrayList<>();
        for (Match match : this.matches)
        {
            arguments.add(match.compute(environment));
        }
        return arguments;
    }
}
