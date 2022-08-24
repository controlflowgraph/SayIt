package sayit;

import sayit.code.CodeTokenizer;
import sayit.code.TokenSupplier;
import sayit.code.TokenUtil;
import sayit.eval.EvaluationEnvironment;
import sayit.pattern.PatternCollector;
import sayit.pattern.matcher.match.Match;
import sayit.pattern.matcher.MatcherContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SayIt
{
    public static void main(String[] args) throws IOException
    {
        run(Files.readString(Path.of("data/Test.say")));
    }

    public static void run(String code)
    {
        List<String> token = CodeTokenizer.tokenize(code);
        TokenSupplier supplier = new TokenSupplier(token);
        List<Match> matches = new ArrayList<>();
        MatcherContext context = new MatcherContext(PatternCollector.collect(PatternCollection.class));
        while(supplier.has())
        {
            int start = supplier.get();

            Match match = context.match(new TokenSupplier(TokenUtil.collectUntilEnd(".", supplier)));
            if(match == null)
            {
                supplier.set(start);
                StringBuilder builder = new StringBuilder();
                while(supplier.has())
                {
                    builder.append(supplier.next()).append(' ');
                }
                throw new RuntimeException("Unable to match! " + builder);
            }

            matches.add(match);
        }
        EvaluationEnvironment env = new EvaluationEnvironment();
        for (Match match : matches)
        {
            match.compute(env);
        }
        env.dump();
    }
}
