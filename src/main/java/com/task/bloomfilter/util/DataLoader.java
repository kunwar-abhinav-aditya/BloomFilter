package com.task.bloomfilter.util;

import com.task.bloomfilter.service.SpellCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    SpellCheckService spellCheckService;

    @Override
    public void run(String... strings) throws Exception {
        spellCheckService.addAll(new URL("http://codekata.com/data/wordlist.txt"));
        LOGGER.info("All words loaded into memory");
    }
}