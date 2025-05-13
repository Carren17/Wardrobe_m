package com.example.wardrobe.ui.theme.updateoutfit

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wardrobe.model.OutfitModel
import com.google.firebase.database.FirebaseDatabase
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateOutfitScreen(outfitId: String, navController: NavController) {
    val context = LocalContext.current
    val outfitRef = FirebaseDatabase.getInstance().getReference("Outfits").child(outfitId)

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(outfitId) {
        outfitRef.get().addOnSuccessListener { snapshot ->
            val outfit = snapshot.getValue(OutfitModel::class.java)
            if (outfit != null) {
                name = outfit.name
                description = outfit.description
            }
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Update Outfit") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
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
                onClick = {
                    val updatedOutfit = mapOf(
                        "name" to name,
                        "description" to description
                    )
                    outfitRef.updateChildren(updatedOutfit).addOnSuccessListener {
                        Toast.makeText(context, "Outfit updated", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}
