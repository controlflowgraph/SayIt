package sayit.eval.argument;

import sayit.eval.EvaluationEnvironment;

public class EmptyArgument implements Argument
{
    @Override
    public Object compute(EvaluationEnvironment environment)
    {
        return null;
    }
}
