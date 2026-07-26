package com.sayeed_dev.notiz.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R
import com.sayeed_dev.notiz.ui.theme.AccentBlue
import com.sayeed_dev.notiz.ui.theme.BackgroundCream
import com.sayeed_dev.notiz.ui.theme.ButtonColor
import com.sayeed_dev.notiz.ui.theme.PrimaryDark

@Composable
fun WelcomeScreen(
    onNavigateToLogin: ( ) -> Unit,
    onNavigateToSignUp: ( ) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "NOTIz",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                letterSpacing = 2.sp

            )
            Icon(
                painter = painterResource(id = com.sayeed_dev.notiz.R.drawable.notiz),
                contentDescription = "Notiz Icon",
                modifier = Modifier.size(280.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Organized your ideas,\n beautifully",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Capture thoughts, manage tasks, and access,\n your notes seamlessly anywhere.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(170.dp))

            Button(
                onClick = { onNavigateToLogin( ) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Already have an account?", color = Color.Gray, fontSize = 14.sp
                )
                Text(
                    text = " Sign Up",
                    fontSize = 14.sp,
                    color = AccentBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToSignUp() })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onNavigateToLogin = {},
        onNavigateToSignUp = {}
    )
}