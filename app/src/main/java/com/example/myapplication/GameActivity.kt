package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class GameActivity : AppCompatActivity() {
    // თამაშის ლოგიკა
    private lateinit var gameLogic: GameLogic
    // 9 უჯრა
    private lateinit var one: TextView
    private lateinit var two: TextView
    private lateinit var three: TextView
    private lateinit var four: TextView
    private lateinit var five: TextView
    private lateinit var six: TextView
    private lateinit var seven: TextView
    private lateinit var eight: TextView
    private lateinit var nine: TextView
    //self explanatory
    private lateinit var startNewGameButton: Button
    // მოთამაშეების ისტორიის შესანახი ცვლადები
    private lateinit var player1Points: TextView
    private lateinit var player2Points: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // layoutის ცვლილება
        setContentView(R.layout.activity_tictactoe)
        // კონსტრუქტორები
        gameLogic = GameLogic()

        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)
        startNewGameButton = findViewById(R.id.start_new_game_button)
        player1Points = findViewById(R.id.player_one_score)
        player2Points = findViewById(R.id.player_two_score)
        // events
        // რომელ უჯრაზეც დააკლიკებ position ობიექტში ჩაიწერება შესაბამისად 3ის მოდულით
        one.setOnClickListener { onBoxClicked(one, Position(0, 0)) }
        two.setOnClickListener { onBoxClicked(two, Position(0, 1)) }
        three.setOnClickListener { onBoxClicked(three, Position(0, 2)) }
        four.setOnClickListener { onBoxClicked(four, Position(1, 0)) }
        five.setOnClickListener { onBoxClicked(five, Position(1, 1)) }
        six.setOnClickListener { onBoxClicked(six, Position(1, 2)) }
        seven.setOnClickListener { onBoxClicked(seven, Position(2, 0)) }
        eight.setOnClickListener { onBoxClicked(eight, Position(2, 1)) }
        nine.setOnClickListener { onBoxClicked(nine, Position(2, 2)) }

        // თამაშის დასრუკების შემდეგ თუ დაეჭირა startNewGameButton
        // ეს button გაქრება და საწყის ცვლადებზე ინიციალიზდება
        // მთლიანი თამაში
        startNewGameButton.setOnClickListener {
            startNewGameButton.visibility = View.GONE
            gameLogic.reset()
            resetboxes()
        }

        updatePoints()
    }

    private fun updatePoints() {
        player1Points.text = "First Player's Score: ${gameLogic.player1Points}"
        player2Points.text = "Second Player's Score: ${gameLogic.player2Points}"
    }

    // თამაშის გაგრძელების შემთხვევაში უჯრების reset
    private fun resetboxes() {
        //თუ რამე წერია უჯრაში (X, O)
        one.text = ""
        two.text = ""
        three.text = ""
        four.text = ""
        five.text = ""
        six.text = ""
        seven.text = ""
        eight.text = ""
        nine.text = ""
        // თუ უჯრებზე ხაზია გავლებული
        one.background = null
        two.background = null
        three.background = null
        four.background = null
        five.background = null
        six.background = null
        seven.background = null
        eight.background = null
        nine.background = null
        // თითოეულ უჯრაზე  event listenerის განახლება
        one.isEnabled = true
        two.isEnabled = true
        three.isEnabled = true
        four.isEnabled = true
        five.isEnabled = true
        six.isEnabled = true
        seven.isEnabled = true
        eight.isEnabled = true
        nine.isEnabled = true
    }


    private fun onBoxClicked(box: TextView, position: Position) {
        // მოწმდება დაჭერილი უჯრა თუ ცარიელია
        if (box.text.isEmpty()) {
            //მიმდინარე მოთამაშის შესაბამისი სიმბოლო (X,O) იწერება ამ უჯრაში
            box.text = gameLogic.currentPlayerMark
            val winningLine = gameLogic.makeMove(position)
            // თუ თამაში დასრულდა
            if (winningLine != null) {
                // ქულების განახლება
                updatePoints()
                //უჯრებზე დაჭერისას არაფერი მოხდება
                disableBoxes()
                // გამოჩნდება თამაშის დაჭყების button
                startNewGameButton.visibility = View.VISIBLE
                //  მოგებულ კომპოზიციაზე ხაზის გასმა
                showWinner(winningLine)
            }
        }
    }
    // ყველა უჯრაზე eventების გაუმება
    private fun disableBoxes() {
        one.isEnabled = false
        two.isEnabled = false
        three.isEnabled = false
        four.isEnabled = false
        five.isEnabled = false
        six.isEnabled = false
        seven.isEnabled = false
        eight.isEnabled = false
        nine.isEnabled = false
    }
    // ადგენს რომელ უჯრებს გაუსვას ხაზი და რომელი ხაზი (draweble რეპოზიტორიდან)
    private fun showWinner(winningComp: WinningComp) {
        val (winningBoxes, background) = when (winningComp) {
            WinningComp.ROW_0 -> Pair(listOf(one, two, three), R.drawable.horizontal_line)
            WinningComp.ROW_1 -> Pair(listOf(four, five, six), R.drawable.horizontal_line)
            WinningComp.ROW_2 -> Pair(listOf(seven, eight, nine), R.drawable.horizontal_line)
            WinningComp.COLUMN_0 -> Pair(listOf(one, four, seven), R.drawable.vertical_line)
            WinningComp.COLUMN_1 -> Pair(listOf(two, five, eight), R.drawable.vertical_line)
            WinningComp.COLUMN_2 -> Pair(listOf(three, six, nine), R.drawable.vertical_line)
            WinningComp.DIAGONAL_LEFT -> Pair(listOf(one, five, nine), R.drawable.left_diagonal_line)
            WinningComp.DIAGONAL_RIGHT -> Pair(listOf(three, five, seven), R.drawable.right_diagonal_line)
        }
        // winningBoxes aris area amitom yvela ujraze rom gaavlos xazi
        // titoeulze ketdeba cal-calke
        winningBoxes.forEach { box ->
            box.background = ContextCompat.getDrawable(GameActivity@ this, background)
        }
    }
}


