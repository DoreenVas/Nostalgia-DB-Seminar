package DBConnection;

import Resources.Container;

public class PopularityContainer implements Container<Double> {

    private double popularity;

    public PopularityContainer(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public Double getValue() {
        return this.popularity;
    }
}