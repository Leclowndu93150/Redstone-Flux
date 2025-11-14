package com.leclowndu93150.redstone_flux;

import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

class EnergyTermReplacerRegexTest {
    
    private static final Pattern FORGE_ENERGY_PATTERN = Pattern.compile(
        "(?<!\\p{L})(Forge Energy|forge energy)|(?<=[\\d,./\\s]|^)([kKmMgGtT])?FE(?!\\p{L})"
    );
    
    private static String replaceEnergyTermsRegex(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        Matcher matcher = FORGE_ENERGY_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();
        
        while (matcher.find()) {
            String match = matcher.group();
            String replacement;
            
            if (match.equals("Forge Energy")) {
                replacement = "Redstone Flux";
            } else if (match.equals("forge energy")) {
                replacement = "redstone flux";
            } else {
                String prefix = matcher.group(2);
                if (prefix != null) {
                    replacement = prefix + "RF";
                } else {
                    replacement = "RF";
                }
            }
            
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);
        
        return result.toString();
    }
    
    @Test
    void testFEWithSpace() {
        assertEquals("100 RF", replaceEnergyTermsRegex("100 FE"));
        assertEquals("1000 RF", replaceEnergyTermsRegex("1000 FE"));
        assertEquals("1,000 RF", replaceEnergyTermsRegex("1,000 FE"));
    }
    
    @Test
    void testFEWithoutSpace() {
        assertEquals("100RF", replaceEnergyTermsRegex("100FE"));
        assertEquals("1000RF", replaceEnergyTermsRegex("1000FE"));
    }
    
    @Test
    void testFEWithPrefixes() {
        assertEquals("100kRF", replaceEnergyTermsRegex("100kFE"));
        assertEquals("100KRF", replaceEnergyTermsRegex("100KFE"));
        assertEquals("100mRF", replaceEnergyTermsRegex("100mFE"));
        assertEquals("100MRF", replaceEnergyTermsRegex("100MFE"));
        assertEquals("100gRF", replaceEnergyTermsRegex("100gFE"));
        assertEquals("100GRF", replaceEnergyTermsRegex("100GFE"));
        assertEquals("100tRF", replaceEnergyTermsRegex("100tFE"));
        assertEquals("100TRF", replaceEnergyTermsRegex("100TFE"));
    }
    
    @Test
    void testFEWithPrefixesAndSpace() {
        assertEquals("100 kRF", replaceEnergyTermsRegex("100 kFE"));
        assertEquals("100 KRF", replaceEnergyTermsRegex("100 KFE"));
        assertEquals("100 mRF", replaceEnergyTermsRegex("100 mFE"));
        assertEquals("100 MRF", replaceEnergyTermsRegex("100 MFE"));
    }
    
    @Test
    void testFEWithRatio() {
        assertEquals("100/1000 RF", replaceEnergyTermsRegex("100/1000 FE"));
        assertEquals("1.5/10.5 RF", replaceEnergyTermsRegex("1.5/10.5 FE"));
    }
    
    @Test
    void testFEWithRatioAndPrefix() {
        assertEquals("100/1000kRF", replaceEnergyTermsRegex("100/1000kFE"));
        assertEquals("10.5/100.5 MRF", replaceEnergyTermsRegex("10.5/100.5 MFE"));
    }
    
    @Test
    void testStandaloneFE() {
        assertEquals("RF", replaceEnergyTermsRegex("FE"));
        assertEquals("Energy: RF", replaceEnergyTermsRegex("Energy: FE"));
    }
    
    @Test
    void testForgeEnergy() {
        assertEquals("Redstone Flux", replaceEnergyTermsRegex("Forge Energy"));
        assertEquals("redstone flux", replaceEnergyTermsRegex("forge energy"));
    }
    
    @Test
    void testComplexStrings() {
        assertEquals("Energy: 1000 RF / 10000 RF", 
            replaceEnergyTermsRegex("Energy: 1000 FE / 10000 FE"));
        assertEquals("Stores 100kRF of Redstone Flux", 
            replaceEnergyTermsRegex("Stores 100kFE of Forge Energy"));
    }
    
    @Test
    void testNoChange() {
        assertEquals("100 RF", replaceEnergyTermsRegex("100 RF"));
        assertEquals("Redstone Flux", replaceEnergyTermsRegex("Redstone Flux"));
        assertEquals("Some random text", replaceEnergyTermsRegex("Some random text"));
    }
    
    @Test
    void testNullAndEmpty() {
        assertNull(replaceEnergyTermsRegex(null));
        assertEquals("", replaceEnergyTermsRegex(""));
    }
    
    @Test
    void testWordBoundaries() {
        assertEquals("IFER is not changed", replaceEnergyTermsRegex("IFER is not changed"));
        assertEquals("WIFE is not changed", replaceEnergyTermsRegex("WIFE is not changed"));
    }
    
    @Test
    void testDecimalNumbers() {
        assertEquals("10.5 RF", replaceEnergyTermsRegex("10.5 FE"));
        assertEquals("1.23 kRF", replaceEnergyTermsRegex("1.23 kFE"));
        assertEquals("99.99/100.00 RF", replaceEnergyTermsRegex("99.99/100.00 FE"));
    }
    
    @Test
    void testPerformanceCheck() {
        String noEnergyText = "This is a long string with no energy terms at all, just regular text";
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            replaceEnergyTermsRegex(noEnergyText);
        }
        long duration = System.nanoTime() - start;
        assertTrue(duration < 100_000_000, "Performance check: should complete 10k iterations in <100ms");
    }
}
