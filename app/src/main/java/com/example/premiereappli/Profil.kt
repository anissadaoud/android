package com.example.premiereappli
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass


@Composable
fun Profil( classes: WindowSizeClass, onClick: () -> Unit) {
    val classeHauteur = classes.windowHeightSizeClass

    when (classeHauteur) {
        WindowHeightSizeClass.COMPACT -> {  // Cas d'un écran avec hauteur limitée
            CompactProfileLayout(onClick)
        }
        else -> {  // Cas d'une hauteur normale ou étendue
            RegularProfileLayout(onClick)
        }
    }
}

@Composable
fun RegularProfileLayout(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        ProfileImage()
        Spacer(Modifier.height(10.dp))
        UserInfo()
        Spacer(Modifier.height(20.dp))
        ContactInfo()
        Spacer(Modifier.height(20.dp))
        StartButton(onClick = onClick)
    }
}

@Composable
fun CompactProfileLayout(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row( verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical=4.dp)){
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                ProfileImage()
                Spacer(Modifier.height(10.dp))
                UserInfo()
            }
            Spacer(Modifier.width(100.dp))
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                ContactInfo()
                Spacer(Modifier.height(20.dp))
                StartButton(onClick = onClick)

            }
        }
    }
}

@Composable
fun ProfileImage() {
    Image(
        painter = painterResource(R.drawable.img_1762),
        contentDescription = "Photo",
        modifier = Modifier.clip(CircleShape)
    )
}

@Composable
fun UserInfo() {
    Text(
        text = "Anissa DAOUD",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(20.dp))
    Text(
        text = "Étudiante en FIA4",
        fontStyle = FontStyle.Italic
    )
    Text(
        text = "ISIS Castres",
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun ContactInfo() {
    Row {
        Image(
            painter = painterResource(R.drawable.capture_d_cran_2024_09_09___14_18_41),
            contentDescription = "Linkedin",
            modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp)
        )
        Text(text = "anissadaoud2@gmail.com")
    }
    Spacer(Modifier.height(10.dp))
    Row {
        Image(
            painter = painterResource(R.drawable.capture_d_cran_2024_09_09___14_12_04),
            contentDescription = "Linkedin",
            modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp)
        )
        Text(text = "www.linkedin.com")
    }
}

@Composable
fun StartButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEEB8D4)
        )
    ) {
        Text("Démarrer")
    }
}


