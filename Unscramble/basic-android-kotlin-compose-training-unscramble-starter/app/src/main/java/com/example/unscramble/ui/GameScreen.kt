package com.example.unscramble.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.R
import com.example.unscramble.ui.theme.UnscrambleTheme

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge)

        GameLayout(
            currentScrambledWord = gameUiState.currentScrambledWord,
            userGuess = gameViewModel.userGuess,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding),
            isGuessWrong = gameUiState.isGuessedWordWrong
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { gameViewModel.checkUserGuess() }
            ) {
                Text(text = stringResource(R.string.submit), fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = { gameViewModel.skipWord() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.skip), fontSize = 16.sp)
            }
        }

        GameStatus(score = gameUiState.score, modifier = Modifier.padding(20.dp))
    }
}

@Composable
fun GameLayout(
    currentScrambledWord: String,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier,
    isGuessWrong: Boolean
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(text = currentScrambledWord, style = MaterialTheme.typography.displayMedium)
            if (isGuessWrong) {
                Text(
                    text = stringResource(R.string.wrong_guess),
                    color = MaterialTheme.colorScheme.error
                )
            }
            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                onValueChange = onUserGuessChanged,
                label = { Text(stringResource(R.string.enter_your_word)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
                modifier = Modifier.fillMaxWidth(),
                isError = isGuessWrong
            )
        }
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(
            text = stringResource(R.string.score, score),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        GameScreen()
    }
}
