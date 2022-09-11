package edu.stanford.cardinalkit.presentation.tasks.components

/*
MIT License

Copyright (c) 2021 Rafsanjani Aziz

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import java.time.LocalDate

@Stable
interface DatePickerState {
    val initialDate: LocalDate
    val shouldScrollToSelectedDate: Boolean
    fun smoothScrollToDate(date: LocalDate)
    fun onScrollCompleted()
}

internal class DatePickerStateImpl(
    selectedDate: LocalDate,
    shouldScrollToSelectedDate: Boolean = true
) : DatePickerState {
    private var _initialDate by mutableStateOf(selectedDate, structuralEqualityPolicy())
    private var _shouldScrollToSelectedDate by mutableStateOf(
        shouldScrollToSelectedDate,
        structuralEqualityPolicy()
    )

    override fun onScrollCompleted() {
        _shouldScrollToSelectedDate = false
    }

    override val shouldScrollToSelectedDate: Boolean
        get() = _shouldScrollToSelectedDate

    override val initialDate: LocalDate
        get() = _initialDate

    override fun smoothScrollToDate(date: LocalDate) {
        _shouldScrollToSelectedDate = true
        _initialDate = date
    }

    companion object {
        val Saver: Saver<DatePickerState, *> = listSaver(
            save = {
                listOf(
                    it.initialDate.year,
                    it.initialDate.monthValue,
                    it.initialDate.dayOfMonth,
                    it.shouldScrollToSelectedDate.toString()
                )
            },
            restore = {
                DatePickerStateImpl(
                    selectedDate = LocalDate.of(
                        it[0].toString().toInt(), // year
                        it[1].toString().toInt(), // month
                        it[2].toString().toInt(), // day
                    ),

                    shouldScrollToSelectedDate = it[3].toString()
                        .toBoolean() // shouldScrollToSelectedDate
                )
            }
        )
    }
}

@Composable
fun rememberDatePickerState(initialDate: LocalDate = LocalDate.now()) =
    rememberSaveable(saver = DatePickerStateImpl.Saver) { DatePickerStateImpl(initialDate) }
