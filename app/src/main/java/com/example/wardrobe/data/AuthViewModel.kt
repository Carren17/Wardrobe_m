package com.example.wardrobe.data

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.navigation.NavController
import com.example.wardrobe.model.Outfit
import com.example.wardrobe.model.OutfitModel
import com.example.wardrobe.model.UserModel
import com.example.wardrobe.navigation.ROUTE_DASHBOARD
import com.example.wardrobe.navigation.ROUTE_LOGIN
import com.example.wardrobe.navigation.ROUTE_OUTFITLIST_SCREEN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AuthViewModel : ViewModel() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    fun signup(
        firstname: String, lastname: String, email: String, password: String,
        navController: NavController,
        context: Context
    ) {
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank()) {

            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()

            return
        }
        _isLoading.value = true

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid ?: ""
                    val userData = UserModel(
                        firstname = firstname, lastname = lastname,
                        email = email, password = password, userId = userId
                    )
                    saveUserToDatabase(userId, userData, navController, context)
                } else {
                    _errorMessage.value = task.exception?.message

                    Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun saveUserToDatabase(
        userId: String, userData: UserModel,
        navController: NavController, context: Context
    ) {
        val regRef = FirebaseDatabase.getInstance()
            .getReference("Users/$userId")
        regRef.setValue(userData).addOnCompleteListener { regRef ->
            if (regRef.isSuccessful) {

                Toast.makeText(context, "User successfully Registered", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_LOGIN)
            } else {
                _errorMessage.value = regRef.exception?.message

            }
        }
    }

    fun login(email: String, password: String, navController: NavController, context: Context) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Email and password required", Toast.LENGTH_LONG).show()

            return
        }
        _isLoading.value = true

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    Toast.makeText(context, "User successfully logged in", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_DASHBOARD)

                } else {
                    _errorMessage.value = task.exception?.message

                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun saveOutfit(name: String, description: String, context: Context) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val outfitId = FirebaseDatabase.getInstance().reference.push().key ?: return

        val savedOutfit = Outfit(
            outfitId = outfitId,
            name = name,
            description = description,
            userId = userId
        )

        FirebaseDatabase.getInstance()
            .getReference("SavedOutfits/$userId/$outfitId")
            .setValue(savedOutfit)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Outfit saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to save outfit", Toast.LENGTH_SHORT).show()
                }
            }
    }

}