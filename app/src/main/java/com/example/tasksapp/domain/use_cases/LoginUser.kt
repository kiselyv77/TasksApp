package com.example.tasksapp.domain.use_cases

import com.example.tasksapp.domain.repository.TasksRepository
import com.example.tasksapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val repository: TasksRepository
){

    operator fun invoke(login:String ,password:String): Flow<Resource<String>> = flow{
        try{
            emit(Resource.Loading<String>())
            val token = repository.loginUser(login, password)
            emit(Resource.Success<String>(token.token))
        } catch (exception: HttpException){
            val debugMessage = exception.message
            val massage = exception.response()?.errorBody()?.charStream()?.readText()?:"Не удалось распознать ошибку"
            emit(Resource.Error<String>(massage))
        }catch (exception: IOException){
            val debugMessage = exception.message
            val message = "Ошибка подключения проверьте подключение к сети"
            emit(Resource.Error<String>(message))
        }
    }
}