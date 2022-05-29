package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var startNewGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // start page
        startNewGameButton = findViewById(R.id.startNewGameButton)

        // tamashis chamrtveli
        startNewGameButton.setOnClickListener {
            val intent = Intent(MainActivity@this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}