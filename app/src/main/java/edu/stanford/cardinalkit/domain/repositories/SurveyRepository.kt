package edu.stanford.cardinalkit.domain.repositories

import edu.stanford.cardinalkit.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    suspend fun uploadSurvey(name: String, data: String): Flow<Response<Void?>>
}