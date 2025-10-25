package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentWord: String
    var userGuess = ""
        private set
    private var usedWords: MutableSet<String> = mutableSetOf()

    init {
        resetGame()
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score + SCORE_INCREASE
            updateGameState(updatedScore)
        } else {
            _uiState.value = _uiState.value.copy(isGuessedWordWrong = true)
        }
        userGuess = ""
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        userGuess = ""
    }

    private fun pickRandomWord(): String {
        currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWord()
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(word, false)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.value = _uiState.value.copy(
                isGameOver = true,
                score = updatedScore,
                isGuessedWordWrong = false
            )
        } else {
            _uiState.value = GameUiState(
                currentScrambledWord = pickRandomWord(),
                score = updatedScore,
                currentWordCount = _uiState.value.currentWordCount + 1,
                isGuessedWordWrong = false,
                isGameOver = false
            )
        }
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(
            currentScrambledWord = pickRandomWord(),
            score = 0,
            currentWordCount = 1,
            isGuessedWordWrong = false,
            isGameOver = false
        )
    }
}
