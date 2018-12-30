package Resources;

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
