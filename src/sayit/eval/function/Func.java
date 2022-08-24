package sayit.eval.function;

import sayit.eval.EvaluationEnvironment;
import sayit.eval.location.Location;
import sayit.eval.location.Variable;

import java.util.List;

public class Func
{
    private final List<Variable> locations;
    private final Block body;

    public Func(List<Variable> locations, Block body)
    {
        this.locations = locations;
        this.body = body;
    }

    public Object compute(List<Object> values)
    {
        if(this.locations.size() != values.size())
            throw new RuntimeException("Mismatching function parameters!");

        // TODO: ensure that you are popping all scopes until the scope that has been pushed here
        this.body.getEnvironment().push();
        Object result = null;
        for (int i = 0; i < values.size(); i++)
        {
            this.locations.get(i).set(values.get(i));
        }
        try
        {
            this.body.run();
        }
        catch (ReturnValueException e)
        {
            result = e.getValue();
        }
        this.body.getEnvironment().pop();
        return result;
    }
}
