package bridge;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class conn_bridge_info {

    private static final Properties PROPERTIES=new Properties();
    static {
        InputStream is= conn_bridge_info.class.getResourceAsStream("/bridge_info.properties");
        try {
            PROPERTIES.load(is);
            Class.forName(PROPERTIES.getProperty("driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection=null;

        try {
            connection= DriverManager.getConnection(PROPERTIES.getProperty("url"),PROPERTIES.getProperty("user"),PROPERTIES.getProperty("password"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }

    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
