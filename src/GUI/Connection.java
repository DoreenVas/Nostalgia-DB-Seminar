package GUI;

public class Connection {

    private static Connection connection = null;
    private Controller controller;

    private Connection() {
        controller = new Controller();
    }

    public Connection connection() {
        if(connection == null) {
            connection = new Connection();
        }
        return connection;
    }
}
