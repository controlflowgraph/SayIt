package sayit;

import sayit.eval.function.Block;
import sayit.eval.function.Func;
import sayit.eval.function.ReturnValueException;
import sayit.eval.location.Location;
import sayit.eval.location.Variable;
import sayit.pattern.LanguagePattern;

import java.util.*;

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

    @LanguagePattern("is !a contained in !b")
    @LanguagePattern("is !a in !b")
    @LanguagePattern("!a is in !b")
    public static boolean isIn(Object a, Collection<?> col)
    {
        return col.contains(a);
    }

    @LanguagePattern("is !a subset of !b")
    public static boolean isSubsetOf(Set<Object> a, Set<Object> b)
    {
        for (Object o : a)
        {
            if(!b.contains(o))
                return false;
        }
        return true;
    }

    @LanguagePattern("the union of  !a (* , !a) (? and !a)")
    public static Set<Object> union(List<Set<Object>> sets)
    {
        Set<Object> set = new HashSet<>();
        for (Set<Object> objects : sets)
        {
            set.addAll(objects);
        }
        return set;
    }

    @LanguagePattern("the intersection of !a (* , !a) (? and !a)")
    public static Set<Object> intersection(List<Set<Object>> sets)
    {
        Set<Object> set = new HashSet<>();
        if(sets.size() > 0)
        {
            loop:
            for (Object o : sets.get(0))
            {
                for (Set<Object> objects : sets)
                {
                    if(!objects.contains(o))
                    {
                        continue loop;
                    }
                }
                set.add(o);
            }
        }
        return set;
    }

    @LanguagePattern("the empty set")
    public static Set<Object> emptySet()
    {
        return Set.of();
    }

    @LanguagePattern("a set containing !a (* , !a) (? and !a)")
    public static Set<Object> setOf(List<Object> objects)
    {
        return new HashSet<>(objects);
    }

    @LanguagePattern("the difference between  !a (* , !a) (? and !a)")
    public static Set<Object> difference(List<Set<Object>> sets)
    {
        Set<Object> difference = new HashSet<>();
        for (int i = 0; i < sets.size(); i++)
        {
            Set<Object> set = sets.get(i);
            for (Object o : set)
            {
                boolean found = false;
                for (int k = 0; !found && k < sets.size(); k++)
                {
                    if(i != k)
                    {
                        if(sets.get(k).contains(o))
                        {
                            found = true;
                        }
                    }
                }

                if(!found)
                {
                    difference.add(o);
                }
            }
        }
        return difference;
    }

    @LanguagePattern("the power set of !a")
    public static Set<Set<Object>> powerSet(Set<Object> objects)
    {
        Set<Set<Object>> all = new HashSet<>();
        all.add(Set.of());
        for (Object object : objects)
        {
            Set<Set<Object>> iteration = new HashSet<>(all.size() * 2);
            iteration.addAll(all);
            for (Set<Object> objectSet : all)
            {
                Set<Object> added = new HashSet<>(objectSet.size() + 1);
                added.addAll(objectSet);
                added.add(object);
                iteration.add(added);
            }
            all = iteration;
        }
        return all;
    }
}
