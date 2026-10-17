package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview
@Composable
fun LemonadeApp() {
    LemonTextAndImage(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun LemonTextAndImage(modifier: Modifier = Modifier) {
    // l'étape en cours (1 = citronnier, 2 = citron)
    var currentStep by remember { mutableStateOf(1) }

    // image affichée selon l'étape
    val imageResource = when (currentStep) {
        1 -> R.drawable.lemon_tree
        else -> R.drawable.lemon_squeeze
    }

    // libellé affiché selon l'étape
    val textResource = when (currentStep) {
        1 -> R.string.select_lemon
        else -> R.string.squeeze_lemon
    }

    // description de l'image selon l'étape
    val descriptionResource = when (currentStep) {
        1 -> R.string.lemon_tree_description
        else -> R.string.lemon_description
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(textResource))
        Button(onClick = { currentStep = 2 }) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = stringResource(descriptionResource)
            )
        }
    }
}