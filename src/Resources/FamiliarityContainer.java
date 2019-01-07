package Resources;

/**
 * Holds an artist familiarity value.
 * Used to distinguish between different data.
 */
public class FamiliarityContainer implements Container<Float>{
    // members
    private float familiarity;

    /***
     * Constructor
     * @param familiarity the familiarity rate
     */
    public FamiliarityContainer(float familiarity){
        this.familiarity = familiarity;
    }

    @Override
    public Float getValue() {
        return this.familiarity;
    }
}


