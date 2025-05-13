package com.example.wardrobe.ui.theme.outfitlist

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wardrobe.model.Outfit
import com.example.wardrobe.model.OutfitModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutfitListScreen() {
    val outfits = remember { mutableStateListOf<OutfitModel>() }

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val outfitRef = FirebaseDatabase.getInstance().getReference("Outfits")

    outfitRef.orderByChild("userId").equalTo(userId)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    outfits.clear()
                    for (dataSnapshot in snapshot.children) {
                        val outfit = dataSnapshot.getValue(OutfitModel::class.java)
                        if (outfit != null) {
                            outfits.add(outfit)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Outfit", "Error fetching outfits: ${error.message}")
            }
        })

    LazyColumn {
        items(outfits) { outfit ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {

                    }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(outfit.name, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(outfit.description, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
