package com.example.tasksapp.presentation.screens.messenger

import com.example.tasksapp.domain.model.MessageModel
import com.example.tasksapp.domain.model.UserModel

data class MessengerState(
    val my: UserModel = UserModel("", "", ""),
    val messagesList: List<MessageModel> = listOf<MessageModel>(),
    val inputMessage: String = "",
    val send: Boolean = false,
    val isLoading: Boolean = true,
    val error: String = "",
)