package DBConnection;

public class Test {

    public static void main(String[] args) {
        boolean connected = false;
        Model model = null;
        try {
            model = new Model();
        } catch (Exception e) {
            // cannot read config file - need to give warning
            e.printStackTrace();
            return;
        }

        while(!connected) {
            try {
                model.openConnection();
                connected = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Getting all songs:");
            String[] myRes = model.getTable("song");
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
            System.out.println();

            System.out.println("Getting songs 4 minutes long:");
            DurationContainer durationContainer = new DurationContainer((float) 3.91);
            myRes = model.getSongs(durationContainer);
            for (String line : myRes) {
                System.out.println(line);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            model.closeConnection();
        } catch (Exception e) {
            // error in closing resources
            e.printStackTrace();
        }
    }
}
