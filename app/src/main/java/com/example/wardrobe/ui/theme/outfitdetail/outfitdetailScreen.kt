package com.example.wardrobe.ui.theme.outfitdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wardrobe.data.OutfitViewModel
import com.example.wardrobe.model.Outfit
import com.google.firebase.auth.FirebaseAuth

@Composable
fun OutfitDetailScreen(outfitId: String?, navController: NavController) {
    val context = LocalContext.current

    val outfit = Outfit(outfitId ?: "0", "Casual Jeans", "Blue jeans with white tee")

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("Outfit Name: ${outfit.name}", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Description: ${outfit.description}", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                OutfitViewModel().assignOutfit(outfitId = outfit.outfitId, userId = userId, context = context, navController = navController)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Assign Outfit")
        }
    }
}
