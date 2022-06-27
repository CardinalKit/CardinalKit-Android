package edu.stanford.cardinalkit.data.repositories

import com.google.firebase.firestore.CollectionReference
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.SurveyResult
import edu.stanford.cardinalkit.domain.repositories.SurveyRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SurveyRepositoryImpl @Inject constructor(
    @Named("surveysRef") private val surveysRef: CollectionReference?
) : SurveyRepository {
    override suspend fun uploadSurvey(name: String, data: String) = flow {
        surveysRef?.let {
            try {
                emit(Response.Loading)
                val surveyId = surveysRef.document().id
                val survey = SurveyResult(
                    id = surveyId,
                    name = name,
                    data = data,
                    timestamp = LocalDateTime.now()
                )
                val upload = surveysRef.document(surveyId).set(survey).await()
                emit(Response.Success(upload))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }
    }
}