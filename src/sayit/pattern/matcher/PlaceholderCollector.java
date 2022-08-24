package sayit.pattern.matcher;

import sayit.pattern.Placeholder;
import sayit.pattern.matcher.part.Repetition;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;

public class PlaceholderCollector
{
    private final LinkedHashMap<String, Repetition> placeholders = new LinkedHashMap<>();
    private final Deque<Repetition> repetitions = new ArrayDeque<>();

    public void add(String variable)
    {
        boolean exists = this.placeholders.containsKey(variable);
        Repetition result = null;
        if(exists)
        {
            result = Repetition.MULTIPLE;
        }
        else
        {
            for (Repetition repetition : repetitions)
            {
                if (repetition == Repetition.ARBITRARY || repetition == Repetition.MULTIPLE)
                {
                    result = Repetition.MULTIPLE;
                    break;
                }
            }
            result = result == null ? this.repetitions.peek() : result;
        }
        result = result == null ? Repetition.REQUIRED : result;
        this.placeholders.put(variable, result);
    }

    public List<Placeholder> order()
    {
        return this.placeholders
                .entrySet()
                .stream()
                .map(e -> new Placeholder(e.getKey(), e.getValue()))
                .toList();
    }

    public void push(Repetition repetition)
    {
        this.repetitions.push(repetition);
    }

    public void pop()
    {
        this.repetitions.pop();
    }
}
