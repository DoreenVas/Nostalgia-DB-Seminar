package DBConnection;

import Resources.*;

public class Test {

    public static void main(String[] args) {
        boolean connected = false;
        DBModel model = null;
        try {
            model = new DBModel();
        } catch (Exception e) {
            // cannot read config file - need to give warning
            e.printStackTrace();
            return;
        }

        while (!connected) {
            try {
                model.openConnection();
                connected = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            DataContainer myRes;
            String[] genre = {"rock", "disco", "rap"};

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
//            String genre2[] = {"pop"};
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
//            ArtistContainer ArtistContainer = new ArtistContainer("Britney Spears");
//            myRes = model.getSongs(ArtistContainer);
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
//
//            System.out.println("Getting songs by hotness:");
//            myRes = model.getSongs(new PopularityContainer(0.004));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting artist by name:");
//            myRes = model.getArtists(new ArtistContainer("aerosmith"));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting song by artist name and genre:");
//            String[] genre = {"rock", "disco", "rap"};
//            myRes = model.getSongs(new GenreContainer(genre), new ArtistContainer("Ross"));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting song by genre name and duration:");
//            myRes = model.getSongs(new GenreContainer(genre), new DurationContainer(120));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting song by tempo and duration:");
//            myRes = model.getSongs(new TempoContainer(60), new DurationContainer(120));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();
//
//            System.out.println("Getting song by tempo, duration and artist:");
//            myRes = model.getSongs(new TempoContainer(150), new DurationContainer(120),new ArtistContainer("Gorillaz"));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();

//            System.out.println("Getting song by tempo of 30:");
//            myRes = model.getSongs(new TempoContainer(30));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();

//            System.out.println("Getting artist by song name:");
//            myRes = model.getArtists(new SongContainer("The Court"));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();

//            System.out.println("Getting songs:");
//            myRes = model.getSongs(new GenreContainer(genre), new PopularityContainer(0.41952), new TempoContainer(30),
//                    new DurationContainer(60), new ArtistContainer("Zbigniew Preisner"));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();

//            System.out.println("Getting song by song name and artist:");
//            myRes = model.getSongs(new SongContainer("The Court"), new ArtistContainer("Zbigniew Preisner"));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();

//            System.out.println("Getting song by year, genre and duration:");
//            myRes = model.getSongs(1979, new GenreContainer(genre), new DurationContainer(300));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();

//            System.out.println("Getting song by genre, artist and album:");
//            myRes = model.getSongs(new GenreContainer(genre), new ArtistContainer("Ross"),
//                    new AlbumContainer("I Promise My Heart"));
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();

//            System.out.println("Getting song by artist and album:");
//            myRes = model.getSongs(new ArtistContainer("Ross"), new AlbumContainer("I Promise My Heart"),
//                    "");
//            for (String line : myRes.getData()) {
//                System.out.println(line);
//            }
//            System.out.println(myRes.getCount());
//            System.out.println();

//            System.out.println("Getting song by album:");
//            myRes = model.getSongs(new AlbumContainer("I Promise My Heart"), "");
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