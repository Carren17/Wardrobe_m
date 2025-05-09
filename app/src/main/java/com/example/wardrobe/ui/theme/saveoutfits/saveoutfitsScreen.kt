package com.example.wardrobe.ui.theme.saveoutfits

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
fun SavedOutfitsScreen(navController: NavController) {
    val savedOutfits = remember {
        mutableStateListOf(
            Outfit("1", "Weekend Casual", "Hoodie with ripped jeans"),
            Outfit("2", "Office Look", "Blazer with formal pants"),
            Outfit("3", "Summer Vibes", "Linen shirt with shorts")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Saved Outfits") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(savedOutfits) { outfit ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
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
fun SaveOutfitsScreenPreview() {
    SavedOutfitsScreen(rememberNavController())

}