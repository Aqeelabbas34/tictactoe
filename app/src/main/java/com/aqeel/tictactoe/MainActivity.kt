package com.aqeel.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY

class MainActivity : AppCompatActivity() {

    private var activePlayer =1
    private val gameState = IntArray(9) {0}
    private val winningPosition= arrayOf(
        intArrayOf(0,1,2),
        intArrayOf(3,4,5),
        intArrayOf(6,7,8),
        intArrayOf(0,3,6),
        intArrayOf(1,4,7),
        intArrayOf(2,5,8),
        intArrayOf(0,4,8),
        intArrayOf(2,4,6),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        var resultText = findViewById<TextView>(R.id.resultText)
        val restartBtn = findViewById<Button>(R.id.resetBtn)
        for ( i in 0 until gridLayout.childCount){
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener{
            playGame(it,i,resultText,restartBtn)
            }

        }
       restartBtn.setOnClickListener{
           resetGame(gridLayout,resultText,restartBtn)
       }

    }
    private fun playGame(view: View,index: Int,resultText : TextView,restartButton: Button) {

        val button= view as Button
        if (gameState[index]==0){
            gameState[index]= activePlayer
            button.text=if (activePlayer==1)"X" else "O"
            Log.d("TicTacToe", "Game State: ${gameState.contentToString()}")

            button.textSize = 60f
            button.isEnabled= false
            if (checkWinner()){
                resultText.text = if (activePlayer==1)"Player X wins !" else "Player O wins!"
                resultText.visibility= View.VISIBLE
                restartButton.visibility= View.VISIBLE
                disableAllBtn()
            }else if (gameState.none{it==0}){

                resultText.text="Its a draw!"
                resultText.visibility= View.VISIBLE
                restartButton.visibility= View.VISIBLE
            }else{
                activePlayer=if (activePlayer==1)2 else 1
            }
        }

    }
    private  fun checkWinner(): Boolean{
        for (position in winningPosition){
            if (gameState[position[0]]== activePlayer && gameState[position[1]]==activePlayer && gameState[position[2]]==activePlayer) {
                Log.d("TicTacToe", "Winning combination: ${position.contentToString()}")
                return true
            }
        }
        return false
    }
    private fun disableAllBtn(){
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        for (i in 0 until gridLayout.childCount){
            gridLayout.getChildAt(i)
            .isEnabled= false
        }
    }

    private fun resetGame(gridLayout: GridLayout, resultText : TextView,restartButton : Button){
        for (i in 0 until gridLayout.childCount){
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.isEnabled= true
        }
        gameState.fill(0)
        resultText.visibility= View.GONE
        restartButton.visibility= View.GONE
    }
}