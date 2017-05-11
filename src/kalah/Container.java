package kalah;

/**
 * Created by Ben on 5/11/2017.
 */
public interface Container {

    /**
     * @return The number of seeds within this container.
     */
    int getSeeds();

    /**
     * Takes all of the seeds in this container and returns them, it then sets the number of seeds in this container
     * to 0.
     * @return The number of seeds present in this container before setting it to 0.
     */
    int takeSeeds();

    /**
     * Increment the seeds in this container by value.
     * @param value
     */
    void incrementSeeds(int value);

    /**
     * Sets the seeds in this container to be this value.
     * @param seeds
     */
    void setSeeds(int seeds);

    /**
     * @return  A string representation of this container.
     */
    String toString();

}
