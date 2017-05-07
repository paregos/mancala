package kalah;

import java.util.ArrayList;

/**
 * Created by Ben on 5/6/2017.
 */
public class BoardSide {

    private ArrayList<House> houses;
    private Store store;

    public BoardSide(){
        super();
    }

    public BoardSide(ArrayList<House> houses){
        super();
        this.houses = houses;
        this.store = new Store();
    }

    public BoardSide(ArrayList<House> houses, Store store){
        super();
        this.houses = houses;
        this.store = store;
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

    //checks if all houses are empty or not.
    public boolean hasEmptyHouses(){
        for(House h : this.houses){
            if (h.getSeeds() > 0){
                return false;
            }
        }
        return true;
    }

    //go through all houses and place any seeds in this boards store.
    public void sumHousesAndEnd(){
        for(House h : this.houses){
            this.getStore().incrementSeeds(h.getSeeds());
        }
        return;
    }

    public String toString(){
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i<this.houses.size(); i++){
            temp.append(" "+(i+1)+this.houses.get(i).toString()+" |");
        }
        return temp.toString();
    }

    public String toStringReverse(){
        StringBuilder temp = new StringBuilder();
        for(int i = this.houses.size()-1; i>=0; i--){
            temp.append("| "+(i+1)+this.houses.get(i).toString()+" ");
        }
        return temp.toString();
    }
}
