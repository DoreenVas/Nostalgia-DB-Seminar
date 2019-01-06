package Resources;

/**
 * Holds a songs tempo value.
 * Used to distinguish between different data.
 */
public class TempoContainer implements Container<Float> {

    private float tempo;

    public TempoContainer(float tempo) {
        this.tempo = tempo;
    }

    @Override
    public Float getValue() {
        return this.tempo;
    }
}
