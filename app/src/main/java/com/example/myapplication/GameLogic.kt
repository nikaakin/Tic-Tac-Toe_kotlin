package com.example.myapplication


class GameLogic {
    //ცვლადები
    private var currentPlayer = 1
    var player1Points = 0
    var player2Points = 0
    // მიმდინარე მოთამშის შესაბამისი სიმბოლოს შემოწმება
    val currentPlayerMark: String
        get() {
            return if (currentPlayer == 1) "X" else "O"
        }
    // საწყისი მდგომარეობა
    private var state = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0)
    )
    // დაჭერისას
    fun makeMove(position: Position): WinningComp? {
        // დაჭერილ ადგილზე state  მატრიცის განახლება
        state[position.row][position.column] = currentPlayer
        // შემოწმება თუ თამაში დასრულდა
        val winningLine = hasGameEnded()

        // მოწმდება თუ თამაში გრძელდება
        if (winningLine == null) {
            //რადგან  currentPLayer საკმარისია იყოს მხოლოდ 1 ან 2
            //ამიტომ  3 - currentPlayer ყოველთვის განსხვავებულ მოთამშეს დააბრუნებს
            // 3-2 =1 და 3-1 =2
            currentPlayer = 3 - currentPlayer
        } else {
            // თუ თამაში დასრულდა მოემატება გაამრჯვებულ მოთამაშეს ქულა
            when (currentPlayer) {
                1 -> player1Points++
                2 -> player2Points++
            }
        }
        // ბრუნდება რომ თუ თამაში დასრულდა ხაზი გადაუსვას
        //მოგებულ პოზიციაზე მდგომ ელემენტებს
        return winningLine
    }
    // თამაშის გაგრძელების შემთხვევაში state არეის reset
    fun reset() {
        state = arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(0, 0, 0),
            intArrayOf(0, 0, 0)
        )
        currentPlayer = 1
    }
    // ამოწმებს ყველა სვეტს, რიგს და დიაგონალს, რომ დაადგინოს თამაში ხომ არ დასრულებულა
    //თუ რომელიმე ჩამოთილილში ერთნაირი სიმბოლოებია აბრუნებს ხაზს რომელიც გაევლება
    //წინააღმდეგ შემთხვევაში დაბრუნდება null
    private fun hasGameEnded(): WinningComp? {
        if (state[0][0] == currentPlayer && state[0][1] == currentPlayer && state[0][2] == currentPlayer) {
            return WinningComp.ROW_0
        } else if (state[1][0] == currentPlayer && state[1][1] == currentPlayer && state[1][2] == currentPlayer) {
            return WinningComp.ROW_1
        } else if (state[2][0] == currentPlayer && state[2][1] == currentPlayer && state[2][2] == currentPlayer) {
            return WinningComp.ROW_2
        } else if (state[0][0] == currentPlayer && state[1][0] == currentPlayer && state[2][0] == currentPlayer) {
            return WinningComp.COLUMN_0
        } else if (state[0][1] == currentPlayer && state[1][1] == currentPlayer && state[2][1] == currentPlayer) {
            return WinningComp.COLUMN_1
        } else if (state[0][2] == currentPlayer && state[1][2] == currentPlayer && state[2][2] == currentPlayer) {
            return WinningComp.COLUMN_2
        } else if (state[0][0] == currentPlayer && state[1][1] == currentPlayer && state[2][2] == currentPlayer) {
            return WinningComp.DIAGONAL_LEFT
        } else if (state[0][2] == currentPlayer && state[1][1] == currentPlayer && state[2][0] == currentPlayer) {
            return WinningComp.DIAGONAL_RIGHT
        }
        return null
    }


}
