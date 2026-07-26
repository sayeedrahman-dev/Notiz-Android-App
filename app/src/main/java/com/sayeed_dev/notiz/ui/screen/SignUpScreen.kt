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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayeed_dev.notiz.R
import com.sayeed_dev.notiz.ui.theme.AccentBlue
import com.sayeed_dev.notiz.ui.theme.BackgroundCream
import com.sayeed_dev.notiz.ui.theme.ButtonColor
import com.sayeed_dev.notiz.ui.theme.PrimaryDark


@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit
) {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // --- Logo & App Name ---
            Icon(
                painter = painterResource(id = R.drawable.notiz),
                contentDescription = "Notiz Icon",
                modifier = Modifier.size(80.dp),
                tint = Color.Unspecified
            )
            Text(
                text = "NOTIz",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryDark,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    // Email Input
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Full Name",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDark,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Full Name", color = Color.Gray) },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryDark,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // FullName Input
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Email",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDark,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Email", color = Color.Gray) },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryDark,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Password Input
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Password",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDark,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Password", color = Color.Gray) },
                            shape = RoundedCornerShape(12.dp),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image =
                                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = image,
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                }
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryDark,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Confirm Password
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Confirm Password",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDark,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Confirm Password",
                                color = Color.Gray) },
                            shape = RoundedCornerShape(12.dp),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image =
                                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = image,
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                }
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryDark,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                }
            }

            Spacer(modifier = Modifier.height(70.dp))

            // Create Button
            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                ),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text = "Create Account",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(7.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Already have an account?", color = Color.Gray, fontSize = 14.sp
                )
                Text(
                    text = " Sign In",
                    fontSize = 14.sp,
                    color = AccentBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin()})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {

}