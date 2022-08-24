package sayit.eval;

import java.util.*;

public class EvaluationEnvironment
{
    private final Deque<Map<String, Object>> scopes = new ArrayDeque<>();

    public EvaluationEnvironment()
    {
        push();
    }

    public void push()
    {
        this.scopes.push(new HashMap<>());
    }

    public void pop()
    {
        this.scopes.pop();
    }

    public void set(String name, Object value)
    {
        Objects.requireNonNull(this.scopes.peek()).put(name, value);
    }

    public Object get(String name)
    {
        return Objects.requireNonNull(this.scopes.peek()).get(name);
    }

    public void dump()
    {
        for (Map<String, Object> scope : this.scopes)
        {
            System.out.println("-------------------");
            scope.forEach((a, b) -> System.out.println(a + ": " + b));
        }
    }
}
