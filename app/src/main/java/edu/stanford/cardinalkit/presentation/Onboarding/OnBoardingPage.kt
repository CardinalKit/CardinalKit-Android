package edu.stanford.cardinalkit.presentation.Onboarding

import androidx.annotation.DrawableRes
import edu.stanford.cardinalkit.R


sealed class OnBoardingPage(
    @DrawableRes
    val image: Int?,
    val title: String,
    val description: String
) {
    object Content: OnBoardingPage(
        image = null,
        title ="",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam consequat metus id tellus aliquam, quis facilisis metus luctus. Vivamus vitae augue scelerisque, semper neque quis, tempor enim. Ut pulvinar eleifend massa, non ultricies orci volutpat eu. Ut nec sagittis est. Cras fermentum congue orci eget hendrerit. Phasellus malesuada sem eget orci aliquam, ut cursus magna ornare. Maecenas a turpis eu diam dictum tempus in quis neque.Vestibulum et lacus aliquet, volutpat massa ac, placerat ex. Nulla rutrum suscipit quam eu posuere. Praesent placerat, lacus vitae finibus ultricies, justo quam interdum augue, ac egestas velit orci elementum purus.Sed sed eros pellentesque, viverra orci hendrerit, sodales lorem. Mauris nec dolor quis est ultricies commodo in ut risus. Sed imperdiet rhoncus tincidunt. Vestibulum et lacus aliquet, volutpat massa ac, placerat ex. Nulla rutrum suscipit quam eu posuere. Duis non vulputate ipsum.  "

    )
    object First : OnBoardingPage(
        image = R.drawable.first,
        title = "Data Gathering",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object Second : OnBoardingPage(
        image = R.drawable.second,
        title = "Privacy",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object Third : OnBoardingPage(
        image = R.drawable.third,
        title = "Data Use",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object Fourth: OnBoardingPage(
        image =R.drawable.fourth,
        title = "Withdrawing",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

}

