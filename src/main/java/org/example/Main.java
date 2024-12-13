package org.example;


public class Main {


    static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=public&user=postgres&password=postgres&password=1111";

    public static void main(String[] args) throws Exception {
        var databaseController = new DataBaseController(JDBC_URL);
        while( true){
            int action = databaseController.showActions();
            databaseController.Execute(action);
        }

    }
}