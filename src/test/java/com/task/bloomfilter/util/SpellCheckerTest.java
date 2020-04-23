package com.task.bloomfilter.util;

import com.task.bloomfilter.exception.SpellCheckException;
import com.task.bloomfilter.service.SpellCheckService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpellCheckerTest {

    @Autowired
    SpellCheckService spellCheckService;

    @Test
    public void testCheckWordPresent() {
        assertEquals(true, spellCheckService.checkWordExists("ABC"));
    }

    @Test
    public void testCheckAfterAddNewWord() {
        spellCheckService.addWord("randomAbsentWord");
        assertEquals(true, spellCheckService.checkWordExists("randomAbsentWord"));
    }

    /**
     * This test will probabilistically pass.
     * The probability of a false positive is 0.0000001 for the active bloom filter parameters.
     */
    @Test
    public void testCheckWordAbsent() {
        assertEquals(false, spellCheckService.checkWordExists("randomAbsentWordSecond"));
    }

    @Test
    public void testCheckExceptionBlankWordCheck() {
        try {
            spellCheckService.checkWordExists("");
        } catch (SpellCheckException sce) {
            assertEquals(400, sce.getErrorCode());
            assertEquals("No word supplied", sce.getMessage());
        }
    }
}
