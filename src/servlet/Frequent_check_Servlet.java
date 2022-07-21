package servlet;

import com.alibaba.fastjson.JSONObject;
import bridge.Frequent_check;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Frequent_check_Servlet")
public class Frequent_check_Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String []arry = request.getParameterValues("arry1[]");
        ArrayList al = new ArrayList();
        for(String string : arry)
        {
            al.add(string);
        }
        System.out.println(al);
        System.out.println(al.size());
        Frequent_check frequent_check=new Frequent_check();
//        int bridgeID = frequent_check.getBridge_id((String)al.get(0));
        List<Frequent_check> f2 = new ArrayList<>();
        for(int i=2;i<56;i=i+5)
            {
                Frequent_check frequentCheck2 = new Frequent_check();
                frequentCheck2.setBridgeID(Integer.parseInt((String) al.get(0)));
                frequentCheck2.setCheck_unit((String)al.get(1));
                frequentCheck2.setCheck_people((String)al.get(57));
                frequentCheck2.setCheckDate((String)al.get(58));
                if(i==2)
                {
                    frequentCheck2.setCheckItem("桥名牌");
                }
                if(i==7)
                {
                    frequentCheck2.setCheckItem("桥面铺装");
                }
                if(i==12)
                {
                    frequentCheck2.setCheckItem("钢结构物");
                }
                if(i==17)
                {
                    frequentCheck2.setCheckItem("人行道");
                }
                if(i==22)
                {
                    frequentCheck2.setCheckItem("伸缩缝");
                }
                if(i==27)
                {
                    frequentCheck2.setCheckItem("栏杆");
                }
                if(i==32)
                {
                    frequentCheck2.setCheckItem("排水设施");
                }
                if(i==37)
                {
                    frequentCheck2.setCheckItem("桥路连接位置");
                }
                if(i==42)
                {
                    frequentCheck2.setCheckItem("上部结构");
                }
                if(i==47)
                {
                    frequentCheck2.setCheckItem("支座");
                }
                if(i==52)
                {
                    frequentCheck2.setCheckItem("下部结构");
                }

                frequentCheck2.setPerfect((String)al.get(i));
                frequentCheck2.setDamageType((String)al.get(i+1));
                frequentCheck2.setDamageDegree((String)al.get(i+2));
                frequentCheck2.setDamageLocation((String)al.get(i+3));
                frequentCheck2.setRemark((String)al.get(i+4));
                frequent_check.toString();
                f2.add(frequentCheck2);
            }
        try {
            frequent_check.addFrequentInfo2(f2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObjec=new JSONObject();
        jsonObjec.put("flag","true");
        PrintWriter pw=response.getWriter();
        pw.print(jsonObjec);
        pw.close();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
