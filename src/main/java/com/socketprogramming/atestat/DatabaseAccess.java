package com.socketprogramming.atestat;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseAccess {

    private Statement statement;
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static int update;

    // DATABASE CONNECTIONS

    public static void startConnection() throws SQLException {

        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/atestatdb",
                "root",
                "NoPasswordReally"
        );
    }
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    // LOGIN AND REGISTER

    /*public static User loginUser(){

    }*/

    public static void addReview(int userID, int companyID, String reviewText, float rating, boolean isAnonymous) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "INSERT INTO review (UserID, CompanyID, Review_Text, Rating, Is_Anonymous)" +
                        "VALUES (?,?,?,?,?)"
        );
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, companyID);
        preparedStatement.setString(3, reviewText);
        preparedStatement.setFloat(4, rating);
        preparedStatement.setBoolean(5, isAnonymous);
        update = preparedStatement.executeUpdate();
    }
    public static void removeReview(int reviewID) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM review WHERE ReviewID = ?"
        );
        preparedStatement.setInt(1, reviewID);
        update = preparedStatement.executeUpdate();
    }


    public static ArrayList<Review> getCompanyReviews(int companyID) throws SQLException {
        ArrayList<Review> reviews = new ArrayList<>();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM view_company_reviews WHERE CompanyID = ?"
        );
        preparedStatement.setInt(1, companyID);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Review review = new Review(
                    resultSet.getInt("ReviewID"),
                    resultSet.getInt("UserID"),
                    resultSet.getString("User_Name"),
                    companyID,
                    resultSet.getString("Company_Name"),
                    resultSet.getString("Review_Text"),
                    resultSet.getFloat("Rating"),
                    resultSet.getBoolean("Is_Anonymous"),
                    resultSet.getBytes("User_Photo"),
                    resultSet.getTimestamp("Review_DateTime").toLocalDateTime()
            );
            reviews.add(review);
        }
        return reviews;
    }


    public static ArrayList<Company> getCompaniesBasedOnService(int serviceID) throws SQLException {
        preparedStatement = connection.prepareStatement(
                " SELECT * FROM view_companies \n" +
                        "\n" +
                        "WHERE CompanyID IN (\n" +
                        "\n" +
                        "    SELECT cs.CompanyID \n" +
                        "\n" +
                        "    FROM companyservices cs\n" +
                        "\n" +
                        "    JOIN services s ON cs.ServiceID = s.ServiceID\n" +
                        "\n" +
                        "    WHERE s.ServiceID = ?\n" +
                        "\n" +
                        ")"
        );
        preparedStatement.setInt(1, serviceID);
        resultSet = preparedStatement.executeQuery();
        ArrayList<Company> companies = new ArrayList<>();
        while(resultSet.next()){
            Company company = new Company(
                    resultSet.getString("Company_Name"),
                    resultSet.getString("Services_Offered"),
                    resultSet.getString("Business_Email"),
                    resultSet.getString("Customer_Service_Email"),
                    resultSet.getString("Business_Phone_Number"),
                    resultSet.getString("Customer_Service_Phone_Number"),
                    resultSet.getString("Address"),
                    resultSet.getString("Website_Link"),
                    resultSet.getDate("Company_Founded_Date").toLocalDate()
            );
            company.setCompanyID(resultSet.getInt("CompanyID"));
            companies.add(company);
        }
        return companies;
    }

    public static ArrayList<Service> getServices() throws SQLException {
        ArrayList<Service> services = new ArrayList<>();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM view_all_services"
        );
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            services.add(new Service(resultSet.getInt("ServiceID"), resultSet.getString("Service")));
        }
        return services;
    }

    public static ArrayList<Review> getUserReviews(int userID) throws SQLException {
        ArrayList<Review> reviews = new ArrayList<>();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM view_user_review WHERE UserID = ?"
        );
        preparedStatement.setInt(1, userID);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Review review = new Review(
                    resultSet.getInt("ReviewID"),
                    userID,
                    resultSet.getString("User_Name"),
                    resultSet.getInt("CompanyID"),
                    resultSet.getString("Company_Name"),
                    resultSet.getString("Review_Text"),
                    resultSet.getFloat("Rating"),
                    resultSet.getBoolean("Is_Anonymous"),
                    resultSet.getBytes("User_Photo"),
                    resultSet.getTimestamp("Review_DateTime").toLocalDateTime()
            );
            reviews.add(review);
        }
        return reviews;
    }

    public static User getUser(int userID) throws SQLException {
        User user = null;
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM user WHERE UserID = ?"
        );
        preparedStatement.setInt(1, userID);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            user = new User(
                    userID,
                    resultSet.getString("Name"),
                    resultSet.getString("Password"),
                    resultSet.getString("Email"),
                    resultSet.getDate("Join_Date").toLocalDate(),
                    resultSet.getBytes("Photo_Byte")
            );
        }
        return user;
    }

    public static float getCompanyTotalRating(int companyID) throws SQLException {
        float totalRating = 0;
        int reviewNumber = 0;
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM view_company_ratings WHERE CompanyID = ?"
        );
        preparedStatement.setInt(1, companyID);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            totalRating+=resultSet.getFloat("Rating");
            reviewNumber++;
        }
        return totalRating/reviewNumber;
    }

    public static boolean checkIfEmailExists(String email) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM user WHERE Email = ?"
        );
        preparedStatement.setString(1, email);
        resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static User attemptLogin(String email, String password) throws SQLException {
        User user = null;
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM user WHERE Email = ? AND Password = ?"
        );
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            user = new User(resultSet.getInt("UserID"),
                    resultSet.getString("Name"),
                    email,
                    password,
                    resultSet.getDate("Join_Date").toLocalDate(),
                    resultSet.getBytes("Photo_Byte")
            );
        }
        return user;
    }

    public static User registerUser(String email, String password, String username) throws SQLException {
        User user = null;
        preparedStatement = connection.prepareStatement(
                "INSERT INTO user (Name, Email, Password) VALUES (?, ?, ?)"
        );
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, username);
        update = preparedStatement.executeUpdate();
        user = DatabaseAccess.attemptLogin(email, password);
        return user;
    }

    public static void updateUserPhoto(int userID, byte[] photoByte) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "UPDATE user SET Photo_Byte = ? WHERE userID = ?"
        );
        preparedStatement.setBytes(1, photoByte);
        preparedStatement.setInt(2, userID);
        update = preparedStatement.executeUpdate();

    }

    public static byte[] getUserPhoto(int userID) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "SELECT Photo_Byte FROM user WHERE UserID = ?"
        );
        preparedStatement.setInt(1, userID);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getBytes("Photo_Byte");
        }
        return null;
    }



}
