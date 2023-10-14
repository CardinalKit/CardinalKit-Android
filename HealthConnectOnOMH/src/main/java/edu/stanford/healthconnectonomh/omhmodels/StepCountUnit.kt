package edu.stanford.healthconnectonomh.omhmodels

enum class StepCountUnit(
    override val schemaValue: String
) : Unit {
    STEPS("steps");
}

typealias StepCountUnitValue = TypedUnitValue<StepCountUnit>