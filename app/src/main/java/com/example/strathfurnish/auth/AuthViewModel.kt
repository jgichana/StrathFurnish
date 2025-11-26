// In ui/auth/AuthViewModel.kt
package com.example.strathfurnish.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // LiveData to hold the current user information
    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    init {
        // Set the initial value to the current user when the ViewModel is created
        _user.value = auth.currentUser
    }

    fun isUserLoggedIn(): Boolean {
        return _user.value != null
    }
}