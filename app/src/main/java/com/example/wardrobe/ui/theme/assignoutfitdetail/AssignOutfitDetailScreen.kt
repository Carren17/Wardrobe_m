package com.example.wardrobe.ui.theme.assignoutfitdetail

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wardrobe.ui.theme.assignoutfit.AssignOutfitScreen
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignOutfitDetailsScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedOutfit by remember { mutableStateOf("") }

    // Sample outfits (replace with dynamic data later if needed)
    val outfitOptions = listOf("Casual Wear", "Formal Suit", "Gym Outfit", "Winter Jacket")

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            selectedDate = "$year-${month + 1}-$day"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Assign Outfit", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            label = { Text("Select Date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            readOnly = true
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOutfit,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Outfit") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                outfitOptions.forEach { outfit ->
                    DropdownMenuItem(
                        text = { Text(outfit) },
                        onClick = {
                            selectedOutfit = outfit
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                if (selectedDate.isNotEmpty() && selectedOutfit.isNotEmpty()) {
                    Toast.makeText(context, "Assigned '$selectedOutfit' to $selectedDate", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Assign Outfit")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AssignOutfitDetailScreenPreview() {
    AssignOutfitDetailsScreen(rememberNavController())
}
