package edu.stanford.cardinalkit.domain.repositories

import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.SurveyResult
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    suspend fun getSurvey(name: String): Flow<Response<String?>>
    suspend fun uploadSurveyResult(result: SurveyResult): Flow<Response<Void?>>
}