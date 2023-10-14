package edu.stanford.healthconnectonomh.omhmodels

enum class DescriptiveStatistic(
    private val customSchemaValue: String? = null
) : SchemaEnumValue, Schema {
    AVERAGE,
    COUNT,
    MAXIMUM,
    MEDIAN,
    MINIMUM,
    STANDARD_DEVIATION,
    SUM,
    VARIANCE,
    TWENTIETH_PERCENTILE("20th percentile"),
    EIGHTIETH_PERCENTILE("80th percentile"),
    LOWER_QUARTILE,
    UPPER_QUARTILE,
    QUARTILE_DEVIATION,
    FIRST_QUINTILE("1st quintile"),
    SECOND_QUINTILE("2nd quintile"),
    THIRD_QUINTILE("3rd quintile"),
    FOURTH_QUINTILE("4th quintile");

    override val schemaValue: String
        get() = customSchemaValue ?: name.lowercase().replace("_", " ")

    override val schemaId: SchemaId
        get() = SchemaId(IEEE_NAMESPACE.value, "descriptive-statistic", "1.0")
}