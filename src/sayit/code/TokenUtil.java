package sayit.code;

import java.util.ArrayList;
import java.util.List;

public class TokenUtil
{
    public static List<String> collectUntilEnd(String end, TokenSupplier supplier)
    {
        List<String> token = new ArrayList<>();
        boolean found = false;
        int indentation = 0;
        while(!found && supplier.has())
        {
            String current = supplier.next();
            if(end.equals(current) && indentation == 0)
            {
                found = true;
            }
            else
            {
                if(current.equals("("))
                {
                    indentation++;
                }
                else if(current.equals(")"))
                {
                    indentation--;
                }
                token.add(current);
            }
        }

        if(!found)
            throw new RuntimeException("Expected '" + end + "'");

        return token;
    }
}
