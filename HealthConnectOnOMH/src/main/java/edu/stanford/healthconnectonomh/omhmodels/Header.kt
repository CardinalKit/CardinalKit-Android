package edu.stanford.healthconnectonomh.omhmodels

import java.util.*

data class Header(
    val uuid: UUID,
    val schemaId: SchemaId,
    val sourceCreationDateTime: DateTime,
    val modality: Modality? = null,
    val externalDatasheets: List<Datasheet>? = null
) {
    data class Datasheet(
        val datasheetType: String? = null,
        val datasheetReference: String
    )
}

