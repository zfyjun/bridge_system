package bridge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    protected static Connection getConn() {

        String driverName="org.gjt.mm.mysql.Driver";

        String url="jdbc:mysql://localhost:3306/bridge";

        String user="root";

        String password="123456789";

        try {

            Class.forName(driverName);

            Connection conn= DriverManager.getConnection(url, user, password);

            //System.out.println("数据库连接成功！");

            return conn;

        } catch (ClassNotFoundException e) {

            // TODO Auto-generated catch block

            System.out.println("驱动程序有误！");

            return null;

        } catch (SQLException e) {

            // TODO Auto-generated catch block

            System.out.println("数据库连接失败！");
            e.printStackTrace();
            return null;

        }

    }
}
