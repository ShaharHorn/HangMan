package com.netomedia.exam.hangman.service;

import com.netomedia.exam.hangman.server.HangmanServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class HangManSolverService {

    @Autowired
    HangmanServer hangmanServer;

    ArrayList<String> dictionaryWords;
    List<Character> allreadyGuessedChars = new ArrayList<>();
    HangManDictionaryService hangManDictionaryService = new HangManDictionaryService();
    HashMap<Character, Integer> charToNumberOfOccMap = new HashMap<>();


    public HangManSolverService() {
        dictionaryWords = hangManDictionaryService.getDictionaryWords();
        countCharOccInDictionary();
    }

    private void countCharOccInDictionary() {
        charToNumberOfOccMap.clear();
        Set<Character> wordSet = new HashSet<Character>();

        for (String word : dictionaryWords) {
            for (char c : word.toCharArray()) {
                wordSet.add(c);
            }
            char[] strArray = word.toCharArray();
            for (char c : wordSet) {
                if (charToNumberOfOccMap.containsKey(c)) {
                    charToNumberOfOccMap.put(c, charToNumberOfOccMap.get(c) + 1);
                } else {
                    charToNumberOfOccMap.put(c, 1);
                }
            }
        }
        for (Character allreadyGuessedChar : allreadyGuessedChars) {
            charToNumberOfOccMap.remove(allreadyGuessedChar);
        }
    }

    public void filterWordsWithNotFoundChar(String notFound) {
        dictionaryWords.removeIf(p -> p.contains(notFound));
    }

    public void filterWordsByLength(String guess) {
        dictionaryWords.removeIf(p -> p.length() != guess.length());
    }

    public void filterWords(String guess) {
        Boolean toDetele = false;
        char[] guessToCheck = guess.toLowerCase().toCharArray();
        guess.toLowerCase();
        for (int j = 0; j < dictionaryWords.size(); j++) {
            char[] wordToCheck = dictionaryWords.get(j).toLowerCase().toCharArray();
            toDetele = false;
            for (int i = 0; i < guess.length(); i++) {
                if (guessToCheck[i] != '_' && guessToCheck[i] == wordToCheck[i]) {
                    toDetele = true;
                }
            }
            if (toDetele == true) {
                dictionaryWords.remove(j);
            }
        }
        countCharOccInDictionary();
    }


    public List<Character> getCharsToGuess() {
        int max = Collections.max(charToNumberOfOccMap.values());
        List<Character> charsToGuess = charToNumberOfOccMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(max))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
        charToNumberOfOccMap.remove(charsToGuess.get(0));
        return charsToGuess;
    }

    public String getCharToGuess() {
        List<Character> charsToGuess = getCharsToGuess();
        Character charToGuess = null;
        if (charsToGuess.size() == 1)
            charToGuess = charsToGuess.get(0);
//        else {
        //more than one char, check the best char to guess;
        //      }
        //charToGuess
        allreadyGuessedChars.add(charToGuess);
        //return charToGuess.toString();
        return charsToGuess.get(0).toString();
    }
}
