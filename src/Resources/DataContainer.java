package Resources;

/**
 * A class holder for the data returned by the model.
 */
public class DataContainer {

    private int count;
    private String[] data;

    /**
     *
     * Constructor.
     * @param data the data to store
     * @param count the row count of the data
     */
    public DataContainer(String[] data, int count) {
        this.count = count;
        this.data = data;
    }

    /**
     *
     * @return the stored data.
     */
    public String[] getData() {
        return data;
    }

    /**
     *
     * @return the data row count.
     */
    public int getCount() {
        return count;
    }
}
