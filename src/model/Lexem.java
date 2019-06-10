package model;
import java.util.regex.Pattern;

public enum Lexem {
    VAR("^[a-z]+"),
    NUM("^\\d+"),
    OP("^[-|+|/|*]"),
    ASSIGN_OP("^="),
    LOG_OP("^[==|<|>|>=|<=|!=]"),
    WHILE("^_while"),
    OPEN_BR("^[/(]"),
    CLOSE_BR("^[/)]"),
    OPEN_BRACE("^[/{]"),
    CLOSE_BRACE("^[/}]"),
    END("^[;]");

    private final Pattern pattern;

    Lexem(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public Pattern getPattern() {
        return pattern;
    }
}