package com.example.todosapp

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todosapp.viewmodel.SearchUserIntent
import com.example.todosapp.viewmodel.UsersIntent
import com.example.todosapp.viewmodel.UsersScreenState
import com.example.todosapp.viewmodel.UsersViewModel
import com.example.todosapp.viewmodel.usersModelFactory

@Composable
@Preview
fun App() {
    MaterialTheme {

        val usersViewModel : UsersViewModel = viewModel(factory = usersModelFactory)
        val state = usersViewModel.state

        var text by remember { mutableStateOf("") }
        val searchState = usersViewModel.searchState

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row (){
                val maxLength = 2
                TextField(
                    value = text,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = {newText ->
                        if(newText.length <= maxLength){
                            text = newText
                            usersViewModel.processSearchIntent(
                                query=text,
                                searchIntent = SearchUserIntent.FetchData
                            )
                        }
                    },
                    label = { Text("Enter user id (2 digits max)") }
                )
            }
            Row {
                if(searchState.isLoading){
                    CircularProgressIndicator()
                }
                if(searchState.error?.isNotEmpty() ?: true){
                    Text("ERROR: ${state.error}")
                }
                if(searchState.user != null){
                    Text("User found!: ${searchState.user.name}")
                }
                Spacer(Modifier.size(40.dp))


            }

            Button(onClick = { usersViewModel.processIntent(UsersIntent.FetchData) }) {
                Text("Show Users while on ${getPlatform().name}")
            }

            if(state.isLoading){
                CircularProgressIndicator()
            }
            if(!state.error.isNullOrBlank()){
                Text("ERROR: ${state.error}")
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.users){ user ->
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