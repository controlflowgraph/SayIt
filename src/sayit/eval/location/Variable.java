package sayit.eval.location;

import sayit.eval.EvaluationEnvironment;

public class Variable implements Location
{
    private final EvaluationEnvironment env;
    private final String name;

    public Variable(EvaluationEnvironment env, String name)
    {
        this.env = env;
        this.name = name;
    }

    @Override
    public void set(Object value)
    {
        this.env.set(this.name, value);
    }
}
