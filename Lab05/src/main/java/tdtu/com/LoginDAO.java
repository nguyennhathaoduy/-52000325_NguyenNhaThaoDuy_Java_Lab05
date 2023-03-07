package tdtu.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO 
{
	public boolean validate(UserLogin us) throws ClassNotFoundException 
	{
        boolean status = false;
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/mysql_database?useSSL=false", "root", "");
            PreparedStatement preparedStatement = connection
            .prepareStatement("select * from login where username = ? and password = ? ")) {
            preparedStatement.setString(1, us.getUsername());
            preparedStatement.setString(2, us.getPassword());

            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();

        } 
        catch (SQLException e) 
        {
            printSQLException(e);
        }
        return status;
    }

    private void printSQLException(SQLException ex) 
    {
        for (Throwable e: ex) 
        {
            if (e instanceof SQLException) 
            {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) 
                {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
