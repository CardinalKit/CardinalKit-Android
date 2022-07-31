package edu.stanford.cardinalkit.domain.use_cases.surveys

import edu.stanford.cardinalkit.domain.repositories.SurveyRepository

class GetSurvey(
    private val repository: SurveyRepository
) {
    operator fun invoke(
        name: String
    ) = repository.getSurvey(name)
}