package edu.stanford.cardinalkit.domain.usecases.tasks

import edu.stanford.cardinalkit.domain.repositories.TasksRepository

class GetTasks(
    private val repository: TasksRepository
) {
    operator fun invoke() = repository.getTasks()
}
