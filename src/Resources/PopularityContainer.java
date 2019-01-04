package Resources;

public class PopularityContainer implements Container<Float> {

    private float popularity;

    public PopularityContainer(float popularity) {
        this.popularity = popularity;
    }

    @Override
    public Float getValue() {
        return this.popularity;
    }
}
