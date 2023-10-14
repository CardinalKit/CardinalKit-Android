package edu.stanford.healthconnectonomh.omhmodels

data class StepCount(
    val stepCount: StepCountUnitValue,
    val effectiveTimeFrame: TimeFrame,
    val descriptiveStatistic: DescriptiveStatistic? = null
) : Schema {
    override val schemaId: SchemaId
        get() = SchemaId(OMH_NAMESPACE.value, "step-count", "2.0")
}
