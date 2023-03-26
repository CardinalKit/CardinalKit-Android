package edu.stanford.cardinalkit.domain.usecases.surveys

import edu.stanford.cardinalkit.domain.repositories.SurveyRepository

class UploadSurveyResult(
    private val repository: SurveyRepository
) {
    suspend operator fun invoke(
        result: Map<String, Any>
    ) = repository.uploadSurveyResult(result)
}
