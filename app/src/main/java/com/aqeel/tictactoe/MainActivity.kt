package com.aqeel.tictactoe

import android.content.Intent
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
    private lateinit var gridLayout : GridLayout
   private lateinit var resultText : TextView
   private lateinit var player1count: TextView
   private lateinit var player2count: TextView
   private lateinit var restartBtn: Button

    private var activePlayer =1
    private  var player1=0
    private var player2=0
    private var isSinglePlayer= false
    private val gameState = IntArray(9) {0}
    //set of wining positions
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

         isSinglePlayer=intent.getBooleanExtra("isSinglePlayer",false)
        gridLayout = findViewById<GridLayout>(R.id.gridLayout)
         resultText = findViewById<TextView>(R.id.resultText)
         restartBtn = findViewById<Button>(R.id.resetBtn)

         player1count=findViewById<TextView>(R.id.player1Score)
         player2count=findViewById<TextView>(R.id.player2Score)
        for ( i in 0 until gridLayout.childCount){
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener{
            playGame(it,i)
            }

        }
       restartBtn.setOnClickListener{
           resetGame()
       }

    }
    private fun playGame(view: View,index: Int) {

        val button= view as Button
        if (gameState[index]==0){
            gameState[index]= activePlayer
            button.text=if (activePlayer==1)"x" else "O"
            Log.d("TicTacToe", "Game State: ${gameState.contentToString()}")
            Log.d("Game", "Current active player: $activePlayer")

            button.textSize = 60f
            button.isEnabled= false
            if (checkWinner()){
                if (activePlayer==1){
                    resultText.text="Player X wins !"
                    player1++
                    player1count.text="$player1"

                }else if (activePlayer==2){
                    resultText.text="Player O wins !"
                    player2++
                    player2count.text="$player2"
                }

                resultText.visibility= View.VISIBLE
                restartBtn.visibility= View.VISIBLE
                disableAllBtn()
            }else if (gameState.none{it==0}){

                resultText.text="Its a draw!"
                resultText.visibility= View.VISIBLE
                restartBtn.visibility= View.VISIBLE
            }else{
                activePlayer=if (activePlayer==1)2 else 1
                Log.d("Game", "Switched active player to: $activePlayer")
                if (isSinglePlayer && activePlayer==2){
                    playWithAI()
                }
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

    private fun resetGame(){
        for (i in 0 until gridLayout.childCount){
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.isEnabled= true
        }
        gameState.fill(0)
        resultText.visibility= View.GONE
        restartBtn.visibility= View.GONE
    }
    private fun playWithAI(){
        val emptyCells= gameState.indices.filter { gameState[it]==0 }
        for (index in emptyCells){
            gameState[index]=2
            if (checkWinner()){
              makeMove(index,"O")
                resultText.text="Player 0 wins!"
                var playerAI=0
                playerAI++
                player2count.text="$playerAI"
                resultText.visibility=View.VISIBLE
                restartBtn.visibility= View.VISIBLE
                disableAllBtn()
                return
            }
            gameState[index]=0
        }

        for (index in emptyCells){
            gameState[index]=1
            if (checkWinner()){
                gameState[index]=2
                makeMove(index,"O")
                return
            }
            gameState[index]=0
        }
        val randInt = emptyCells.random()
        gameState[randInt]=2
        makeMove(randInt,"O")
    }
    private fun makeMove(index: Int,symbol: String){
        val button=gridLayout.getChildAt(index) as Button
        button.text= symbol
        button.isEnabled=false
        Log.d("Game", "Move made by $symbol at index $index")
        activePlayer = 1
        Log.d("Game", "Active player switched to: $activePlayer")
       /* if (!checkWinner()){
            activePlayer=1
        }*/
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MainActivity, MainMenu::class.java))
        finish()
    }
}
