package sayit.eval.function;

import sayit.eval.CustomRuntimeException;

public class ReturnValueException extends CustomRuntimeException
{
    private final Object value;

    public ReturnValueException(Object value)
    {
        this.value = value;
    }

    public Object getValue()
    {
        return this.value;
    }
}
