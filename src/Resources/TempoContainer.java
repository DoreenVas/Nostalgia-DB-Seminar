package Resources;

import DBConnection.Container;

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
