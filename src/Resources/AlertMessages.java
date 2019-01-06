package Resources;

public class AlertMessages {

    public static String failedConnection() {
        return "Couldn't open connection to db.\nTry again?";
    }

    public static String failedConnection(String reason) {
        return "Couldn't open connection to db.\nReason: " + reason + "\nTry again?";
    }

    public static String failedConnection(String reason, boolean tryAgain) {
        String message = "Couldn't open connection to db.\nReason: " + reason;
        if(tryAgain) {
            message += "\nTry again?";
        }
        return message;
    }

    public static String emptyResult() {
        return "The search yielded no results.\nPlease enter different search parameters.";
    }

    public static String queryExecutionFailure() {
        return "Failed to execute a query.";
    }

    public static String failedDisconnection() {
        return "Couldn't close the connection to db.\nTry again?";
    }

    public static String pageLoadingFailure() {
        return "Couldn't load the requested page.";
    }
}
