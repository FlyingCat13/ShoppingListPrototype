import controllers.ApplicationController;

import java.sql.SQLException;

public class ApplicationMain {
    public static void main(String[] args) throws SQLException {
        ApplicationController applicationController = new ApplicationController();
        applicationController.start();
    }
}
