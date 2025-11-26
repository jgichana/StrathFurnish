package com.example.strathfurnish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrathFurnishTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ProfileScreen(
                        onBackClick = {
                            finish()
                        },
                        onHomeClick = {
                            // TODO: Navigate to home
                        },
                        onCartClick = {
                            // TODO: Navigate to cart
                        },
                        onStoreClick = {
                            // TODO: Navigate to store
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StrathFurnishTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    StrathFurnishTheme {
        ProfileScreen()
    }
}