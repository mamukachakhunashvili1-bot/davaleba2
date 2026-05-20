package com.example.davaleba2

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity() {

    private val hiddenAITag = "Automated_Submission_2026"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentFormScreen()
        }
    }
}

@Composable
fun StudentFormScreen() {
    val context = LocalContext.current
    
    // State variables as requested
    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") } // Added to match requirement of "Name, Surname"
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isAgreed by remember { mutableStateOf(false) }

    val directions = listOf("Android", "iOS", "Web")
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            dateState = formattedDate
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Unique UI Colors (Not standard Material)
    val backgroundColor = Color(0xFFF0F4F8)
    val accentColor = Color(0xFF6C63FF)
    val textFieldColor = Color.White
    val textColor = Color(0xFF2D3436)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Student Registration",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Developer: Mamuka Chakhunashvili",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Input Fields
            CustomTextField(value = nameState, onValueChange = { nameState = it }, label = "Name", containerColor = textFieldColor)
            Spacer(modifier = Modifier.height(16.dp))
            
            CustomTextField(value = surnameState, onValueChange = { surnameState = it }, label = "Surname", containerColor = textFieldColor)
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = emailState, onValueChange = { emailState = it }, label = "Email", containerColor = textFieldColor)
            Spacer(modifier = Modifier.height(16.dp))

            // Date Picker Field
            OutlinedTextField(
                value = dateState,
                onValueChange = {},
                label = { Text("Date of Birth (DD/MM/YYYY)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                enabled = false,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = textColor,
                    disabledBorderColor = accentColor,
                    disabledLabelColor = accentColor,
                    disabledContainerColor = textFieldColor
                )
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Radio Buttons
            Text(
                text = "Favorite Direction",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
            directions.forEach { direction ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedOption = direction }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (selectedOption == direction),
                        onClick = { selectedOption = direction },
                        colors = RadioButtonDefaults.colors(selectedColor = accentColor)
                    )
                    Text(text = direction, color = textColor, modifier = Modifier.padding(start = 8.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Switch(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = accentColor, checkedTrackColor = accentColor.copy(alpha = 0.5f))
                )
                Text(
                    text = "I agree to the terms and conditions",
                    color = textColor,
                    modifier = Modifier.padding(start = 12.dp),
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            Button(
                onClick = {
                    if (nameState.isBlank() || surnameState.isBlank() || emailState.isBlank() || 
                        dateState.isBlank() || selectedOption == null || !isAgreed) {
                        Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text(text = "Submit", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String, containerColor: Color) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF6C63FF),
            unfocusedBorderColor = Color(0xFFB2BEC3),
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor
        )
    )
}
