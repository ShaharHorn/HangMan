package com.netomedia.exam.hangman.player;

import com.netomedia.exam.hangman.model.ServerResponse;
import com.netomedia.exam.hangman.server.HangmanServer;
import com.netomedia.exam.hangman.service.HangManSolverService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HangmanPlayer {

    private static HangmanServer server = new HangmanServer();
    private static HangManSolverService hangManSolverService = new HangManSolverService();

    /**
     * This is the entry point of your Hangman Player
     * To start a new game call server.startNewGame()
     */
    public static void main(String[] args) throws Exception {
    try{
        ServerResponse serverResponse;
        serverResponse = server.startNewGame();
        String guess;
        hangManSolverService.filterWordsByLength(serverResponse.getHangman());
        while(!serverResponse.isGameEnded())
        {
            String token = serverResponse.getToken();
            String charToGuess = hangManSolverService.getCharToGuess();
            serverResponse = server.guess(token,charToGuess);
            if(serverResponse.isCorrect())
            {
                hangManSolverService.filterWords(serverResponse.getHangman());
            }
            else{
                hangManSolverService.filterWordsWithNotFoundChar(charToGuess);
            }
        }
    }
    catch (Exception e)
    {
        log.error(e.getMessage());
    }
    }
}
