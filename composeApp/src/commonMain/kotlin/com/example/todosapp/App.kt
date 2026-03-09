package com.example.todosapp

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todosapp.viewmodel.UsersScreenState
import com.example.todosapp.viewmodel.UsersViewModel
import com.example.todosapp.viewmodel.usersModelFactory

@Composable
@Preview
fun App() {
    MaterialTheme {

        val usersViewModel = viewModel<UsersViewModel>(factory = usersModelFactory)
        val screenState by usersViewModel.uiState.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { usersViewModel.loadUsers() }) {
                Text("Show Users while on ${getPlatform().name}")
            }

            when(val screenState = screenState) {
                is UsersScreenState.Error -> {
                    Text("ERROR: ${screenState.msg}")
                }
                UsersScreenState.Loading ->
                    CircularProgressIndicator()
                UsersScreenState.Nothing -> {
                    Text("Click the button to load the data")
                }
                is UsersScreenState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(screenState.users){ user ->
                            Card(Modifier.fillMaxWidth()) {
                                Column(Modifier.fillMaxWidth().padding(12.dp)) {
                                    Text("Name: ${user.name}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}