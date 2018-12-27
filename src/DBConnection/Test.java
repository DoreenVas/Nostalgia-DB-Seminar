package DBConnection;

import Resources.DataContainer;

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
            DataContainer myRes;

//            System.out.println("Getting all songs:");
//            myRes = model.getTable("song");
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting songs from 1979:");
//            myRes = model.getSongs(1979);
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting songs between 1978 and 1995:");
//            myRes = model.getSongs(1978, 1995);
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting songs 4 minutes long:");
//            DurationContainer durationContainer = new DurationContainer((float) 3.91);
//            myRes = model.getSongs(durationContainer);
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting all genres:");
//            myRes = model.getTable("genre");
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting all songs with rock, metal, jazz, punk genre:");
//            String genre[] = {"rock", "metal", "jazz", "punk"};
//            GenreContainer genreContainer = new GenreContainer(genre);
//            myRes = model.getSongs(genreContainer);
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting song - Shattered Life:");
//            myRes = model.getSongs("Shattered Life");
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting songs of an artist");
//            ArtisitContainer artisitContainer = new ArtisitContainer("Britney Spears");
//            myRes = model.getSongs(artisitContainer);
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting songs lyrics:");
//            myRes = model.getLyrics("Everytime");
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
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
