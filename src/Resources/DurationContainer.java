package Resources;

/**
 * Holds an songs duration.
 * Used to distinguish between different data.
 */
public class DurationContainer implements Container<Float> {

    private float duration;

    public DurationContainer(float duration) {
        this.duration = duration;
    }

    @Override
    public Float getValue() {
        return this.duration;
    }
}
