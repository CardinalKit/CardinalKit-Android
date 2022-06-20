package edu.stanford.cardinalkit.domain.models

sealed class CKResult<out T> {
    object Loading: CKResult<Nothing>()
    data class Success<out T>(val data: T?): CKResult<T>()
    data class Error(val e: Exception?): CKResult<Nothing>()
}