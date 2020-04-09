package com.example.hw_fragment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.hw_fragment.R
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.Storage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    companion object {
        val HABITS = "HABITS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.navigation_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, navigation_drawer)
        NavigationUI.setupWithNavController(navigation_view, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navigation_fragment)
        return NavigationUI.navigateUp(navController, navigation_drawer)
    }
}
