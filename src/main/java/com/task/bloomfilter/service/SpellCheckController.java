package com.task.bloomfilter.service;

import com.task.bloomfilter.enums.PresenceStatus;
import com.task.bloomfilter.model.WordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/spellchecker")
public class SpellCheckController {

    @Autowired
    SpellCheckService spellCheckService;

    @GetMapping(path = "/check")
    public WordStatus checkWord(@RequestParam String word) {
        if (!spellCheckService.checkWordExists(word)) {
            return new WordStatus(PresenceStatus.DEFINITELY_ABSENT,
                    "The word "+ word+ " is definitely not present");
        } else {
            return new WordStatus(PresenceStatus.PROBABLY_PRESENT,
                    "The word "+ word+ " is probably present");
        }
    }
}
