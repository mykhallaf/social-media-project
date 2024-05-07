package socialmediaproject.gui;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "social_media_app";
        String databaseUser = "root";
        String databasePassword = "sqlmohakhallaf101101@#";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink =DriverManager.getConnection(url,databaseUser,databasePassword);

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }


}
