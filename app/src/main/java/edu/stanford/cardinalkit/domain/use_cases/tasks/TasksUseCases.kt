package edu.stanford.cardinalkit.domain.use_cases.tasks

data class TasksUseCases(
    val getTasks: GetTasks,
    val uploadTaskLog: UploadTaskLog,
    val getTaskLogs: GetTaskLogs
)
