package com.github.staego.config_builder.utils;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEngine {
    private static final Pattern PATTERN = Pattern.compile("\\$\\{([\\S\\d-_]+)}");

    public static String render(String template, Map<String, String> components) {
        StringBuilder result = new StringBuilder(template.length() << 1);
        int start_index = 0;
        Matcher matcher = PATTERN.matcher(template);
        while (matcher.find()) {
            if (start_index < matcher.start()) {
                result.append(template.subSequence(start_index, matcher.start()));
                start_index = matcher.end();
            }
            String key = matcher.group(1).toLowerCase();
            result.append(components.getOrDefault(key, ""));
        }
        if (start_index < template.length()) {
            result.append(template.subSequence(start_index, template.length()));
        }
        return result.toString();
    }

    public static Set<String> parse(String template) {
        Set<String> result = new HashSet<>();
        Matcher matcher = PATTERN.matcher(template);
        while (matcher.find()) {
            result.add(matcher.group(1).toLowerCase());
        }
        return result;
    }
}
