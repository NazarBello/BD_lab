package org.example;

import java.sql.*;
import java.util.*;


public class DataBaseModel {
     public Connection connection;

    public DataBaseModel(String jdbcUrl) throws SQLException {

        connection = DriverManager.getConnection(jdbcUrl);
        System.out.println("Connection established");
    }

    public void Insert() throws SQLException {
        var statement = connection.createStatement();
        List<String> tables = getTables("insert");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        List<String> columns = getColumns(tables.get(index));
        List<String> values = new ArrayList<>();
        List<String> columnTypes = getColumnTypes(tables.get(index));

        for (int flag = 0; flag < columns.size(); flag++) {
            String column = columns.get(flag);
            String columnType = columnTypes.get(flag);
            System.out.println("Enter value for -> " + column + " (" + columnType + "):");

            if (column.equals("users_id") && !tables.get(index).equals("users")) {
                int userId;
                while (true) {
                    try {
                        userId = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        System.out.println("Checking if user exists...");
                        if (!UserExists(userId)) {
                            System.out.println("User not found. Would you like to add the user? (yes/no)");
                            String response = scanner.nextLine();
                            if (response.equalsIgnoreCase("yes")) {
                                createPlaceHolderUser(userId);
                            } else {
                                System.out.println("Cannot insert without a valid user_id.");
                                return;
                            }
                        }
                        values.add(String.valueOf(userId));
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer for user_id.");
                        scanner.nextLine(); // Consume the invalid input
                    }
                }
            }
            else if (column.equals("trip_period") || column.equals("booking_period")) {
                System.out.println("Enter trip period in format [YYYY-MM-DD,YYYY-MM-DD]:");
                String tripPeriod = scanner.nextLine();
                if (!tripPeriod.matches("\\[[0-9]{4}-[0-9]{2}-[0-9]{2},[0-9]{4}-[0-9]{2}-[0-9]{2}\\]")) {
                    System.out.println("Invalid format. Please use [YYYY-MM-DD,YYYY-MM-DD].");
                    return;
                }
                values.add("'" + tripPeriod + "'");
            }else {
                String value = scanner.nextLine();
                if (columnType.equals("character varying")) {
                    value = "'" + value.replace("'", "''") + "'";
                }
                values.add(value);
            }
        }

        String insertQuery = "INSERT INTO " + tables.get(index) + " (" + String.join(", ", columns) + ") VALUES (" + String.join(", ", values) + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            System.out.println("Inserted successfully");
        } catch (SQLException e) {
            System.out.println("Error executing insert: " + e.getMessage());
            throw e;
        }
    }
    public void Delete() throws SQLException {
        List<String> tables = getTables("delete");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        List<String> columns = getColumns(tables.get(index));
        System.out.println("Input row id to delete data:");
        int rowId = scanner.nextInt();
        String query = "DELETE FROM " + tables.get(index) + " WHERE " + tables.get(index) + "_id = " + rowId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            System.out.println("Deleted successfully");
        } catch (SQLException e) {
            System.out.println("Error executing deletion: " + e.getMessage());
            throw e;
        }
    }

    public void Update() throws SQLException {
        List<String> tables = getTables("update");
        Scanner scanner = new Scanner(System.in);
        
        int tableIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<String> columns = getColumns(tables.get(tableIndex));
        System.out.println("Input row ID to update data:");
        int rowId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        StringBuilder setClause = new StringBuilder();
        List<String> values = new ArrayList<>();

        // Gather values for each column
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            System.out.println("Enter value for -> " + column);
            String value = scanner.nextLine();

            // Handle special types
            String columnType = getColumnTypes(tables.get(tableIndex)).get(i);
            if (columnType.equals("character varying")) {
                value = "'" + value.replace("'", "''") + "'";
            } else if (columnType.equals("daterange")) {
                if (!value.matches("\\[[0-9]{4}-[0-9]{2}-[0-9]{2},[0-9]{4}-[0-9]{2}-[0-9]{2}\\]")) {
                    System.out.println("Invalid daterange format. Use [YYYY-MM-DD,YYYY-MM-DD].");
                    return;
                }
                value = "'" + value + "'";
            }

            setClause.append(column).append(" = ").append(value);
            if (i < columns.size() - 1) {
                setClause.append(", ");
            }
        }

        // Construct the final query
        String updateQuery = "UPDATE " + tables.get(tableIndex) +
                " SET " + setClause +
                " WHERE " + tables.get(tableIndex) + "_id = " + rowId;

        // Debugging: Print the query
        System.out.println("Generated Query: " + updateQuery);

        // Execute the query
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Row updated successfully.");
            } else {
                System.out.println("No rows updated. Check the row ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
            throw e;
        }

    }

    public void Generate () throws SQLException {
        List<String> tables = getTables("generate");
        Scanner scanner = new Scanner(System.in);

        int tableIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String selectedTable = tables.get(tableIndex);
        List<String> columns = getColumns(selectedTable);
        List<String> columnTypes = getColumnTypes(selectedTable);

        StringBuilder columnsPart = new StringBuilder();
        StringBuilder valuesPart = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String columnType = columnTypes.get(i);
            columnsPart.append(column);
            // Skip primary key generation
            if (column.equals(selectedTable + "_id")) {
                valuesPart.append(random.nextInt(10));
            } else if  (columnType.equals("integer")) {
                valuesPart.append(random.nextInt(1000)); // Random integer between 0 and 999
            } else if (columnType.equals("character varying")) {
                valuesPart.append("'").append(generateRandomString(random, 10)).append("'"); // Random string
            } else if (columnType.equals("daterange")) {
                String startDate = generateRandomDate(random, 2020, 2025);
                String endDate = generateRandomDate(random, 2026, 2030);
                valuesPart.append("'[").append(startDate).append(",").append(endDate).append("]'"); // Random daterange
            } else if (columnType.equals("boolean")) {
                valuesPart.append(random.nextBoolean()); // Generates `TRUE` or `FALSE`
            }else {
                valuesPart.append("NULL"); // Default for unsupported types
            }

            if (i < columns.size() - 1) {
                columnsPart.append(", ");
                valuesPart.append(", ");
            }
        }

        String insertQuery = "INSERT INTO " + selectedTable +
                " (" + columnsPart + ") VALUES (" + valuesPart + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            System.out.println("Generated Query: " + insertQuery);
            preparedStatement.executeUpdate();
            System.out.println("Random values inserted successfully into " + selectedTable);
        } catch (SQLException e) {
            System.out.println("Error executing random value insertion: " + e.getMessage());
            throw e;
        }
    }

    public void Search() throws SQLException {
            Scanner scanner = new Scanner(System.in);

            // Step 1: Display available tables
            List<String> tables = getTables("search");
            int tableIndex = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            String selectedTable = tables.get(tableIndex);

            // Step 2: Get columns for the selected table
            List<String> columns = getColumns(selectedTable);
            System.out.println("Available columns in " + selectedTable + ":");
            for (int i = 0; i < columns.size(); i++) {
                System.out.println(i + ": " + columns.get(i));
            }
            System.out.println("Choose a column to search:");
            int columnIndex = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            String selectedColumn = columns.get(columnIndex);

            // Step 3: Get the search value
            System.out.println("Enter the value to search for in column " + selectedColumn + ":");
            String searchValue = scanner.nextLine();
            String columnType = getColumnTypes(selectedTable).get(columnIndex);

            // Step 4: Build the SQL query
            String query = "SELECT * FROM " + selectedTable + " WHERE " + selectedColumn + " = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                if (columnType.equals("integer")) {
                    preparedStatement.setInt(1, Integer.parseInt(searchValue));
                } else if (columnType.equals("boolean")) {
                    preparedStatement.setBoolean(1, Boolean.parseBoolean(searchValue));
                } else {
                    preparedStatement.setString(1,  searchValue );
                }
                System.out.println("Executing Query: " + query);

                // Step 5: Execute the query and process the results
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Display results
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    System.out.println("Search Results:");
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(metaData.getColumnName(i) + ": " + resultSet.getString(i) + " | ");
                        }
                        System.out.println();
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error executing search: " + e.getMessage());
                throw e;
            }
    }

    public void Print () throws SQLException {
        List<String> tables = getTables("print");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        scanner.nextLine();
        String selectedTable = tables.get(index);

        String query = "SELECT * FROM " + selectedTable ;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Printing results from: " + selectedTable);
            for(int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");

            }
            System.out.println("\n" + "-".repeat(50));
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data from table " + selectedTable + ": " + e.getMessage());
            throw e;
        }
    }

    public List<String> getTables(String operation) throws SQLException {
        List<String> tables = new ArrayList<>();
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';";
        System.out.println("Choose table to " + operation + " data");
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            tables.add(rs.getString("table_name"));
        }
        statement.close();
        tables.forEach(System.out::println);
        return tables;

    }

    public List<String> getColumns(String table) throws SQLException {
        List<String> columns = new ArrayList<>();
        String query = "SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '" + table + "'";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            columns.add(rs.getString("column_name"));
        }
        statement.close();

        return columns;


    }

    public List<String> getColumnTypes(String table) throws SQLException {
        List<String> columnTypes = new ArrayList<>();
        String query = "select data_type "+
                        "from information_schema.columns "+
                        "where table_name = ?"+
                        " order by ordinal_position";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, table);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    columnTypes.add(resultSet.getString("data_type"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching column types: " + e.getMessage());
        }

        return columnTypes;
    }
    public void createPlaceHolderUser( int user_id) throws SQLException {
        String query = "INSERT INTO users(user_id, name)VALUES (?, 'PlaceHolder');";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, user_id);
            statement.executeUpdate();
            System.out.println("User created");

        }
    }
    public boolean UserExists(int user_id) throws SQLException {
        String query = "SELECT 1 FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    private String generateRandomString(Random random, int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private String generateRandomDate(Random random, int startYear, int endYear) {
        int year = random.nextInt(endYear - startYear + 1) + startYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1; // Simplify to 28 days for all months
        return String.format("%04d-%02d-%02d", year, month, day);
    }


}
