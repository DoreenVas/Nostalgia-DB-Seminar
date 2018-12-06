package DBConnection;

public class PopularityContainer implements Container<Float> {

    private float genre;

    public PopularityContainer(float genre) {
        this.genre = genre;
    }

    @Override
    public Float getValue() {
        return this.genre;
    }
}
