package Resources;

/**
 * Holds an artist familiarity value.
 * Used to distinguish between different data.
 */
public class FamiliarityContainer implements Container<Float>{
    private float familiarity;

    public FamiliarityContainer(float familiarity){
        this.familiarity = familiarity;
    }
    @Override
    public Float getValue() {
        return this.familiarity;
    }
}


