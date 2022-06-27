package edu.stanford.cardinalkit.data.repositories

import com.google.firebase.firestore.CollectionReference
import edu.stanford.cardinalkit.domain.models.CKResult
import edu.stanford.cardinalkit.domain.models.Survey
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
                emit(CKResult.Loading)
                val surveyId = surveysRef.document().id
                val survey = Survey(
                    id = surveyId,
                    name = name,
                    data = data,
                    timestamp = LocalDateTime.now()
                )
                val upload = surveysRef.document(surveyId).set(survey).await()
                emit(CKResult.Success(upload))
            } catch (e: Exception) {
                emit(CKResult.Error(e))
            }
        }
    }
}