package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class DataBaseController {
    DataBaseModel dataBaseModel = null;

    public DataBaseController(String JDBC_URL) throws SQLException {
        dataBaseModel = new DataBaseModel(JDBC_URL);
    }

    public int showActions(){
        int choice;
        System.out.println("\nChoose what action you would like to perform");
        System.out.println("1. Insert data into the database");
        System.out.println("2. Remove data from the database");
        System.out.println("3. Edit data in the database");
        System.out.println("4. Generate random data into the database");
        System.out.println("5. Search data in the database");
        System.out.println("6. Print data from the database");
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();
        return choice;
    }

    public void Execute(int choice) throws SQLException {
        switch (choice) {
            case 1:
                dataBaseModel.insert();
                break;

            case 2:
                dataBaseModel.delete();
                break;

            case 3:
                dataBaseModel.update();
                break;

            case 4:
                dataBaseModel.generate();
                break;

            case 5:
                dataBaseModel.search();
                break;

            case 6:
                dataBaseModel.print();
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
}
