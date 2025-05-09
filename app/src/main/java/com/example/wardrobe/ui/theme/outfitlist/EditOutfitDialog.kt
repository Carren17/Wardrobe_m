package com.example.wardrobe.ui.theme.outfitlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wardrobe.model.Outfit

@Composable
fun EditOutfitDialog(
    outfit: Outfit,
    onDismiss: () -> Unit,
    onSave: (Outfit) -> Unit
) {
    var name by remember { mutableStateOf(outfit.name) }
    var description by remember { mutableStateOf(outfit.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Outfit") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    singleLine = false
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(outfit.copy(name = name, description = description))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
