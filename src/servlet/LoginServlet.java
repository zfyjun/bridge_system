package servlet;

import bridge.User;
import bridge.UserDmo;
import com.alibaba.fastjson.JSONObject;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user =new User(username,password);
        try {
            boolean s= UserDmo.login(user);
            JSONObject jsonObject=new JSONObject();
            if(s){
                jsonObject.put("userExsit","true");
            }
            else{
                jsonObject.put("userExsit","false");
            }
            PrintWriter pw=response.getWriter();
            pw.print(jsonObject);
            pw.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
