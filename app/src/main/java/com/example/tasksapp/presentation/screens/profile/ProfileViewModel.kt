package com.example.tasksapp.presentation.screens.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksapp.data.local.global.Token
import com.example.tasksapp.domain.use_cases.GetUserByToken
import com.example.tasksapp.domain.use_cases.LogOut
import com.example.tasksapp.domain.use_cases.SetUserStatus
import com.example.tasksapp.domain.use_cases.UploadNewAvatar
import com.example.tasksapp.util.Resource
import com.example.tasksapp.util.UserStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val uploadNewAvatarUseCase: UploadNewAvatar,
    private val getUserByToken: GetUserByToken,
    private val setUserStatusUseCase: SetUserStatus,
    private val logOutUseCase: LogOut,
    ) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init{
        loadData(Token.token)
    }

    fun onEvent(event: ProfileEvent){
        when(event){
            is ProfileEvent.Refresh ->{
                loadData(Token.token)
            }
            is ProfileEvent.LogOut ->{
                setUserStatus()
                logOut()
            }
            is ProfileEvent.UploadNewAvatarEvent -> {
                uploadNewAvatar(event.stream)
            }
        }
    }

    private fun uploadNewAvatar(stream: InputStream) {
        viewModelScope.launch {
            uploadNewAvatarUseCase(Token.token, stream).collect{ result ->
                Log.d("dsfvsedfsrvsdfsv", result.data.toString())
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _state.value = _state.value.copy(
                                profileImgKey = (0..1_000_000).random()
                            )
                        }
                    }
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {
                    }
                }
            }
        }
    }

    private fun logOut(){
        viewModelScope.launch {
            logOutUseCase().collect{ result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { message->
                            _state.value = _state.value.copy(
                                isLogOut = true,
                                error = result.message ?: "",
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = result.message ?: "", isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading,
                            error = result.message ?: ""
                        )
                    }
                }
            }
        }
    }

    private fun loadData(token: String){
        viewModelScope.launch {
            getUserByToken(token).collect{ result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { userDto->
                            _state.value = _state.value.copy(
                                name = userDto.name,
                                login = userDto.login,
                                status= userDto.status,
                                error = result.message ?: "",
                                isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = result.message ?: "", isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading,
                            error = result.message ?: ""
                        )
                    }
                }
            }
        }
    }

    private fun setUserStatus() {
        viewModelScope.launch {
            setUserStatusUseCase(Token.token, UserStatus.OFFLINE_STATUS).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("StartViewModel", result.data.toString())
                    }
                    is Resource.Error -> {
                        Log.d("StartViewModel", result.data.toString())
                    }
                    is Resource.Loading -> {
                        Log.d("StartViewModel", result.data.toString())
                    }
                }
            }
        }
    }
}