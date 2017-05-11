package kalah;

/**
 * Created by Ben on 5/6/2017.
 */
public class House implements Container{

    private int seeds;

    public House(){
        super();
        this.seeds = 4;
    }

    public House(int seeds){
        super();
        this.seeds = seeds;
    }

    @Override
    public int getSeeds() {
        return seeds;
    }

    @Override
    public int takeSeeds() {
        int temp = seeds;
        this.seeds = 0;
        return temp;

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
        if(this.seeds<10) {
            return "[ " + this.seeds + "]";
        }else{
            return "["+this.seeds+"]";
        }
    }

}
