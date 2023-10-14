package edu.stanford.healthconnectonomh.omhmodels

sealed class TimeInterval : Schema {
    override val schemaId: SchemaId
        get() = SchemaId(IEEE_NAMESPACE.value, "time-interval", "1.0")
}

data class StartEndTimeInterval(
    val startDateTime: DateTime,
    val endDateTime: DateTime
) : TimeInterval()

data class StartDurationTimeInterval(
    val startDateTime: DateTime,
    val duration: DurationUnitValue
) : TimeInterval()

data class EndDurationTimeInterval(
    val endDateTime: DateTime,
    val duration: DurationUnitValue
) : TimeInterval()