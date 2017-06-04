package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.board.BoardSide;
import kalah.board.Container;
import kalah.board.House;
import kalah.player.HumanPlayer;
import kalah.player.Player;
import kalah.rules.*;

import java.util.ArrayList;

/**
 * Created by Ben on 5/6/2017.
 */
public class Kalah {

    public static final int NUMBER_OF_HOUSES = 6;
    public static final int NUMBER_OF_PLAYERS = 2;

    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public Kalah() {
        super();
    }

    /**
     * Initialises the game to a new state, this is used to setup all needed variables at the start of a game. TurnState
     * is intialised and this.rules is populated with instances of the rules that will be included in the game. All
     * of these variables are then passed into a new instance of KalahGame in a way that resembles the dependency
     * injection principle, as many of the variables injected are interface types.
     */
    public void play(IO io) {

        //Creating the players for this game
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            ArrayList<Container> houses = new ArrayList<Container>();
            for (int j = 0; j < NUMBER_OF_HOUSES; j++) {
                houses.add(new House());
            }
            players.add(new HumanPlayer(i, new BoardSide(houses, i)));
        }

        //Creating the set of rules for this game
        ArrayList<Rule> rules = new ArrayList<Rule>();
        rules.add(new EndInOwnStore());
        rules.add(new StealFromOppositeHouse());
        rules.add(new NoPlayableTurn());

        //Creating the turnstate for the game
        TurnState turnState = new TurnState(players.get(0), players.get(0).getBoardSide(), 0);

        //Intialising and starting the game
        Game game = new KalahGame(rules, players, turnState);
        game.play(io);
    }


}
