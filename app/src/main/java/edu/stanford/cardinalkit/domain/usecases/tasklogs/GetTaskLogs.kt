package edu.stanford.cardinalkit.domain.usecases.tasklogs

import edu.stanford.cardinalkit.domain.repositories.TaskLogRepository

class GetTaskLogs(
    private val repository: TaskLogRepository
) {
    operator fun invoke() = repository.getTaskLogs()
}
