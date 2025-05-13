package com.example.wardrobe.ui.theme.assignoutfit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wardrobe.model.AssignedOutfit
import com.example.wardrobe.navigation.ROUTE_OUTFITLIST_SCREEN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignOutfitScreen(outfitId: String, navController: NavController) {
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    fun assignOutfitToUser(
        outfitId: String,
        userId: String,
        context: Context,
        navController: NavController
    ) {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val assignedOutfit = AssignedOutfit(outfitId, userId, currentDate)

        val ref = FirebaseDatabase.getInstance().getReference("AssignedOutfits")
        ref.child(outfitId).setValue(assignedOutfit)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Outfit Assigned Successfully!", Toast.LENGTH_SHORT).show()
                    navController.navigate(ROUTE_OUTFITLIST_SCREEN)
                } else {
                    Toast.makeText(context, "Assignment Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Assign this outfit?", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                assignOutfitToUser(outfitId, userId, context, navController)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Assign Outfit")
        }
    }
}
