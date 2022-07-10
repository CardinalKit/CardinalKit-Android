package edu.stanford.cardinalkit.domain.use_cases.surveys

import edu.stanford.cardinalkit.domain.repositories.SurveyRepository

class UploadSurvey(
    private val repository: SurveyRepository
) {
    suspend operator fun invoke(
        name: String,
        data: String
    ) = repository.uploadSurvey(name, data)
}