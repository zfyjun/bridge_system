package bridge;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class UserDmo {

    private static Connection conn= JDBC.getConn();
    private static Statement statement;

    static
    {
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean login(User user) throws SQLException {
//        Connection conn= JDBCUtils.getConnection();
//        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("select * from monitoring_user");
        while(result.next()){
            String uname = result.getString("m_id");
            System.out.println(uname);
            String upass = result.getString("m_password");
            System.out.println(upass);
            System.out.println(user.getUsername()+":"+user.getPassword());
            if(Objects.equals(user.getUsername(), uname) && Objects.equals(user.getPassword(), upass))
                return true;
        }
        return false;
    }

    public static void register(User user) throws SQLException {

    }
}
