package edu.stanford.cardinalkit.domain.use_cases.tasklogs

data class TaskLogUseCases(
    val uploadTaskLog: UploadTaskLog,
    val getTaskLogs: GetTaskLogs
)
