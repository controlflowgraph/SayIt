package sayit.pattern.matcher.match;

import sayit.eval.CustomRuntimeException;
import sayit.eval.argument.Argument;
import sayit.eval.EvaluationEnvironment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MethodMatch implements Match
{
    private final Method method;
    private final List<Argument> arguments;

    public MethodMatch(Method method, List<Argument> arguments)
    {
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Object compute(EvaluationEnvironment environment)
    {
        Object[] args = new Object[this.arguments.size()];
        for (int i = 0; i < this.arguments.size(); i++)
        {
            args[i] = this.arguments.get(i).compute(environment);
        }
        try
        {
            return this.method.invoke(null, args);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("THE ARGUMENTS ARE: ");
            System.out.println(Arrays.toString(args));
            System.out.println(this.method);
            throw new RuntimeException();
        }
        catch (InvocationTargetException e)
        {
            if(e.getCause() instanceof CustomRuntimeException r)
            {
                throw r;
            }

            Class<?>[] arr = new Class[args.length];
            for (int i = 0; i < args.length; i++)
            {
                arr[i] = args[i] != null ? args[i].getClass() : null;
            }
            System.out.println(this.method);
            System.out.println(Arrays.toString(arr));
            System.out.println(Arrays.toString(this.method.getParameterTypes()));

            throw new RuntimeException(e);
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
