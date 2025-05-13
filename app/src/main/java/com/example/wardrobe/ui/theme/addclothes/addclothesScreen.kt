package com.example.wardrobe.ui.theme.addclothes


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wardrobe.data.OutfitViewModel
import com.example.wardrobe.data.saveOutfitToFirebase
import com.example.wardrobe.model.Outfit
import com.example.wardrobe.model.OutfitModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOutfitScreen(
    navController: NavController,
    outfitViewModel: OutfitViewModel = viewModel()
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    fun saveOutfit() {
        if (name.isBlank() || description.isBlank()) {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        val newOutfit = OutfitModel(
            outfitId = "",
            name = name,
            description = description,
            userId = userId
        )

        isSaving = true
        outfitViewModel.saveOutfitToDatabase(newOutfit, context, navController) {
            isSaving = false
            Toast.makeText(context, "Outfit saved successfully!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add New Outfit") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Outfit Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { saveOutfit() },
                enabled = !isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isSaving) "Saving..." else "Save Outfit")
            }
        }
    }
}
