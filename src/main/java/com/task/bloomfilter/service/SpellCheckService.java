package com.task.bloomfilter.service;

import com.task.bloomfilter.exception.SpellCheckException;
import com.task.bloomfilter.model.SpellChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SpellCheckService {

    @Autowired
    SpellChecker spellChecker;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpellCheckService.class);

    public void addAll(URL requestURL) throws IOException {
        int wordCount = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(requestURL.openStream()));
        String word;
        while ((word = in.readLine()) != null) {
            wordCount += 1;
            addWord(word);
        }
        in.close();
        LOGGER.info(wordCount + " words added");
    }

    public void addWord(String word) {
        long[] hashes = getHashes(word, spellChecker.getNumHashFunctions());
        for (long hash : hashes) {
            int bitToSet = (int) (hash % spellChecker.getBitSet().size());
            spellChecker.getBitSet().set(bitToSet);
        }
    }

    public Boolean checkWordExists(String word) {
        if (word == "") {
            throw new SpellCheckException(
                    400,
                    "No word supplied",
                    "No word has been supplied to test whether it exists",
                    "Please supply a word to check"
            );
        }
        long[] hashes = getHashes(word, spellChecker.getNumHashFunctions());
        for (long hash : hashes) {
            int bitToCheck = (int) (hash % spellChecker.getBitSet().size());
            if (!spellChecker.getBitSet().get(bitToCheck)) {
                return false;
            }
        }
        return true;
    }

    private long[] getHashes(String word, int numHashes) {
        long[] hashes = new long[numHashes];
        try {
            MessageDigest md = MessageDigest.getInstance(spellChecker.getHashName());
            BigInteger hashValue = new BigInteger(1, md.digest(word.getBytes("UTF-8")));
            int hashFirst = Math.abs(hashValue.intValue());
            int hashSecond = Math.abs(hashValue.shiftRight(hashValue.bitCount() / 2).intValue());
            for (int i = 0; i < numHashes; i++) {
                hashes[i] = Long.valueOf(hashFirst) + i * Long.valueOf(hashSecond);
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new SpellCheckException(
                    500,
                    "No such algorithm found",
                    "The algorithm by the name of " + this.spellChecker.getHashName() + "could not be found",
                    "Choose a correct algorithm name"
            );
        } catch (UnsupportedEncodingException ex) {
            throw new SpellCheckException(500, "Encoding is unsupported", "", "");
        }
        return hashes;
    }
}
