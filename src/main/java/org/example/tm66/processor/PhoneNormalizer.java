package org.example.tm66.processor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneNormalizer {

    public static String normalize(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return normalizeSB(sb, input);
    }

    public static String format(@NotNull String normalizePhone) {
        if ((normalizePhone.length() == 11 && normalizePhone.startsWith("7"))
                ||
                (normalizePhone.length() == 12 && normalizePhone.startsWith("+7"))
        ) {
            int offset = normalizePhone.length() == 12 ? 1 : 0;
            return String.format("+7 (%s) %s-%s-%s",
                    normalizePhone.substring(1 + offset, 4 + offset),
                    normalizePhone.substring(4 + offset, 7 + offset),
                    normalizePhone.substring(7 + offset, 9 + offset),
                    normalizePhone.substring(9 + offset, 11 + offset));
        }
        return normalizePhone;
    }

    public static String addTelLink(String text) {
        return addTelLinkAndFormat(text, false);
    }

    public static String addTelLink(String text, boolean format) {
        return addTelLinkAndFormat(text, format);
    }

    private static String addTelLinkAndFormat(String text, boolean format) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int first = Integer.MAX_VALUE;
        int last = Integer.MIN_VALUE;
        int count = 0;
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
                if (count < first) first = count;
                if (count > last) last = count;
            }
            count++;
        }
        if (sb.length() == 0) return text;
        boolean includePlus = false;
        if (first > 0 && text.charAt(first - 1) == '+') {
            first--;
            includePlus = true;
        }
        String replaceText = text.substring(first, last + 1);
        String normalized = normalizeSB(sb, replaceText);
        if (format) replaceText = format(normalized);
        if (normalized.equals(replaceText) && !includePlus) return text;
        return text.substring(0, first)
                + "<a href=\"tel:" + normalized + "\">" + replaceText + "</a>"
                + text.substring(last + 1);
    }


    private static String normalizeSB(StringBuilder sb, String input) {
        if (sb.length() == 11 && sb.charAt(0) == '8') {
            sb.setCharAt(0, '7');
            return "+" + sb;
        } else if (sb.length() == 10) {
            return "+7" + sb;
        } else if (sb.length() == 11 && sb.charAt(0) == '7') {
            return "+" + sb;
        }
        return input;
    }

}
