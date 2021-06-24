package com.netomedia.exam.hangman.service;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@Service
@Slf4j
@ConfigurationProperties
@Data
public class HangManDictionaryService {
    //@Value("${hangman.config.reqConfigFilePath}")
    private String filePath = "src/main/resources/dictionary.txt";

    public ArrayList<String> getDictionaryWords() {
        ArrayList<String> dictionaryWords = new ArrayList<>();
        try (Scanner s = new Scanner(new FileReader(filePath))) {
            while (s.hasNext()) {
                dictionaryWords.add(s.next());
            }
        } catch (Exception e) {
            log.error("error while reading dictionary file.");
        }

        return dictionaryWords;
    }
}
