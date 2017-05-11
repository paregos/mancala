package kalah;

import java.util.ArrayList;

/**
 * Created by Ben on 5/6/2017.
 */
public class BoardSide {

    private ArrayList<House> houses;
    private Store store;
    private int id;

    public BoardSide(){
        super();
        this.houses = new ArrayList<House>();
        this.store = new Store();
        this.id = 0;
    }

    public BoardSide(ArrayList<House> houses, int number){
        super();
        this.houses = houses;
        this.store = new Store();
        this.id = number;
    }

    public BoardSide(ArrayList<House> houses, Store store, int number){
        super();
        this.houses = houses;
        this.store = store;
        this.id = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<House> getHouses() {
        return houses;
    }

    public House getHouse(int index) {
        return this.houses.get(index);
    }

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * Cycles through all of the houses within this boardside.
     * @return  True if there exists only houses with 0 seeds, False otherwise.
     */
    public boolean hasEmptyHouses(){
        for(House h : this.houses){
            if (h.getSeeds() > 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Called when the game has ended naturally, any seeds remaining on a board side are taken and placed in that
     * boardsides store.
     */
    public void sumHousesAndEnd(){
        for(House h : this.houses){
            this.getStore().incrementSeeds(h.getSeeds());
        }
        return;
    }

    /**
     * @return  A string representation of this boardside.
     */
    public String toString(){
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i<this.houses.size(); i++){
            temp.append(" "+(i+1)+this.houses.get(i).toString()+" |");
        }
        return temp.toString();
    }

    /**
     *
     * @return A string representation of this boardside in reversed order.
     */
    public String toStringReverse(){
        StringBuilder temp = new StringBuilder();
        for(int i = this.houses.size()-1; i>=0; i--){
            temp.append("| "+(i+1)+this.houses.get(i).toString()+" ");
        }
        return temp.toString();
    }
}
