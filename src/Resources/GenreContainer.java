package Resources;

/**
 * Holds a list of genres.
 * Used to distinguish between different data.
 */
public class GenreContainer implements Container<String[]> {
    // members
    private String[] genre;

    /***
     * Constructor
     * @param genre the genres of the artist
     */
    public GenreContainer(String[] genre) {
        this.genre = genre;
    }

    @Override
    public String[] getValue() {
        return this.genre;
    }
}
