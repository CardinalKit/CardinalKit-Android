package edu.stanford.cardinalkit.domain.use_cases.tasklogs

import edu.stanford.cardinalkit.domain.repositories.TaskLogRepository

class GetTaskLogs(
    private val repository: TaskLogRepository
) {
    operator fun invoke() = repository.getTaskLogs()
}