package DBConnection;

public class HotnessContainer implements Container<Float> {

    private float hotness;

    public HotnessContainer(float hotness) {
        this.hotness = hotness;
    }

    @Override
    public Float getValue() {
        return this.hotness;
    }
}
