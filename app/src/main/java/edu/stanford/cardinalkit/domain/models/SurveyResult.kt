package edu.stanford.cardinalkit.domain.models

import java.util.*

data class SurveyResult(
    var id: String,
    var name: String,
    var data: String,
    var date: Date
)