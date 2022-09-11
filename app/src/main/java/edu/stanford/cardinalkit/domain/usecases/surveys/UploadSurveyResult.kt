package edu.stanford.cardinalkit.domain.usecases.surveys

import edu.stanford.cardinalkit.domain.models.SurveyResult
import edu.stanford.cardinalkit.domain.repositories.SurveyRepository

class UploadSurveyResult(
    private val repository: SurveyRepository
) {
    suspend operator fun invoke(
        result: SurveyResult
    ) = repository.uploadSurveyResult(result)
}
