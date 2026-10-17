package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LemonadeApp() {
    Scaffold(
        // barre jaune en haut avec le titre en gras, centré
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF9E44C)
                )
            )
        }
    ) { innerPadding ->
        LemonTextAndImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun LemonTextAndImage(modifier: Modifier = Modifier) {
    // l'étape en cours (1 = citronnier, 2 = citron, 3 = citronnade, 4 = verre vide)
    var currentStep by remember { mutableStateOf(1) }

    // nombre de pressions restantes pour presser le citron
    var squeezeCount by remember { mutableStateOf(0) }

    // image affichée selon l'étape
    val imageResource = when (currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    // libellé affiché selon l'étape
    val textResource = when (currentStep) {
        1 -> R.string.select_lemon
        2 -> R.string.squeeze_lemon
        3 -> R.string.drink_lemonade
        else -> R.string.restart_empty_glass
    }

    // description de l'image selon l'étape
    val descriptionResource = when (currentStep) {
        1 -> R.string.lemon_tree_description
        2 -> R.string.lemon_description
        3 -> R.string.lemonade_glass_description
        else -> R.string.empty_glass_description
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // l'image est un bouton carré aux angles arrondis, avec une couleur d'accentuation
        Button(
            onClick = {
                when (currentStep) {
                    1 -> {
                        // on cueille un citron : nombre de pressions aléatoire entre 2 et 4
                        currentStep = 2
                        squeezeCount = (2..4).random()
                    }
                    2 -> {
                        // chaque clic presse le citron une fois
                        squeezeCount--
                        if (squeezeCount == 0) {
                            currentStep = 3
                        }
                    }
                    3 -> currentStep = 4
                    else -> currentStep = 1
                }
            },
            modifier = Modifier.size(200.dp),
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC3ECD2)),
            contentPadding = PaddingValues(24.dp)
        ) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = stringResource(descriptionResource),
                modifier = Modifier.fillMaxSize()
            )
        }
        // espace de 16dp entre l'image et le libellé
        Spacer(modifier = Modifier.height(16.dp))
        // libellé avec une police plus grande que celle par défaut
        Text(
            text = stringResource(textResource),
            fontSize = 18.sp
        )
    }
}