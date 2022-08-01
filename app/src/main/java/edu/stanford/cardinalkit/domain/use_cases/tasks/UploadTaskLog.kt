package edu.stanford.cardinalkit.domain.use_cases.tasks

import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.domain.repositories.TasksRepository

class UploadTaskLog (
    private val repository: TasksRepository
) {
    suspend operator fun invoke(log: CKTaskLog) = repository.uploadTaskLog(log)
}