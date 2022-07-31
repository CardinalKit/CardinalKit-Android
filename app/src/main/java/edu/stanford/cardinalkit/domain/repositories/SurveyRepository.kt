package edu.stanford.cardinalkit.domain.repositories

import edu.stanford.cardinalkit.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    fun getSurvey(name: String): Response<String>
    suspend fun uploadSurvey(name: String, data: String): Flow<Response<Void?>>
}