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
