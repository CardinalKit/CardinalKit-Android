package edu.stanford.healthconnectonomh.omhmodels

enum class DurationUnit(
    override val schemaValue: String
) : Unit {
    PICOSECOND("ps"),
    NANOSECOND("ns"),
    MICROSECOND("us"),
    MILLISECOND("ms"),
    SECOND("sec"),
    MINUTE("min"),
    HOUR("h"),
    DAY("d"),
    WEEK("wk"),
    MONTH("Mo"),
    YEAR("yr");
}

typealias DurationUnitValue = TypedUnitValue<DurationUnit>