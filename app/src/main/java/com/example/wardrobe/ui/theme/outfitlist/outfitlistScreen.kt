package com.example.wardrobe.ui.theme.outfitlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wardrobe.model.Outfit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutfitListScreen(navController: NavController) {
    val outfits = remember {
        mutableStateListOf(
            Outfit("1", "Casual Jeans", "Blue jeans with white tee"),
            Outfit("2", "Formal Suit", "Navy blue suit for meetings"),
            Outfit("3", "Workout Wear", "Track pants with dri-fit shirt")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Outfits") })
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(outfits) { outfit ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
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
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OutfitlistScreenPreview() {
    OutfitListScreen(rememberNavController())

}