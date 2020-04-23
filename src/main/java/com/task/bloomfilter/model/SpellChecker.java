package com.task.bloomfilter.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.BitSet;

@Data
@Component
public class SpellChecker {
    private BitSet bitSet;
    private int numHashFunctions;
    private String hashName;

    public SpellChecker(@Value("${property.bitmaplength:10000000}") int bitmaplength,
                        @Value("${property.numhashfunctions:20}") int numHashFunctions,
                        @Value("${property.hashname:MD5}") String hashName) {
        this.bitSet = new BitSet(bitmaplength);
        this.numHashFunctions = numHashFunctions;
        this.hashName = hashName;
    }
}
