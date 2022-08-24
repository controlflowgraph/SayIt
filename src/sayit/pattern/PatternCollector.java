package sayit.pattern;

import sayit.pattern.matcher.Matcher;
import sayit.pattern.matcher.PatternMatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PatternCollector
{
    public static List<PatternMatcher> collect(Class<?> cls)
    {
        List<PatternMatcher> matchers = new ArrayList<>();
        Method[] declaredMethods = cls.getDeclaredMethods();
        for (Method method : declaredMethods)
        {
            LanguagePattern single = method.getAnnotation(LanguagePattern.class);
            if(single != null)
            {
                matchers.add(create(single, method));
            }
            MultiLanguagePattern multi = method.getAnnotation(MultiLanguagePattern.class);
            if(multi != null)
            {
                for (LanguagePattern pattern : multi.value())
                {
                    matchers.add(create(pattern, method));
                }
            }
        }
        return matchers;
    }

    private static PatternMatcher create(LanguagePattern annotation, Method method)
    {
        Matcher parse = PatternParser.parse(annotation.value());

        return new PatternMatcher(parse, method);
    }
}
