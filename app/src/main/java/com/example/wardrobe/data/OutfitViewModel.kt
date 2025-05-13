package com.example.wardrobe.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.wardrobe.model.Outfit
import com.example.wardrobe.model.OutfitModel
import com.example.wardrobe.navigation.ROUTE_OUTFITLIST_SCREEN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutfitViewModel : ViewModel() {
    fun saveOutfitToDatabase(
        outfit: OutfitModel,
        context: Context,
        navController: NavController,
        function: () -> Boolean
    ) {
        val outfitsRef = FirebaseDatabase.getInstance().getReference("Outfits")
        val outfitId = outfitsRef.push().key ?: return

        outfitsRef.child(outfitId).setValue(outfit).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Outfit successfully saved", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_OUTFITLIST_SCREEN)
            } else {
                Toast.makeText(context, "Failed to save outfit", Toast.LENGTH_LONG).show()
            }
        }
    }
        fun assignOutfit(
            outfitId: String,
            userId: String,
            date: String,
            context: Context,
            navController: NavController
        ) {
            val databaseRef = FirebaseDatabase.getInstance()
                .getReference("Users/$userId/assignedOutfits/$outfitId")

            val assignmentData = mapOf(
                "date" to date,
                "assigned" to true
            )

            databaseRef.setValue(assignmentData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Outfit assigned on $date!", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUTE_OUTFITLIST_SCREEN)
                    } else {
                        Toast.makeText(context, "Failed to assign outfit", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
fun saveOutfitToFirebase(
    outfit: Outfit,
    context: Context,
    navController: NavController,
    onComplete: () -> Unit
) {
    val ref = FirebaseDatabase.getInstance()
        .getReference("Outfits")
        .child(outfit.outfitId)

    ref.setValue(outfit).addOnCompleteListener { task ->
        onComplete()
        if (task.isSuccessful) {
            Toast.makeText(context, "Outfit saved!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        } else {
            Toast.makeText(context, "Failed to save outfit", Toast.LENGTH_SHORT).show()
        }
    }
        fun assignOutfit(
            outfitId: String,
            userId: String,
            date: String,
            context: Context,
            navController: NavController
        ) {
            val databaseRef = FirebaseDatabase.getInstance()
                .getReference("Users/$userId/assignedOutfits/$outfitId")

            val assignmentData = mapOf(
                "date" to date,
                "assigned" to true
            )

            databaseRef.setValue(assignmentData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Outfit assigned on $date!", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUTE_OUTFITLIST_SCREEN)
                    } else {
                        Toast.makeText(context, "Failed to assign outfit", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    val outfitRef = FirebaseDatabase.getInstance().getReference("Outfits")

    val newOutfit = OutfitModel(
        outfitId = "",
        name = "Casual Jeans",
        description = "Blue jeans with white tee",
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    )

        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val outfits = mutableListOf<OutfitModel>()
                    for (dataSnapshot in snapshot.children) {
                        val outfit = dataSnapshot.getValue(OutfitModel::class.java)
                        if (outfit != null) {
                            outfits.add(outfit)
                        }
                    }
                    Log.d("Outfit", "Fetched ${outfits.size} outfits")
                } else {
                    Log.d("Outfit", "No outfits found for this user")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Outfit", "Error fetching outfits: ${error.message}")
            }
        })
