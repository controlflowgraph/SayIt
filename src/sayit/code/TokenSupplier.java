package sayit.code;

import java.util.List;

public class TokenSupplier
{
    private final List<String> token;
    private int index;

    public TokenSupplier(List<String> token)
    {
        this.token = token;
    }

    public boolean has()
    {
        return this.index < this.token.size();
    }

    public String next()
    {
        return this.token.get(this.index++);
    }

    public String peek()
    {
        return this.token.get(this.index);
    }

    public int get()
    {
        return this.index;
    }

    public void set(int index)
    {
        this.index = index;
    }
}
