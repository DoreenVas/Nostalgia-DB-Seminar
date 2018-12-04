package DBConnection;

public class Test {

    public static void main(String[] args) {
        Model model = new Model();

        System.out.println("Getting all songs:");
        String[] myRes = model.getSongs();
        for (String line : myRes) {
            System.out.println(line);
        }
        System.out.println();

        System.out.println("Getting songs from 1979:");
        myRes = model.getSongs(1979);
        for (String line : myRes) {
            System.out.println(line);
        }
        System.out.println();

        System.out.println("Getting songs between 1978 and 1995:");
        myRes = model.getSongs(1978, 1995);
        for (String line : myRes) {
            System.out.println(line);
        }

        model.closeConnection();
    }
}
