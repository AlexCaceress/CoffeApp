package com.example.coffeapp.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.network.HttpException
import com.example.coffeapp.repository.Repository
import com.example.coffeapp.room.CoffeeDB
import com.example.coffeapp.utils.RetrofitInstance
import com.example.coffeapp.viewmodel.CoffeeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val db = CoffeeDB.getInstance(context)
    val repository = Repository(db)
    val coffeeViewModel = CoffeeViewModel(repository)

    LaunchedEffect(key1 = true) {

        withContext(Dispatchers.IO) {
            try {
                val isDatabaseEmpty = repository.isDatabaseEmpty()

                Log.e("Database empty", isDatabaseEmpty.toString())

                if (isDatabaseEmpty) {
                    val response = try {
                        RetrofitInstance.api.getCoffes()
                    } catch (e: HttpException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "HTTP error ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return@withContext
                    }

                    if (response.isSuccessful && response.body() != null) {
                        val coffeesEntity = repository.mapDataItemToCoffeeEntity(response.body()!!)
                        withContext(Dispatchers.Main) {
                            coffeeViewModel.insertAllCoffees(coffeesEntity)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("Database error", e.toString())
                    Toast.makeText(context, "Unexpected error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    NavHost(navController = navController, startDestination = "HomeScreen") {

        composable(route = "HomeScreen") {
            HomeScreen(navController = navController, viewModel = coffeeViewModel)
        }

        composable(
            route = "DetailScreen?title={title}&id={id}&image={image}&description={description}&ingredients={ingredients}",
            arguments = listOf(
                navArgument(name = "title") {
                    type = NavType.StringType
                },
                navArgument(name = "id") {
                    type = NavType.StringType
                },
                navArgument(name = "description") {
                    type = NavType.StringType
                },
                navArgument(name = "image") {
                    type = NavType.StringType
                },
                navArgument(name = "ingredients") {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen(
                navController = navController,
                title = it.arguments?.getString("title"),
                id = it.arguments?.getString("id"),
                image = it.arguments?.getString("image"),
                description = it.arguments?.getString("description"),
                ingredients = it.arguments?.getString("ingredients"),
                viewModel = coffeeViewModel
            )
        }

    }
}