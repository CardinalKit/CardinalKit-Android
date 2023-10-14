package edu.stanford.healthconnectonomh.omhmodels


data class TypedUnitValue<T : Unit>(
    val unit: T,
    val value: Double
)