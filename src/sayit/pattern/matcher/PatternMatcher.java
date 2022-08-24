package sayit.pattern.matcher;

import sayit.code.TokenSupplier;
import sayit.eval.argument.Argument;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.match.MethodMatch;

import java.lang.reflect.Method;
import java.util.List;

public record PatternMatcher(Matcher matcher, Method method)
{
    public Match tryMatch(MatcherContext context, TokenSupplier supplier)
    {
        List<Argument> arguments = this.matcher.tryMatch(context, supplier);
        if(arguments == null)
            return null;
        return new MethodMatch(this.method, arguments);
    }
}
