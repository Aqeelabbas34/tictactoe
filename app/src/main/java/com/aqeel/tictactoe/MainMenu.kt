package com.aqeel.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainMenu : AppCompatActivity() {
    private lateinit var singlePlayerBtn: Button
    private lateinit var multiPlayerBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        singlePlayerBtn= findViewById<Button>(R.id.singelID)
        multiPlayerBtn= findViewById<Button>(R.id.multiplayerID)
        singlePlayerBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("isSinglePlayer",true)
            startActivity(intent)
            finish()
        }
        multiPlayerBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("isSinglePlayer",false)
            startActivity(intent)
            finish()
        }

    }
}