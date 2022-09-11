package edu.stanford.cardinalkit.domain.usecases.tasklogs

data class TaskLogUseCases(
    val uploadTaskLog: UploadTaskLog,
    val getTaskLogs: GetTaskLogs
)
