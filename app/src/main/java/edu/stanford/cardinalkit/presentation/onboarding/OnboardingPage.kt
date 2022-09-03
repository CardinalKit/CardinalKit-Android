package edu.stanford.cardinalkit.presentation.onboarding

import androidx.annotation.DrawableRes
import edu.stanford.cardinalkit.R

sealed class OnboardingPage(
    @DrawableRes
    val image: Int?,
    val title: Int?,
    val description: Int?
) {
    object First: OnboardingPage(
        image = R.drawable.first,
        title = R.string.data_gathering_title,
        description = R.string.data_gathering_description
    )

    object Second: OnboardingPage(
        image = R.drawable.second,
        title = R.string.privacy_title,
        description = R.string.privacy_description
    )

    object Third: OnboardingPage(
        image = R.drawable.third,
        title = R.string.data_use_title,
        description = R.string.data_use_description
    )

    object Fourth: OnboardingPage(
        image =R.drawable.fourth,
        title = R.string.withdrawing_title,
        description = R.string.withdrawing_description
    )
}