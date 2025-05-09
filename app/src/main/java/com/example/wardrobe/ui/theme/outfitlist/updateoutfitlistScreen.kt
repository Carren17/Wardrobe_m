package com.example.wardrobe.ui.theme.outfitlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wardrobe.model.Outfit
import com.example.wardrobe.ui.theme.dashboard.DashboardScreen
import com.example.wardrobe.ui.theme.outfitlist.OutfitListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateOutfitListScreen(navController: NavHostController, outfitId: String) {
    var outfits by remember {
        mutableStateOf(
            listOf(
                Outfit("1", "Weekend Casual", "Hoodie with jeans"),
                Outfit("2", "Formal Meeting", "Suit with tie"),
                Outfit("3", "Gym Wear", "Tank top with shorts")
            )
        )
    }
    var showDialog by remember { mutableStateOf(false) }
    var selectedOutfit by remember { mutableStateOf<Outfit?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Update Outfit List") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(outfits) { outfit ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedOutfit = outfit
                            showDialog = true
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(outfit.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(outfit.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }

    selectedOutfit?.let { outfitToEdit ->
        EditOutfitDialog(
            outfit = outfitToEdit,
            onDismiss = { selectedOutfit = null },
            onSave = { updatedOutfit ->
                outfits = outfits.map {
                    if (it.id == updatedOutfit.id) updatedOutfit else it
                }
                selectedOutfit = null
            }

        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateOutfitListScreenPreview() {
OutfitListScreen(rememberNavController())
}