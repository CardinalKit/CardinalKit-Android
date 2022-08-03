package edu.stanford.cardinalkit.presentation.home.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LearnMoreCard(){
    val context = LocalContext.current
    Card(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://cardinalkit.sites.stanford.edu/")
            }
            context.startActivity(intent, bundleOf())
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        backgroundColor = PrimaryTheme,
        shape= RoundedCornerShape(18)
    ){
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text= "Learn About CardinalKit",
                color=Color.White,
                fontWeight= FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = 20.sp
            )
            Text(
                text= "Visit Cardinalkit.org",
                color=Color.White
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text= "See More",
                color=Color.White,
                fontWeight = FontWeight.SemiBold
            )

        }

    }
}