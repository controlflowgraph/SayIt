package sayit.eval.argument;

import sayit.eval.EvaluationEnvironment;

import java.lang.reflect.InvocationTargetException;

public interface Argument
{
    Object compute(EvaluationEnvironment environment);
}
