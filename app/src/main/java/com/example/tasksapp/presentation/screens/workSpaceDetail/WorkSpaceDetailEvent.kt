package com.example.tasksapp.presentation.screens.workSpaceDetail

import java.time.LocalDateTime

sealed class WorkSpaceDetailEvent {

    object OnAllRefresh: WorkSpaceDetailEvent()
    object OnTasksRefresh: WorkSpaceDetailEvent()
    object OnUsersRefresh: WorkSpaceDetailEvent()

    object OpenCloseAddTaskDialog: WorkSpaceDetailEvent()
    object OpenCloseAddUserDialog: WorkSpaceDetailEvent()
    data class OpenCloseSetTaskStatusDialog(val taskId: String = ""):WorkSpaceDetailEvent()

    data class SetTaskNameInDialog(val newName: String): WorkSpaceDetailEvent()
    data class SetTaskDescriptionInDialog(val newDescription:String): WorkSpaceDetailEvent()
    data class SetTaskDeadLineDialog(val deadLine: LocalDateTime): WorkSpaceDetailEvent()
    data class SetUserLoginInDialog(val newUserLogin:String): WorkSpaceDetailEvent()
    data class SetTaskStatusDialog(val newStatus: String): WorkSpaceDetailEvent()

    data class SetTasksFilter(val filter:String): WorkSpaceDetailEvent()
    data class UserSelectDialog(val userLogin: String) : WorkSpaceDetailEvent()

    object OpenCloseDeleteWorkSpaceDialog : WorkSpaceDetailEvent()
    object DeleteWorkSpace : WorkSpaceDetailEvent()

    object OpenCloseLeaveDialog : WorkSpaceDetailEvent()
    object Leave: WorkSpaceDetailEvent()

    object AddTask: WorkSpaceDetailEvent()
    object AddUser: WorkSpaceDetailEvent()
    object SetTaskStatus: WorkSpaceDetailEvent()
}