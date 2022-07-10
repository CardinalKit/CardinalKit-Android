package edu.stanford.cardinalkit.domain.models.tasks

data class CKTaskContext(
    val category: CKTaskCategory = CKTaskCategory.MISC,
    val uri: String = ""
)
