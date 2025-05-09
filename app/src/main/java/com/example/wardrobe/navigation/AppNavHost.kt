package com.example.wardrobe.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wardrobe.ui.theme.addclothes.AddClothesScreen
import com.example.wardrobe.ui.theme.assignoutfitdetail.AssignOutfitDetailsScreen
import com.example.wardrobe.ui.theme.splash.SplashScreen
import com.example.wardrobe.ui.theme.calendar.CalendarScreen
import com.example.wardrobe.ui.theme.dashboard.DashboardScreen
import com.example.wardrobe.ui.theme.login.LoginScreen
import com.example.wardrobe.ui.theme.outfitlist.UpdateOutfitListScreen
import com.example.wardrobe.ui.theme.register.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController:NavHostController = rememberNavController(),startDestination:String= ROUTE_SPLASH){
    NavHost(navController=navController,startDestination=startDestination){
        composable(ROUTE_SPLASH) {
            SplashScreen { isLoggedIn ->
                val nextRoute = if (isLoggedIn as Boolean) ROUTE_DASHBOARD else ROUTE_LOGIN
                navController.navigate(nextRoute) {
                    popUpTo(ROUTE_SPLASH) { inclusive = true }
                }
            }
        }

        composable(ROUTE_LOGIN) { LoginScreen(navController) }
        composable(ROUTE_REGISTER) { RegisterScreen(navController) }
        composable(ROUTE_DASHBOARD) { DashboardScreen(navController) }
        composable("assignOutfitDetails") {
            AssignOutfitDetailsScreen(navController)
        }
        composable(ROUTE_ADD_CLOTHES_SCREEN) { AddClothesScreen(navController) }
        composable(ROUTE_CALENDAR_SCREEN) { CalendarScreen(navController) }
        composable("$ROUTE_UPDATE_OUTFITLIST_SCREEN/{outfitId}") { backStackEntry ->
            val outfitId = backStackEntry.arguments?.getString("outfitId")!!
            UpdateOutfitListScreen(navController, outfitId)
        }

    }

}


