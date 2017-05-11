package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.ArrayList;

/**
 * Created by Ben on 5/11/2017.
 */
public class KalahUtilities {

    public static void printBoard(IO io, ArrayList<Player> players) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 " + players.get(1).getBoardSide().toStringReverse() + players.get(0).getBoardSide().getStore().toString());
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println(players.get(1).getBoardSide().getStore().toString() + players.get(0).getBoardSide().toString() + " P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        return;
    }

    public static void printGameEnd(IO io, ArrayList<Player> players, TurnState turnState) {
        io.println("Game over");
        printBoard(io, players);

        //print the winners and score only if the game ended naturally
        if (turnState.getGameOver() == 1) {
            for (int i = 0; i < players.size(); i++) {
                BoardSide b = players.get(i).getBoardSide();
                b.sumHousesAndEnd();
                io.println("\tplayer " + (i + 1) + ":" + b.getStore().getSeeds());
            }
            int winner = getWinner(players);
            if(winner == -1){
                io.println("A tie!");
            } else {
                io.println("Player " + winner + " wins!");
            }
        }
        return;
    }

    //returns -1 if there was a tie
    private static int getWinner(ArrayList<Player> players){
        int highest = 0;
        int player = 0;
        int count = 0;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getBoardSide().getStore().getSeeds() > highest){
                highest = players.get(i).getBoardSide().getStore().getSeeds();
                player = i;
                count = 1;
            }else if(players.get(i).getBoardSide().getStore().getSeeds() == highest){
                count++;
            }
        }
        return count > 1 ? -1 : player+1;
    }


}
