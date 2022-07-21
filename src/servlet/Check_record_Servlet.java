package servlet;

import com.alibaba.fastjson.JSONObject;
import bridge.Frequent_check;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet( "/Check_Servlet")
public class Check_record_Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String bridge_id = request.getParameter("bridge_id");
        String check_data = request.getParameter("check_data");

        Frequent_check frequent_check=new Frequent_check();
        List<Frequent_check> f2;
        try {
            f2=frequent_check.show_table(Integer.parseInt(bridge_id),check_data);
            System.out.println(f2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObjec=new JSONObject();
        jsonObjec.put("f2",f2);
        PrintWriter pw=response.getWriter();
        pw.print(jsonObjec);
        pw.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request,response);
    }
}
