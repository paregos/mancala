package kalah;

/**
 * Created by Ben on 5/6/2017.
 */
public class House {

    private int seeds;

    public House(){
        super();
        this.seeds = 4;
    }

    public House(int seeds){
        super();
        this.seeds = seeds;
    }

    public int getSeeds() {
        return seeds;
    }

    public int takeSeeds() {
        int temp = seeds;
        this.seeds = 0;
        return temp;

    }

    public void incrementSeeds() {
        this.seeds++;
        return;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public String toString(){
        return "[ "+this.seeds+"]";
    }

}
