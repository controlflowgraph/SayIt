package sayit.pattern;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MultiLanguagePattern.class)
public @interface LanguagePattern
{
    String value();
}
