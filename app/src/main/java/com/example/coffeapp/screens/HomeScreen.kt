package com.example.coffeapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.coffeapp.models.CoffeesDataItem
import com.example.coffeapp.room.CoffeeEntity
import com.example.coffeapp.viewmodel.CoffeeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: CoffeeViewModel
) {

    val coffees by viewModel.coffees.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var inputTitle by remember { mutableStateOf("") }
    var inputDescription by remember { mutableStateOf("") }

    fun openDialog(){
        showDialog = true;
    }

    fun closeDialog(){
        showDialog = false
    }

    if (coffees.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text("Loading coffees...")
        }
    } else {

        Scaffold(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .systemBarsPadding(),

            floatingActionButton = {
                FloatingActionButton(
                    onClick = { openDialog() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            },

            content = {
                Column(
                    modifier = Modifier
                        .padding(paddingValues = it)
                ) {
                    Text(
                        text = "Popular Coffees",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        items(coffees) { coffee ->
                            CoffeeItem(coffee = coffee, navController = navController)
                        }

                    }

                }

            }

        )

    }

    if(showDialog){

        AlertDialog(
            onDismissRequest =  {closeDialog()},
            dismissButton = {
                Button(
                    onClick = {closeDialog()}
                ) {
                    Text("Cancel")
                }
            },
            title = {
                Text(
                    text = "New coffee",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(5.dp)
                )
            },
            text = {

                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    OutlinedTextField(
                        value = inputTitle,
                        onValueChange = {inputTitle = it},
                        label = {
                            Text(text = "Coffee Title")
                        },
                        placeholder = {
                            Text(text = "Enter your coffee title")
                        }
                    )
                    OutlinedTextField(
                        value = inputDescription,
                        onValueChange = {inputDescription = it},
                        label = {
                            Text(text = "Coffee Description")
                        },
                        placeholder = {
                            Text(text = "Enter your coffee description")
                        }
                    )
                }
            },
            confirmButton = {

                if(inputTitle.isNotEmpty() &&  inputDescription.isNotEmpty()){

                    Button(
                        onClick = {
                            val newCoffee = CoffeeEntity(
                                description = inputDescription,
                                title = inputTitle,
                                image = "https://cdn-icons-png.flaticon.com/512/924/924514.png", //Default coffee image
                            )

                            viewModel.addCoffee(newCoffee)
                            closeDialog()
                            inputTitle = ""
                            inputDescription = ""
                        }
                    ) {
                        Text(text = "Save")
                    }

                }

            }
        )

    }

}

@Composable
fun CoffeeItem(
    coffee: CoffeeEntity,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        onClick = {

            navController.navigate(
                "DetailScreen?title=${coffee.title}&id=${coffee.id}&image=${coffee.image}&description=${coffee.description}"
            )

        },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                AsyncImage(
                    model = coffee.image,
                    contentDescription = coffee.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = coffee.title,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

        }
    }
}