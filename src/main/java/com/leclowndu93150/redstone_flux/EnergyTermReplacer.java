package com.leclowndu93150.redstone_flux;

public class EnergyTermReplacer {

    // regex still isn't faster :)

    public static String replaceEnergyTerms(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        int len = text.length();
        if (len < 2) {
            return text;
        }

        char[] chars = text.toCharArray();

        if (!quickCheck(chars)) {
            return text;
        }

        StringBuilder result = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            char c = chars[i];

            // We only care when the current char is 'F' or 'f'
            if ((c == 'F' || c == 'f') && i + 1 < len) {
                char n1 = chars[i + 1];

                // --- "Forge Energy" / "forge energy" ---
                if (n1 == 'o') {
                    if (c == 'F' && matchesForgeEnergy(chars, i)) {
                        result.append("Redstone Flux");
                        i += 11; // skip "Forge Energy" (12 chars total, loop will ++)
                        continue;
                    } else if (c == 'f' && matchesforgeenergy(chars, i)) {
                        result.append("redstone flux");
                        i += 11;
                        continue;
                    }
                }

                if (c == 'F' && n1 == 'E' &&
                        (i + 2 >= len || !Character.isLetter(chars[i + 2]))) {

                    if (i > 0 && Character.isLetter(chars[i - 1]) && !isPrefix(chars[i - 1])) {
                        result.append(c);
                        continue;
                    }

                    int start = findNumberStart(chars, i - 1);
                    if (start < i) {
                        int numStart = start;
                        int toRemove = i - start;

                        result.setLength(result.length() - toRemove);
                        result.append(chars, numStart, i - numStart);

                        char prefix = (i > 0 && isPrefix(chars[i - 1])) ? chars[i - 1] : '\0';
                        if (prefix != '\0') {

                            result.setLength(result.length() - 1);
                            result.append(prefix);
                        }

                        result.append("RF");
                        i += 1;
                    } else {
                        result.append("RF");
                        i += 1;
                    }
                    continue;
                }
            }

            result.append(c);
        }

        return result.toString();
    }

    private static boolean quickCheck(char[] chars) {
        int len = chars.length - 1;
        for (int i = 0; i < len; i++) {
            char c = chars[i];
            char n = chars[i + 1];
            if ((c == 'F' || c == 'f') && (n == 'E' || n == 'o')) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesForgeEnergy(char[] chars, int pos) {
        // "Forge Energy" is 12 chars
        if (pos + 12 > chars.length) return false;

        if (chars[pos]     != 'F' || chars[pos + 1] != 'o' ||
                chars[pos + 2] != 'r' || chars[pos + 3] != 'g' ||
                chars[pos + 4] != 'e' || chars[pos + 5] != ' ' ||
                chars[pos + 6] != 'E' || chars[pos + 7] != 'n' ||
                chars[pos + 8] != 'e' || chars[pos + 9] != 'r' ||
                chars[pos + 10] != 'g'|| chars[pos + 11] != 'y') {
            return false;
        }

        return pos == 0 || !Character.isLetter(chars[pos - 1]);
    }

    private static boolean matchesforgeenergy(char[] chars, int pos) {
        // "forge energy" is 12 chars
        if (pos + 12 > chars.length) return false;

        if (chars[pos]     != 'f' || chars[pos + 1] != 'o' ||
                chars[pos + 2] != 'r' || chars[pos + 3] != 'g' ||
                chars[pos + 4] != 'e' || chars[pos + 5] != ' ' ||
                chars[pos + 6] != 'e' || chars[pos + 7] != 'n' ||
                chars[pos + 8] != 'e' || chars[pos + 9] != 'r' ||
                chars[pos + 10] != 'g'|| chars[pos + 11] != 'y') {
            return false;
        }

        return pos == 0 || !Character.isLetter(chars[pos - 1]);
    }

    private static int findNumberStart(char[] chars, int pos) {
        while (pos >= 0) {
            char c = chars[pos];
            if (Character.isDigit(c) || c == ',' || c == '.' || c == '/') {
                pos--;
            } else if (isPrefix(c) && pos > 0 && Character.isDigit(chars[pos - 1])) {
                pos--;
            } else if (c == ' ' && pos > 0 && Character.isDigit(chars[pos - 1])) {
                pos--;
            } else {
                return pos + 1;
            }
        }
        return 0;
    }

    private static boolean isPrefix(char c) {
        return c == 'k' || c == 'K' ||
                c == 'm' || c == 'M' ||
                c == 'g' || c == 'G' ||
                c == 't' || c == 'T';
    }
}