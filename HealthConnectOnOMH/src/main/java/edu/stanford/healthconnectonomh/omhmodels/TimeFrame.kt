package edu.stanford.healthconnectonomh.omhmodels

sealed class TimeFrame

data class DateTimeTimeFrame(
    val dateTime: DateTime
) : TimeFrame() {
    companion object {
        fun parse(text: String) = DateTimeTimeFrame(DateTime.parse(text))
    }
}

data class TimeIntervalTimeFrame(
    val timeInterval: TimeInterval
) : TimeFrame()