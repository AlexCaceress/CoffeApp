package com.example.coffeapp.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.network.HttpException
import com.example.coffeapp.models.CoffeesDataItem
import com.example.coffeapp.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    var coffees by remember {
        mutableStateOf(listOf<CoffeesDataItem>())
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {

        scope.launch(Dispatchers.IO) {

            val response = try {
                RetrofitInstance.api.getCoffes()
            } catch (e: HttpException) {
                Toast.makeText(
                    context,
                    "http error ${e.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            if(response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main){
                    coffees = response.body()!!
                    Log.i("Coffees Request", coffees.toString())
                }
            }

        }

    }

    NavHost(navController = navController, startDestination = "HomeScreen") {

        composable(route = "HomeScreen") {
            HomeScreen(coffees = coffees, navController = navController)
        }

        composable(route = "DetailScreen") {
            DetailScreen()
        }

    }
}