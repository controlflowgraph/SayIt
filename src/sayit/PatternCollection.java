package sayit;

import sayit.eval.EvaluationEnvironment;
import sayit.eval.function.Block;
import sayit.eval.function.Func;
import sayit.eval.function.ReturnValueException;
import sayit.eval.location.Location;
import sayit.eval.location.Variable;
import sayit.pattern.LanguagePattern;
import sayit.pattern.matcher.match.Match;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PatternCollection
{
    @LanguagePattern("split !l at !e")
    @LanguagePattern("split !l at each !e")
    public static List<List<Object>> splitAtEach(List<?> data, Object element)
    {
        List<List<Object>> splits = new ArrayList<>();
        List<Object> current = new ArrayList<>();
        for (Object datum : data)
        {
            if (Objects.equals(datum, element))
            {
                splits.add(current);
                current = new ArrayList<>();
            }
            else
            {
                current.add(datum);
            }
        }
        return splits;
    }

    @LanguagePattern("!l at !i")
    public static Object content(List<?> lst, int index) throws IOException
    {
        return lst.get(index);
    }

    @LanguagePattern("content of !a")
    public static String content(String path) throws IOException
    {
        return Files.readString(Path.of(path));
    }

    @LanguagePattern("lines of !a")
    public static List<String> lines(String path) throws IOException
    {
        return Files.readAllLines(Path.of(path));
    }

    @LanguagePattern("!a is equal to !b")
    public static boolean equal(Object a, Object b){ return Objects.equals(a, b); }

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

    @LanguagePattern("!a floored")
    public static int floored(Number a)
    {
        return a.intValue();
    }

    @LanguagePattern("!a less than !b")
    @LanguagePattern("!a is less than !b")
    public static boolean isLess(Number a, Number b)
    {
        return a.doubleValue() < b.doubleValue();
    }

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

    @LanguagePattern("call !a with !b (* , !b)")
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

    @LanguagePattern("a set of #m where 'a is from !s")
    public static Set<Object> test(Block map, Location location, Set<Object> source)
    {
        EvaluationEnvironment environment = map.getEnvironment();
        Match match = map.getMatches().get(0);
        Set<Object> mapped = new HashSet<>();
        for (Object o : source)
        {
            location.set(o);
            mapped.add(match.compute(environment));
        }
        return mapped;
    }

    @LanguagePattern("a set of #m where 'a is from !s and #c")
    public static Set<Object> test(Block map, Location location, Set<Object> source, Block condition)
    {
        EvaluationEnvironment environment = map.getEnvironment();
        Match match = map.getMatches().get(0);
        Match cond = condition.getMatches().get(0);
        Set<Object> mapped = new HashSet<>();
        for (Object o : source)
        {
            location.set(o);
            boolean check = (Boolean) cond.compute(environment);
            if (check)
            {
                mapped.add(match.compute(environment));
            }
        }
        return mapped;
    }

    @LanguagePattern("every 'a of !l mapped to #c")
    public static List<Object> mapping(Location location, List<?> source, Block mapper)
    {
        EvaluationEnvironment environment = mapper.getEnvironment();
        Match match = mapper.getMatches().get(0);
        List<Object> mapped = new ArrayList<>();
        for (Object o : source)
        {
            location.set(o);
            mapped.add(match.compute(environment));
        }
        return mapped;
    }

    @LanguagePattern("!l summed up")
    public static double summed(List<?> lst)
    {
        double summed = 0;
        for (Object i : lst)
            summed += ((Number) i).doubleValue();
        return summed;
    }

    @LanguagePattern("!a as int")
    public static Integer asInt(String s)
    {
        return Integer.parseInt(s);
    }

    @LanguagePattern("!a as an int")
    public static Integer asAnInt(char c)
    {
        return (int) c;
    }

    @LanguagePattern("!a sorted")
    @LanguagePattern("!a sorted in ascending order")
    public static List<Object> sortedAscending(List<Object> elements)
    {
        return elements.stream()
                .sorted()
                .toList();
    }

    @LanguagePattern("!a sorted in descending order")
    public static List<Object> sortedDescending(List<Object> elements)
    {
        return flipped(elements.stream()
                .sorted()
                .toList());
    }

    @LanguagePattern("take first !n of !l")
    public static List<Object> takeFirstN(int n, List<Object> objects)
    {
        return objects.stream()
                .limit(n)
                .toList();
    }

    @LanguagePattern("!a flipped")
    @LanguagePattern("!a reversed")
    public static List<Object> flipped(List<Object> elements)
    {
        List<Object> objs = new ArrayList<>();
        for (int i = elements.size() - 1; i >= 0; i--)
        {
            objs.add(elements.get(i));
        }
        return objs;
    }

    @LanguagePattern("!a sorted by")
    public static List<Object> sortedBy(List<Object> elements, Block sorter)
    {
        EvaluationEnvironment environment = sorter.getEnvironment();
        Match match = sorter.getMatches().get(0);
        Location a = new Variable(environment, "a");
        Location b = new Variable(environment, "b");
        return elements.stream()
                .sorted((v1, v2) -> {
                    a.set(v1);
                    b.set(v2);
                    return (Integer) match.compute(environment);
                })
                .toList();
    }

    @LanguagePattern("character !a of !b")
    public static char characterAt(int i, String str)
    {
        return str.charAt(i);
    }

    @LanguagePattern("when !v is (+ !v1 then #v2)")
    public static Object when(Object value, List<Object> cases, List<Block> values)
    {
        for (int i = 0; i < cases.size(); i++)
        {
            if(Objects.equals(value, cases.get(i)))
            {
                Match m = values.get(i).getMatches().get(0);
                EvaluationEnvironment env = values.get(i).getEnvironment();
                return m.compute(env);
            }
        }
        throw new RuntimeException("Fell through all cases!");
    }

    @LanguagePattern("in case (+ #c then #v)")
    public static Object when(List<Block> conditions, List<Block> values)
    {
        for (int i = 0; i < conditions.size(); i++)
        {
            Match match = conditions.get(i).getMatches().get(0);
            EvaluationEnvironment environment = conditions.get(i).getEnvironment();
            boolean b = (boolean) match.compute(environment);
            if(b)
            {
                Match m = values.get(i).getMatches().get(0);
                EvaluationEnvironment env = values.get(i).getEnvironment();
                return m.compute(env);
            }
        }
        throw new RuntimeException("Fell through all cases!");
    }

    @LanguagePattern("the substring from !a of !s")
    public static String sub(int start, String s)
    {
        return s.substring(start);
    }

    @LanguagePattern("the substring from !a to !b of !s")
    public static String sub(int start, int end, String s)
    {
        return s.substring(start, end);
    }

    @LanguagePattern("!a as a set")
    public static Set<?> asASet(Collection<?> collection)
    {
        return new HashSet<>(collection);
    }

    @LanguagePattern("the characters of !a")
    public static List<Character> characters(String s)
    {
        return s.chars().mapToObj(i -> (char) i).toList();
    }

    @LanguagePattern("the length of !a")
    public static int len(String s)
    {
        return s.length();
    }

    @LanguagePattern("!a as a list")
    public static List<?> asAList(Collection<?> collection)
    {
        return new ArrayList<>(collection);
    }

    @LanguagePattern("!a is lowercase character")
    public static boolean isLower(char c)
    {
        return 'a' <= c && c <= 'z';
    }

    @LanguagePattern("!a is uppercase character")
    public static boolean isUpper(char c)
    {
        return 'A' <= c && c <= 'Z';
    }

    @LanguagePattern("!a in groups of size !n")
    public static <T> List<List<T>> groups(List<T> elements, int n)
    {
        List<List<T>> all = new ArrayList<>();
        Iterator<T> iterator = elements.iterator();
        while (iterator.hasNext())
        {
            List<T> current = new ArrayList<>();
            for(int i = 0; iterator.hasNext() && i < n; i++)
            {
                current.add(iterator.next());
            }
            all.add(current);
        }
        return all;
    }
}
