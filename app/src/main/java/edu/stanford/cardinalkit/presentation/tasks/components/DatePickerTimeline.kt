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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*

@Suppress("LongMethod")
@ExperimentalComposeUiApi
@Composable
fun DatePickerTimeline(
    modifier: Modifier = Modifier,
    state: DatePickerState = rememberDatePickerState(LocalDate.now()),
    backgroundBrush: Brush,
    selectedBackgroundBrush: Brush,
    eventIndicatorColor: Color = MaterialTheme.colors.primaryVariant,
    eventDates: List<LocalDate>,
    pastDaysCount: Int = 120,
    orientation: Orientation = Orientation.Horizontal,
    selectedTextColor: Color = MaterialTheme.colors.onSurface,
    dateTextColor: Color = MaterialTheme.colors.onSurface,
    todayLabel: @Composable BoxScope.() -> Unit = {},
    onDateSelected: (LocalDate) -> Unit
) {
    // The first date shown on the calendar
    val startDate by remember {
        mutableStateOf(state.initialDate.minusDays(pastDaysCount.toLong()))
    }

    val currentEventDates by rememberUpdatedState(newValue = eventDates)

    var totalWindowWidth by remember { mutableStateOf(1) }

    val selectedDateIndex = ChronoUnit.DAYS.between(startDate, state.initialDate).toInt()

    val coroutineScope = rememberCoroutineScope()

    // placeholder for how many items a row/column can occupy. Actual value gets calculated after placement
    var span by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()

    // Don't scroll if selected date is already visible on the screen
    val isVisible = listState.layoutInfo.visibleItemsInfo.any {
        it.index == selectedDateIndex
    }

    // We don't want smooth scrolling during initial composition
    var isInitialComposition by remember {
        mutableStateOf(true)
    }

    // scroll to the selected date when it changes
    LaunchedEffect(state.initialDate) {
        if (!isInitialComposition) {
            val scrollPosition = selectedDateIndex - span / 2

            if (scrollPosition <= 0) {
                // Invalid start date, so just scroll to the first item
                listState.animateScrollToItem(0)
            } else if (state.shouldScrollToSelectedDate && !isVisible) {
                listState.animateScrollToItem(scrollPosition.toInt())
            }

            // Reset the shouldScrollToSelectedDate flag
            state.onScrollCompleted()
        }
    }

    LaunchedEffect(Unit) {
        listState.scrollToItem(selectedDateIndex.toInt())

        state.onScrollCompleted()
        isInitialComposition = false
    }

    Surface(
        elevation = 6.dp,
        modifier = modifier
            .onPlaced {
                totalWindowWidth =
                    if (orientation == Orientation.Horizontal) it.size.width else it.size.height
            }
    ) {
        Column(
            modifier = Modifier
                .then(if (orientation == Orientation.Vertical) Modifier.fillMaxHeight() else Modifier.fillMaxWidth())
                .background(brush = backgroundBrush)
                .padding(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        coroutineScope.launch {
                            state.smoothScrollToDate(LocalDate.now())

                            // state.smoothScrollToDate is backed by a MutableState which doesn't cause recomposition
                            // when the user still has today's date selected because currentValue will be the same
                            // as the applied value. This can happen when the user selects today's date and flings the
                            // calendar. Technically they still have today's date selected so clicking on the 'Today'
                            // text after the fling animation does nothing. We perform this extra step to see if today's
                            // date is visible on the screen. If yes, do nothing, else scroll to it
                            if (!isVisible) {
                                listState.animateScrollToItem((selectedDateIndex - span / 2).toInt())
                            }
                        }
                    }
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                todayLabel()
            }

            Spacer(modifier = Modifier.height(4.dp))

            DatePickerLayout(
                orientation = orientation,
                listState = listState,
            ) {
                items(Integer.MAX_VALUE) { position ->
                    val date = startDate.plusDays(position.toLong())
                    val isEventDate = currentEventDates.contains(date)

                    DateCard(
                        modifier = Modifier
                            .onPlaced {
                                span =
                                    totalWindowWidth / if (orientation == Orientation.Horizontal) {
                                    it.size.width
                                } else {
                                    it.size.height
                                }
                            },
                        date = date,
                        isSelected = date == state.initialDate,
                        onDateSelected = {
                            onDateSelected(it)
                            state.smoothScrollToDate(it)
                        },
                        selectedBackgroundBrush = selectedBackgroundBrush,
                        selectedTextColor = selectedTextColor,
                        dateTextColor = dateTextColor,
                        isEventDate = isEventDate,
                        eventIndicatorColor = eventIndicatorColor
                    )
                }
            }
        }
    }
}
@Composable
private fun DatePickerLayout(
    orientation: Orientation,
    listState: LazyListState,
    content: LazyListScope.() -> Unit,
) {
    when (orientation) {
        Orientation.Vertical -> {
            LazyColumn(
                modifier = Modifier.testTag(tag = "datepickertimeline"),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState,
                content = content
            )
        }
        Orientation.Horizontal -> {
            LazyRow(
                modifier = Modifier.testTag(tag = "datepickertimeline"),
                verticalAlignment = Alignment.CenterVertically,
                state = listState,
                content = content
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun DatePickerTimeline(
    modifier: Modifier = Modifier,
    state: DatePickerState = rememberDatePickerState(),
    backgroundColor: Color = MaterialTheme.colors.surface,
    selectedBackgroundColor: Color = MaterialTheme.colors.secondaryVariant,
    eventIndicatorColor: Color = MaterialTheme.colors.primaryVariant,
    eventDates: List<LocalDate> = listOf(),
    pastDaysCount: Int = 120,
    orientation: Orientation = Orientation.Horizontal,
    dateTextColor: Color = MaterialTheme.colors.onSurface,
    selectedTextColor: Color = MaterialTheme.colors.onSurface,
    todayLabel: @Composable BoxScope.() -> Unit = {},
    onDateSelected: (LocalDate) -> Unit
) {
    DatePickerTimeline(
        modifier = modifier,
        state = state,
        backgroundBrush = SolidColor(backgroundColor),
        selectedBackgroundBrush = SolidColor(selectedBackgroundColor),
        eventIndicatorColor = eventIndicatorColor,
        eventDates = eventDates,
        orientation = orientation,
        pastDaysCount = pastDaysCount,
        onDateSelected = onDateSelected,
        selectedTextColor = selectedTextColor,
        dateTextColor = dateTextColor,
        todayLabel = todayLabel,
    )
}
private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

@Composable
private fun DateCard(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    isEventDate: Boolean,
    eventIndicatorColor: Color,
    onDateSelected: (LocalDate) -> Unit,
    selectedBackgroundBrush: Brush,
    selectedTextColor: Color = MaterialTheme.colors.onSurface,
    dateTextColor: Color = MaterialTheme.colors.onSurface
) {
    Column(
        modifier = modifier
            .testTag(tag = dateFormatter.format(date))
            .clip(shape = RoundedCornerShape(16.dp))
            .then(
                if (isSelected) {
                    Modifier.background(
                        brush = selectedBackgroundBrush,
                        alpha = 0.65f,
                    )
                } else Modifier
            )
            .padding(vertical = 4.dp)
            .clickable {
                onDateSelected(date)
            }
            .padding(vertical = 2.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val textColor = if (isSelected) selectedTextColor else dateTextColor

        Text(
            text = date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(),
            color = textColor
        )
        Text(
            text = date.dayOfMonth.toString(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = textColor
        )

        Text(
            text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(),
            color = textColor
        )

        if (isEventDate) {
            Spacer(modifier = Modifier.height(2.dp))

            Divider(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(14.dp),
                thickness = 3.dp,
                color = eventIndicatorColor
            )
        }
    }
}

enum class Orientation {
    Vertical,
    Horizontal
}
