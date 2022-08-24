package sayit.pattern.matcher;

import sayit.code.TokenSupplier;
import sayit.eval.argument.Argument;
import sayit.eval.argument.EmptyArgument;
import sayit.eval.argument.MultiArgument;
import sayit.eval.argument.SingleArgument;
import sayit.pattern.Placeholder;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.part.MatcherPart;
import sayit.pattern.matcher.part.Repetition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record Matcher(List<Placeholder> order, MatcherPart part)
{
    public List<Argument> tryMatch(MatcherContext context, TokenSupplier supplier)
    {
        Map<String, List<Match>> match = this.part.match(context, supplier);
        if(match != null)
        {
            return convertToArguments(match);
        }
        return null;
    }

    private List<Argument> convertToArguments(Map<String, List<Match>> match)
    {
        List<Argument> arguments = new ArrayList<>();
        for (Placeholder placeholder : this.order)
        {
            List<Match> matches = match.get(placeholder.name());
            if(placeholder.repetition() == Repetition.OPTIONAL)
            {
                arguments.add(getOptional(matches));
            }
            else if(placeholder.repetition() == Repetition.REQUIRED)
            {
                arguments.add(getRequired(matches));
            }
            else
            {
                arguments.add(getMulti(matches));
            }
        }
        return arguments;
    }

    private Argument getOptional(List<Match> matches)
    {
        if(matches != null)
        {
            if(matches.size() != 1)
                throw new RuntimeException("Required one match but none / multiple found!");
            return new SingleArgument(matches.get(0));
        }
        else
        {
            return new EmptyArgument();
        }
    }

    private Argument getRequired(List<Match> matches)
    {
        if(matches == null || matches.size() != 1)
        {
            throw new RuntimeException("Required one match but none / multiple found!");
        }
        return new SingleArgument(matches.get(0));
    }

    private Argument getMulti(List<Match> matches)
    {
        return new MultiArgument(Objects.requireNonNullElseGet(matches, ArrayList::new));
    }
}
