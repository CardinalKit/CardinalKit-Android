package edu.stanford.cardinalkit.domain.use_cases.surveys

import edu.stanford.cardinalkit.domain.use_cases.surveys.UploadSurveyResult

data class SurveysUseCases(
    val uploadSurveyResult: UploadSurveyResult,
    val getSurvey: GetSurvey
)