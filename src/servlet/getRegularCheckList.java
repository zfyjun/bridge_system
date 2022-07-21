package servlet;

import bridge.BridgeScoreDao;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet( "/getRegularCheckList")
public class getRegularCheckList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bridgeName=request.getParameter("bridge_name");
        String bridgeNo=BridgeScoreDao.getBridgeNo(bridgeName);//通过桥名找到桥id
        String typeNo=BridgeScoreDao.getTypeNo();//通过桥id找到桥梁类型
        List<String>typeNos=new ArrayList<>();
        typeNos.add(typeNo);
        System.out.println(bridgeNo);
        System.out.println(typeNo);
        JSONArray o=new JSONArray();
        o = JSONArray.parseArray(JSONObject.toJSONString( typeNos));
        System.out.println("jsonArray2：" + o);
        PrintWriter pw = response.getWriter();
        pw.print(o);
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
