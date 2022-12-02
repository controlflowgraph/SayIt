package sayit.pattern.matcher.part;

import sayit.code.TokenSupplier;
import sayit.code.TokenUtil;
import sayit.pattern.matcher.match.FunctionMatch;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.MatcherContext;
import sayit.pattern.matcher.PlaceholderCollector;

import java.util.List;
import java.util.Map;

public class ExpressionMatcherPart implements MatcherPart
{
    private final String name;
    public ExpressionMatcherPart(String name)
    {
        this.name = name;
    }

    @Override
    public void collect(PlaceholderCollector collector)
    {
        collector.add(this.name);
    }

    @Override
    public Map<String, List<Match>> match(MatcherContext context, TokenSupplier supplier)
    {
        if(!supplier.has())
            return null;

        String next = supplier.next();
        if(next.equals("("))
        {
            List<String> strings = TokenUtil.collectUntilEnd(")", supplier);
            if(strings.size() == 1 || strings.size() == 2 && strings.get(1).equals("."))
            {
                return match(strings.get(0));
            }
            else
            {
                Match match = context.match(new TokenSupplier(strings));
                if(match == null)
                    return null;
                return Map.of(this.name, List.of(match));
            }
        }
        else
        {
            return match(next);
        }
    }

    private Map<String, List<Match>> match(String str)
    {
        if(str.matches("\\d+"))
        {
            int value = Integer.parseInt(str);
            Match match = new FunctionMatch(e -> value);
            return Map.of(this.name, List.of(match));
        }
        if(str.matches("\\d+\\.\\d+"))
        {
            double value = Double.parseDouble(str);
            Match match = new FunctionMatch(e -> value);
            return Map.of(this.name, List.of(match));
        }
        else if(str.matches("\".*\""))
        {
            String content = str.substring(1, str.length() - 1);
            Match match = new FunctionMatch(e -> content);
            return Map.of(this.name, List.of(match));
        }
        else if(str.matches("'.*'"))
        {
            String content = str.substring(1, str.length() - 1);
            char c = switch (content)
                    {
                        case "\\n" -> '\n';
                        case "\\r" -> '\r';
                        case "\\t" -> '\t';
                        default -> content.charAt(0);
                    };
            Match match = new FunctionMatch(e -> c);
            return Map.of(this.name, List.of(match));
        }
        else if(str.equals("true") || str.equals("false"))
        {
            boolean value = str.equals("true");
            Match match = new FunctionMatch(e -> value);
            return Map.of(this.name, List.of(match));
        }
        else
        {
            Match match = new FunctionMatch(e -> e.get(str));
            return Map.of(this.name, List.of(match));
        }
    }
}
