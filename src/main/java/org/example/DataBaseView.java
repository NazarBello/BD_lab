package org.example;

import org.example.util.HibernateUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseView {



    public void Process() throws SQLException {
        // Use the new no-arg constructor in your DataBaseController
        var databaseController = new DataBaseController(HibernateUtil.getSessionFactory());

        while (true) {
            int action = databaseController.showActions();
            databaseController.Execute(action);
        }
    }


}
