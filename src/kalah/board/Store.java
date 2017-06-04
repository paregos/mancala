package kalah.board;

/**
 * Created by Ben on 5/6/2017.
 */
public class Store implements Container{

    private int seeds;

    public Store(){
        this.seeds = 0;
    }

    public Store(int seeds){
        this.seeds = seeds;
    }

    @Override
    public int getSeeds() {
        return seeds;
    }

    @Override
    public int takeSeeds() {
        return 0;
    }

    @Override
    public void incrementSeeds(int value) {
        this.seeds += value;
        return;
    }

    @Override
    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    @Override
    public String toString(){
        if(this.seeds < 10) {
            return "|  " + this.seeds + " |";
        }else{
            return "| " + this.seeds + " |";
        }

    }

}
