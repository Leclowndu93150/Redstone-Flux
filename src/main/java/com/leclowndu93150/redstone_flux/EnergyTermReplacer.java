package com.leclowndu93150.redstone_flux;

public class EnergyTermReplacer {

    //regex isn't faster lol
    
    public static String replaceEnergyTerms(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        if (!quickCheck(text)) {
            return text;
        }
        
        StringBuilder result = new StringBuilder(text.length());
        int len = text.length();
        
        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            
            if (c == 'F' && i + 1 < len && text.charAt(i + 1) == 'o' && matchesForgeEnergy(text, i)) {
                result.append("Redstone Flux");
                i += 11;
            }
            else if (c == 'f' && i + 1 < len && text.charAt(i + 1) == 'o' && matchesforgeenergy(text, i)) {
                result.append("redstone flux");
                i += 11;
            }
            else if (c == 'F' && i + 1 < len && text.charAt(i + 1) == 'E' && (i + 2 >= len || !Character.isLetter(text.charAt(i + 2)))) {
                if (i > 0 && Character.isLetter(text.charAt(i - 1)) && !isPrefix(text.charAt(i - 1))) {
                    result.append(c);
                    continue;
                }
                
                int start = findNumberStart(text, i - 1);
                if (start < i) {
                    int numStart = start;
                    result.setLength(result.length() - (i - start));
                    result.append(text, numStart, i);
                    
                    char prefix = (i > 0 && isPrefix(text.charAt(i - 1))) ? text.charAt(i - 1) : '\0';
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
            }
            else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    private static boolean quickCheck(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            char c = text.charAt(i);
            if ((c == 'F' || c == 'f') && (text.charAt(i + 1) == 'E' || text.charAt(i + 1) == 'o')) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean matchesForgeEnergy(String text, int pos) {
        if (pos + 12 > text.length()) return false;
        String sub = text.substring(pos, pos + 12);
        return sub.equals("Forge Energy") && (pos == 0 || !Character.isLetter(text.charAt(pos - 1)));
    }
    
    private static boolean matchesforgeenergy(String text, int pos) {
        if (pos + 12 > text.length()) return false;
        String sub = text.substring(pos, pos + 12);
        return sub.equals("forge energy") && (pos == 0 || !Character.isLetter(text.charAt(pos - 1)));
    }
    
    private static int findNumberStart(String text, int pos) {
        while (pos >= 0) {
            char c = text.charAt(pos);
            if (Character.isDigit(c) || c == ',' || c == '.' || c == '/') {
                pos--;
            } else if (isPrefix(c) && pos > 0 && Character.isDigit(text.charAt(pos - 1))) {
                pos--;
            } else if (c == ' ' && pos > 0 && Character.isDigit(text.charAt(pos - 1))) {
                pos--;
            } else {
                return pos + 1;
            }
        }
        return 0;
    }
    
    private static boolean isPrefix(char c) {
        return c == 'k' || c == 'K' || c == 'm' || c == 'M' || 
               c == 'g' || c == 'G' || c == 't' || c == 'T';
    }
}
