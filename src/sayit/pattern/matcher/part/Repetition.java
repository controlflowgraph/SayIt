package sayit.pattern.matcher.part;

public enum Repetition
{
    REQUIRED,
    OPTIONAL,
    ARBITRARY,
    MULTIPLE,
    ;
    public static Repetition getRepetition(String token)
    {
        return switch (token)
                {
                    case "?" -> OPTIONAL;
                    case "*" -> ARBITRARY;
                    case "+" -> MULTIPLE;
                    default -> throw new RuntimeException("Unknown repetition '" + token + "'!");
                };
    }
}
