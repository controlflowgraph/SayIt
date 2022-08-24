package sayit;

import sayit.eval.function.Block;
import sayit.eval.function.Func;
import sayit.eval.function.ReturnValueException;
import sayit.eval.location.Location;
import sayit.eval.location.Variable;
import sayit.pattern.LanguagePattern;

import java.util.ArrayList;
import java.util.List;

public class PatternCollection
{
    @LanguagePattern("let 'a be equal to !b")
    public static void assignment(Location location, Object value)
    {
        location.set(value);
    }

    @LanguagePattern("!a plus !b")
    public static Number plus(Number a, Number b){ return a.doubleValue() + b.doubleValue(); }

    @LanguagePattern("!a minus !b")
    public static Number minus(Number a, Number b){ return a.doubleValue() - b.doubleValue(); }

    @LanguagePattern("!a times !b")
    public static Number times(Number a, Number b){ return a.doubleValue() * b.doubleValue(); }

    @LanguagePattern("!a divided by !b")
    public static Number divided(Number a, Number b){ return a.doubleValue() / b.doubleValue(); }

    @LanguagePattern("a list containing !a (* , !a) (? and !a)")
    public static List<Object> list(List<Object> object)
    {
        return object;
    }

    @LanguagePattern("the empty list")
    public static List<Object> empty()
    {
        return new ArrayList<>();
    }

    @LanguagePattern("the concatenation of !a (* , !a) and !a")
    public static List<Object> concatenate(List<List<Object>> lists)
    {
        List<Object> result = new ArrayList<>();
        for (List<Object> list : lists)
        {
            result.addAll(list);
        }
        return result;
    }

    @LanguagePattern("print !a")
    public static void print(Object obj)
    {
        System.out.println(obj);
    }

    @LanguagePattern("a function that (? receives 'a (* , 'a) and) does the following #b")
    public static Object function(List<Variable> variables, Block block)
    {
        return new Func(variables, block);
    }


    @LanguagePattern("call !a")
    public static Object call(Func func)
    {
        return func.compute(List.of());
    }

    @LanguagePattern("call !a with !b (+ , !b)")
    public static Object call(Func func, List<Object> arguments)
    {
        return func.compute(arguments);
    }

    @LanguagePattern("return (? !a)")
    public static void ret(Object value)
    {
        throw new ReturnValueException(value);
    }

    @LanguagePattern("!n choose !k")
    public static Number choose(Number n, Number k)
    {
        return factorial(n).doubleValue() / (factorial(k).intValue() * factorial(n.intValue() - k.intValue()).intValue());
    }

    @LanguagePattern("!a factorial")
    public static Number factorial(Number n)
    {
        int result = 1;
        for(int i = 1; i <= n.intValue(); i++)
        {
            result *= i;
        }
        return result;
    }
}
