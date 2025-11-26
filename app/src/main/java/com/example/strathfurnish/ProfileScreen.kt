package com.example.strathfurnish

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

//main profile screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick:() -> Unit={},
    onHomeClick:() -> Unit={},
    onCartClick:() -> Unit={},
    onStoreClick:() -> Unit={}
){

    //get context for saving data
    val context=LocalContext.current
    var fullName by remember{mutableStateOf("")}
    var email by remember{mutableStateOf("")}
    var phone by remember{mutableStateOf("")}
    var profileImageUri by remember{mutableStateOf<Uri?>(null)}

    //load the data that is saved when screen opens
    LaunchedEffect(Unit){
        val prefs=context.getSharedPreferences("UserProfile",Context.MODE_PRIVATE)
        fullName=prefs.getString("fullName","")?:""
        email=prefs.getString("email","")?:""
        phone=prefs.getString("phone","")?:""
    }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract=ActivityResultContracts.GetContent()
    ){
        uri:Uri? ->
        profileImageUri=uri
    }

    //main screen layout
    Scaffold(
        topBar={
            TopAppBar(
                title={},
                navigationIcon={
                    IconButton(onClick=onBackClick){
                        Icon(
                            imageVector=Icons.Default.ArrowBack,
                            contentDescription="Back",
                            tint=Color.Black
                        )
                    }
                },
                actions= {
                    IconButton(onClick = { /* Favorite action */ }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.Black

                        )
                    }
                },
                colors=TopAppBarDefaults.topAppBarColors(
                    containerColor= Color.White
                )
            )
        },

        bottomBar={

            BottomNavigationBar(
                onHomeClick=onHomeClick,
                onCartClick=onCartClick,
                onStoreClick=onStoreClick,
                onProfileClick={ }
            )
        }
    ) { paddingValues ->
    // Main content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "My Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Picture Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                imagePickerLauncher.launch("image/*")
            }
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (profileImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(profileImageUri),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Default Profile",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Add Profile picture",
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Full Name Input
        ProfileInputField(
            label = "Full Name",
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = "Joy Mwangi"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Email Input
        ProfileInputField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            placeholder = "@strathmore.edu"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Phone Input
        ProfileInputField(
            label = "Phone",
            value = phone,
            onValueChange = { phone = it },
            placeholder = ""
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Save Button
        Button(
            onClick = {
                saveProfileData(context, fullName, email, phone)
            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4267B2)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Save",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}
}

// Reusable Input Field Component
@Composable
fun ProfileInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.width(90.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Input field
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

// Bottom Navigation Bar Component
@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onStoreClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = Color(0xFFF5F5F5),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onHomeClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = onCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = onStoreClick) {
                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = "Store",
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

// Function to save profile data
fun saveProfileData(context: Context, fullName: String, email: String, phone: String) {
    val prefs = context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
    prefs.edit().apply {
        putString("fullName", fullName)
        putString("email", email)
        putString("phone", phone)
        apply()
    }

    // Show success message
    android.widget.Toast.makeText(context, "Profile saved successfully!", android.widget.Toast.LENGTH_SHORT).show()
}

