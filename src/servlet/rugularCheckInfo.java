package servlet;

import bridge.*;
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
import java.util.concurrent.TimeUnit;

@WebServlet("/rugularCheckInfo")
public class rugularCheckInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("receive!");
        String info=request.getParameter("info");
        JSONArray jsonArray = JSONArray.parseArray(info);
        System.out.println(info);
        System.out.println(jsonArray.size());
        List<BridgeDeck>bridgeDecks=new ArrayList<>();
        List<SuperStructure>superStructures=new ArrayList<>();
        List<BankPier>bankPiers=new ArrayList<>();
        List<Pier>piers=new ArrayList<>();
        List<RugularCheck>rugularChecks=new ArrayList<>();
        List<Double>Scores=new ArrayList<>();

        for(int i=0;i<jsonArray.size();i++)
        {
            RugularCheck rugularCheck=new RugularCheck();
            String name=jsonArray.getJSONObject(i).getString("name");
           String value=jsonArray.getJSONObject(i).getString("value");
//            System.out.println(name.substring(0,name.indexOf("；")));
//            System.out.println("   xxx ");
//            System.out.println(name.substring(name.indexOf("；")+1,name.indexOf("；")));
           // System.out.println();
            String[] s=name.split("；");
            rugularCheck.setL_name(s[0]);
            rugularCheck.setS_name(s[1]);
            rugularCheck.setDamageType(s[2]);
            rugularCheck.setDamageLevel(value);
            rugularChecks.add(rugularCheck);
            for(String h:s)
            {
                System.out.println(h);
            }
            System.out.println("----------");

        }
        BridgeScoreDao.insertRegularCheck(rugularChecks);//数据插入定期检测表
        //计算桥面系BCI分数
        bridgeDecks= BridgeScoreDao.setDeckInfo();
        BridgeScoreDao.setBdsiNo( bridgeDecks);
        BridgeScoreDao.setBddScore( bridgeDecks);
        BridgeScoreDao.setBdsiWeight(bridgeDecks);
        BridgeScoreDao.getDeckTotalScore( bridgeDecks);
       //计算上部结构BCI分数
//        superStructures= BridgeScoreDao.setSuperStructrueInfo();
//        BridgeScoreDao.setSsiNo( superStructures);
//        BridgeScoreDao.setSddScore(  superStructures);
//        BridgeScoreDao.setSsiWeight( superStructures);
//        BridgeScoreDao.getSuperTotalScore(  superStructures);


        //计算桥墩BCI分数
//        piers= BridgeScoreDao.setPierInfo();
//        BridgeScoreDao.setPsiNo( piers);
//        BridgeScoreDao.setPddScore( piers);
//        BridgeScoreDao.setPsiWeight(piers);
//        BridgeScoreDao.getPierrTotalScore(piers);
//        //计算桥台BCI分数
//        bankPiers= BridgeScoreDao.setBankPierInfo();
//        BridgeScoreDao.setBpsiNo( bankPiers);
//        BridgeScoreDao.setBpScore(bankPiers);
//        BridgeScoreDao.setBbpsiWeight(bankPiers);
//        BridgeScoreDao.getBankPierTotalScore(bankPiers);
        //计算总分数
        double totalScore=BridgeScoreDao.totalBridgeScore()*10;
        Scores.add(totalScore);
        System.out.println(totalScore);
        JSONArray o=new JSONArray();
        o = JSONArray.parseArray(JSONObject.toJSONString(Scores));
        System.out.println("jsonArray2：" + o);
        PrintWriter pw = response.getWriter();
        pw.print(o);
        pw.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
