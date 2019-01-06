package Resources;

/**
 * Holds a list of genres.
 * Used to distinguish between different data.
 */
public class GenreContainer implements Container<String[]> {

    private String[] genre;

    public GenreContainer(String[] genre) {
        this.genre = genre;
    }

    @Override
    public String[] getValue() {
        return this.genre;
    }
}
