package edu.stanford.cardinalkit.domain.use_cases.tasks

import edu.stanford.cardinalkit.domain.repositories.TasksRepository

class GetTaskLogs(
    private val repository: TasksRepository
) {
    operator fun invoke() = repository.getTaskLogs()
}