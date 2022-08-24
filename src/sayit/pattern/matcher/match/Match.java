package sayit.pattern.matcher.match;

import sayit.eval.EvaluationEnvironment;

public interface Match
{
    Object compute(EvaluationEnvironment environment);
}
