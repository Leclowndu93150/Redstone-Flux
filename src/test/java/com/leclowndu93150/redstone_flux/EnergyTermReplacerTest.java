package com.leclowndu93150.redstone_flux;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnergyTermReplacerTest {
    
    @Test
    void testFEWithSpace() {
        assertEquals("100 RF", EnergyTermReplacer.replaceEnergyTerms("100 FE"));
        assertEquals("1000 RF", EnergyTermReplacer.replaceEnergyTerms("1000 FE"));
        assertEquals("1,000 RF", EnergyTermReplacer.replaceEnergyTerms("1,000 FE"));
    }
    
    @Test
    void testFEWithoutSpace() {
        assertEquals("100RF", EnergyTermReplacer.replaceEnergyTerms("100FE"));
        assertEquals("1000RF", EnergyTermReplacer.replaceEnergyTerms("1000FE"));
    }
    
    @Test
    void testFEWithPrefixes() {
        assertEquals("100kRF", EnergyTermReplacer.replaceEnergyTerms("100kFE"));
        assertEquals("100KRF", EnergyTermReplacer.replaceEnergyTerms("100KFE"));
        assertEquals("100mRF", EnergyTermReplacer.replaceEnergyTerms("100mFE"));
        assertEquals("100MRF", EnergyTermReplacer.replaceEnergyTerms("100MFE"));
        assertEquals("100gRF", EnergyTermReplacer.replaceEnergyTerms("100gFE"));
        assertEquals("100GRF", EnergyTermReplacer.replaceEnergyTerms("100GFE"));
        assertEquals("100tRF", EnergyTermReplacer.replaceEnergyTerms("100tFE"));
        assertEquals("100TRF", EnergyTermReplacer.replaceEnergyTerms("100TFE"));
    }
    
    @Test
    void testFEWithPrefixesAndSpace() {
        assertEquals("100 kRF", EnergyTermReplacer.replaceEnergyTerms("100 kFE"));
        assertEquals("100 KRF", EnergyTermReplacer.replaceEnergyTerms("100 KFE"));
        assertEquals("100 mRF", EnergyTermReplacer.replaceEnergyTerms("100 mFE"));
        assertEquals("100 MRF", EnergyTermReplacer.replaceEnergyTerms("100 MFE"));
    }
    
    @Test
    void testFEWithRatio() {
        assertEquals("100/1000 RF", EnergyTermReplacer.replaceEnergyTerms("100/1000 FE"));
        assertEquals("1.5/10.5 RF", EnergyTermReplacer.replaceEnergyTerms("1.5/10.5 FE"));
    }
    
    @Test
    void testFEWithRatioAndPrefix() {
        assertEquals("100/1000kRF", EnergyTermReplacer.replaceEnergyTerms("100/1000kFE"));
        assertEquals("10.5/100.5 MRF", EnergyTermReplacer.replaceEnergyTerms("10.5/100.5 MFE"));
    }
    
    @Test
    void testStandaloneFE() {
        assertEquals("RF", EnergyTermReplacer.replaceEnergyTerms("FE"));
        assertEquals("Energy: RF", EnergyTermReplacer.replaceEnergyTerms("Energy: FE"));
    }
    
    @Test
    void testForgeEnergy() {
        assertEquals("Redstone Flux", EnergyTermReplacer.replaceEnergyTerms("Forge Energy"));
        assertEquals("redstone flux", EnergyTermReplacer.replaceEnergyTerms("forge energy"));
    }
    
    @Test
    void testComplexStrings() {
        assertEquals("Energy: 1000 RF / 10000 RF", 
            EnergyTermReplacer.replaceEnergyTerms("Energy: 1000 FE / 10000 FE"));
        assertEquals("Stores 100kRF of Redstone Flux", 
            EnergyTermReplacer.replaceEnergyTerms("Stores 100kFE of Forge Energy"));
    }
    
    @Test
    void testNoChange() {
        assertEquals("100 RF", EnergyTermReplacer.replaceEnergyTerms("100 RF"));
        assertEquals("Redstone Flux", EnergyTermReplacer.replaceEnergyTerms("Redstone Flux"));
        assertEquals("Some random text", EnergyTermReplacer.replaceEnergyTerms("Some random text"));
    }
    
    @Test
    void testNullAndEmpty() {
        assertNull(EnergyTermReplacer.replaceEnergyTerms(null));
        assertEquals("", EnergyTermReplacer.replaceEnergyTerms(""));
    }
    
    @Test
    void testWordBoundaries() {
        assertEquals("IFER is not changed", EnergyTermReplacer.replaceEnergyTerms("IFER is not changed"));
        assertEquals("WIFE is not changed", EnergyTermReplacer.replaceEnergyTerms("WIFE is not changed"));
    }
    
    @Test
    void testDecimalNumbers() {
        assertEquals("10.5 RF", EnergyTermReplacer.replaceEnergyTerms("10.5 FE"));
        assertEquals("1.23 kRF", EnergyTermReplacer.replaceEnergyTerms("1.23 kFE"));
        assertEquals("99.99/100.00 RF", EnergyTermReplacer.replaceEnergyTerms("99.99/100.00 FE"));
    }
    
    @Test
    void testPerformanceCheck() {
        String noEnergyText = "This is a long string with no energy terms at all, just regular text";
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            EnergyTermReplacer.replaceEnergyTerms(noEnergyText);
        }
        long duration = System.nanoTime() - start;
        assertTrue(duration < 100_000_000, "Performance check: should complete 10k iterations in <100ms");
    }
}
