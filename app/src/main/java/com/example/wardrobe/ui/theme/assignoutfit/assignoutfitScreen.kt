package com.example.wardrobe.ui.theme.assignoutfit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wardrobe.navigation.ROUTE_ASSIGNOUTFIT_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignOutfitScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        DatePickerField(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(ROUTE_ASSIGNOUTFIT_SCREEN)
            },
            enabled = selectedDate.isNotEmpty()
        ) {
            Text("Assign Outfit")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AssignOutfitScreenPreview() {
    AssignOutfitScreen(rememberNavController())
}
