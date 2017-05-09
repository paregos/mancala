package kalah;

/**
 * Created by Ben on 5/6/2017.
 */
public class Store {

    private int seeds;

    public Store(){
        this.seeds = 0;
    }

    public Store(int seeds){
        this.seeds = seeds;
    }

    public int getSeeds() {
        return seeds;
    }

    public void incrementSeeds(int value) {
        this.seeds += value;
        return;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public String toString(){
        if(this.seeds < 10) {
            return "|  " + this.seeds + " |";
        }else{
            return "| " + this.seeds + " |";
        }

    }

}
