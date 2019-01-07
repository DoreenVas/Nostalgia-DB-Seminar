package Resources;

/**
 * Holds a songs popularity value.
 * Used to distinguish between different data.
 */
public class PopularityContainer implements Container<Float> {
    // members
    private float popularity;

    /***
     * Constructor
     * @param popularity the popularity rate of the artist
     */
    public PopularityContainer(float popularity) {
        this.popularity = popularity;
    }

    @Override
    public Float getValue() {
        return this.popularity;
    }
}
