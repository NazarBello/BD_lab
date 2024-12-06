package org.example;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseView {

    static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=public&user=postgres&password=postgres&password=1111";

    public void Process() throws SQLException {
        var databaseController = new DataBaseController(JDBC_URL);
        while( true){
            int action = databaseController.showActions();
            databaseController.Execute(action);
        }


    }


}
