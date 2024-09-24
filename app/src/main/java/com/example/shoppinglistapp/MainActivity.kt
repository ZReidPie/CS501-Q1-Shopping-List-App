package com.example.shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglistapp.ui.theme.ShoppingListAppTheme
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardCapitalization


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListAppTheme {
                ShoppingListApp()
            }
        }
    }
}

@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    // State to manage the list of items
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var itemsList by remember { mutableStateOf(mutableStateMapOf<String, String>()) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Input fields for new items
            TextField(
                value = itemName,
                onValueChange = {
                    // Filter out non-alphabet characters
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                        itemName = it
                    }
                },
                label = { Text("Item Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text
                )
            )

            // Only numbers for quantity
            TextField(
                value = itemQuantity,
                onValueChange = { itemQuantity = it },
                label = { Text("Quantity") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number
                )
            )

            // Button to add items
            Button(
                onClick = {
                    if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                        // Add item to the map (itemName -> itemQuantity)
                        //println(itemsList)
                        itemsList[itemName] = itemQuantity
                        itemName = ""
                        itemQuantity = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Text("Add Item")
            }

            // One other component of your choice
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // LazyColumn to list the items
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(itemsList.entries.toList()) { item ->
                    ShoppingListItem(item.key, item.value)
                }
            }
        }
    }
}

@Composable
fun ShoppingListItem(name: String, quantity: String) {
    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Quantity: ${quantity}", style = MaterialTheme.typography.bodyMedium)
        }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    ShoppingListAppTheme {
        ShoppingListApp()
    }
}
