package com.example.coffeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.coffeapp.room.CoffeeEntity
import com.example.coffeapp.viewmodel.CoffeeViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    title: String?,
    id: String?,
    image: String?,
    description: String?,
    viewModel: CoffeeViewModel
) {

    var showDialog by remember { mutableStateOf(false) }
    var inputTitle by remember { mutableStateOf(title ?: "") }
    var inputDescription by remember { mutableStateOf(description ?: "") }

    fun onBackClick() {
        navController.popBackStack()
    }

    fun openDialog(){
        showDialog = true;
    }

    fun closeDialog(){
        showDialog = false
    }

    Scaffold(
        topBar = {
            TopMenu(onBackClick = { onBackClick() }, openDialog = { openDialog() })
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(paddingValues = it)
                .padding(10.dp)

        ) {
            Text(
                text = title ?: "No title Coffee",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Description: ",
                    fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
                Text(
                    text = description ?: "No description",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify
                )
            }

        }

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
                    text = "Update coffee",
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
                                id = id!!.toInt(),
                                description = inputDescription,
                                title = inputTitle,
                                image = image!!,
                            )

                            viewModel.updateCoffee(newCoffee)
                            navController.popBackStack()
                            closeDialog()

                        }
                    ) {
                        Text(text = "Save")
                    }

                }

            }
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopMenu(
    onBackClick: () -> Unit,
    openDialog : () -> Unit,
) {

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Coffee Detail",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(
                    onClick = openDialog,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Coffee"
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    )

}