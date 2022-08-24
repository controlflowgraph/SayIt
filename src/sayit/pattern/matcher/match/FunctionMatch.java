package sayit.pattern.matcher.match;

import sayit.eval.EvaluationEnvironment;

import java.util.function.Function;

public class FunctionMatch implements Match
{
    private final Function<EvaluationEnvironment, Object> function;

    public FunctionMatch(Function<EvaluationEnvironment, Object> function)
    {
        this.function = function;
    }


    @Override
    public Object compute(EvaluationEnvironment environment)
    {
        return this.function.apply(environment);
    }
}
