package com.example.tasksapp.domain.use_cases

import android.util.Log
import com.example.tasksapp.data.remote.dto.TaskDTO
import com.example.tasksapp.domain.repository.TasksRepository
import com.example.tasksapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddTask @Inject constructor(
    private val repository: TasksRepository
) {
    operator fun invoke(token:String, name:String ,description:String, workSpaceId: String): Flow<Resource<TaskDTO>> = flow{
        try{
            emit(Resource.Loading<TaskDTO>())
            val response = repository.addTaskToWorkSpace(token, name, description, workSpaceId)
            emit(Resource.Success<TaskDTO>(response))
        } catch (exception: HttpException){
            val debugMessage = exception.message
            val massage = exception.response()?.errorBody()?.charStream()?.readText()?:"Не удалось распознать ошибку"
            emit(Resource.Error<TaskDTO>(massage))
        }catch (exception: IOException){
            val debugMessage = exception.message
            val message = "Ошибка подключения проверьте подключение к сети"
            Log.d("debugMessage", debugMessage.toString())
            emit(Resource.Error<TaskDTO>(message.toString()))
        }
    }
}