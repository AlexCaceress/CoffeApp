package com.example.coffeapp.screens

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

        scope.launch(Dispatchers.IO) {

            val isDatabaseEmpty = repository.isDatabaseEmpty()

            if (isDatabaseEmpty) {

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

                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        val coffeesEntity = repository.mapDataItemToCoffeeEntity(response.body()!!)
                        coffeeViewModel.insertAllCoffees(coffeesEntity)
                    }
                }

            }

        }

    }

    NavHost(navController = navController, startDestination = "HomeScreen") {

        composable(route = "HomeScreen") {
            HomeScreen(navController = navController, viewModel = coffeeViewModel)
        }

        composable(
            route = "DetailScreen?title={title}&id={id}&image={image}&description={description}",
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
            )
        ) {
            DetailScreen(
                navController = navController,
                title = it.arguments?.getString("title"),
                id = it.arguments?.getString("id"),
                image = it.arguments?.getString("image"),
                description = it.arguments?.getString("description"),
                viewModel = coffeeViewModel
            )
        }

    }
}