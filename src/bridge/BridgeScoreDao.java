package bridge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BridgeScoreDao extends JDBC {
    private static BridgeScore bridgeScore = new BridgeScore();



    //根据桥编号找到桥梁类型编号,根据桥梁编号跳转到相应的类型的定期检测
    public static String getTypeNo() {
        String type_no = "";
        Connection conn = getConn();
        String sql = "select type_no from bridge_info where bridge_no=?";
        PreparedStatement p;

        try {
            p = conn.prepareStatement(sql);
            p.setString(1, bridgeScore.getBridgeNo());
            ResultSet result = p.executeQuery();
            if (result.next()) {
                type_no = result.getString("type_no");
            }
            System.out.println("桥梁类型：" + type_no);
            p.close();
            conn.close();
            bridgeScore.setTypeNo(type_no);
            return type_no;
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    //根据用户输入的桥梁名称找到id号并在定期检测表中存储用户输入的桥梁名称,桥梁名称不可重名
    public static void insertRegularCheck(List<RugularCheck>rugularChecks) {
        Connection conn = getConn();
        String sql = "insert into regular_checking(bridge_no,l_name,s_name,damageType,damageLevel) value(?,?,?,?,?)";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for(int i=0;i<rugularChecks.size();i++)
            {
                p.setString(1, bridgeScore.getBridgeNo());
                p.setString(2, rugularChecks.get(i).getL_name());
                p.setString(3,  rugularChecks.get(i).getS_name());
                p.setString(4,  rugularChecks.get(i).getDamageType());
                p.setString(5,  rugularChecks.get(i).getDamageLevel());
                p.executeUpdate();
            }
            conn.close();
            System.out.println("成功插入");
        } catch (
                SQLException throwables) {
            System.out.println("插入数据失败");
            throwables.printStackTrace();
        }
    }

    //存储数据阶段使用，根据定期检测表中存储的桥梁名称查询桥梁id,桥梁不允许重名
    public static String getBridgeNo(String bridgeName) {
        Connection conn = getConn();
        String bridgeNo = " ";
        String sql = "select bridge_no from bridge_info where bridge_name=?";
        PreparedStatement p;

        try {
            p = conn.prepareStatement(sql);
            p.setString(1, bridgeName);
            ResultSet result = p.executeQuery();
            if (result.next()) {
                bridgeNo = result.getString("bridge_no");
            }
            bridgeScore.setBridgeNo(bridgeNo);
            p.close();
            conn.close();
            System.out.println(bridgeNo);
            return bridgeNo;
        } catch (
                SQLException throwables) {
            System.out.println("桥梁id查询失败！");
            throwables.printStackTrace();
        }
        return null;
    }

    //根据存储定期检测的桥面系构建名称
    public static List<BridgeDeck> setDeckInfo() {
        Connection conn = getConn();
        List<BridgeDeck> bridgeDecks = new ArrayList<>();
        String sql = "select * from regular_checking where bridge_no=?";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, bridgeScore.getBridgeNo());
            ResultSet result = p.executeQuery();
            while (result.next()) {
                BridgeDeck bridgeDeck = new BridgeDeck();
                if (result.getString("l_name").equals("桥面系")) {
                    bridgeDeck.setBdsiName(result.getString("s_name"));
                    bridgeDeck.setBddName(result.getString("damageType"));
                    bridgeDeck.setBddLevel(result.getString("damageLevel"));
                    bridgeDecks.add(bridgeDeck);
                }

            }
            p.close();
            conn.close();
            System.out.println(bridgeDecks.size());
            System.out.println("桥梁编号："+bridgeScore.getBridgeNo());
            System.out.println("桥类型标号："+bridgeScore.getTypeNo());
            for (BridgeDeck BD : bridgeDecks) {
                System.out.println(BD);
            }
            return bridgeDecks;
        } catch (
                SQLException throwables) {
            System.out.println("BridgeDeck信息存储失败！");
            throwables.printStackTrace();
        }
        return null;
    }

    //根据定期检测的桥面系小部件（如桥面铺装）set deck的部件id.若后期无操作，可设置为无返回值函数类型
    public static String setBdsiNo(List<BridgeDeck> bridgeDecks) {
        Connection conn = getConn();
        String bdsiNo = " ";
        String sql = "select bdsi_no,bdd_no from bridge_deck_score_info,bd_damage_type where bdsi_name=? and bdd_name=?";
        PreparedStatement p;

        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i < bridgeDecks.size(); i++) {
                p.setString(1, bridgeDecks.get(i).getBdsiName());
                p.setString(2, bridgeDecks.get(i).getBddName());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    bridgeDecks.get(i).setBdsiNo(result.getString("bdsi_no"));
                    bridgeDecks.get(i).setBddNo(result.getString("bdd_no"));
                }

            }
            p.close();
            conn.close();
            for (BridgeDeck BD : bridgeDecks) {
                System.out.println("here");
                System.out.println(BD);
            }
            System.out.println("插入bdsiNo、bddNo");

            return bdsiNo;
        } catch (
                SQLException throwables) {
            System.out.println("插入bdsiNo、bddNo失败！");
            throwables.printStackTrace();
        }
        return null;
    }



    //根据桥面系大部件（如桥面铺装）id、损坏类型id、损坏等级set deck扣分值,若后期无操作，可设置为无返回值函数类型
    public static String setBddScore(List<BridgeDeck> bridgeDecks) {
        Connection conn = getConn();
        String bdd = " ";
        String sql = "select bdd_score from bdd where  bdsi_no=? and bdd_no=? and bdd_level=? ";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i < bridgeDecks.size(); i++) {
                p.setString(1, bridgeDecks.get(i).getBdsiNo());
                p.setString(2, bridgeDecks.get(i).getBddNo());
                p.setString(3, bridgeDecks.get(i).getBddLevel());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    bridgeDecks.get(i).setScore(result.getDouble("bdd_score"));
                }

            }
            p.close();
            conn.close();
            System.out.println("插入bdd_score");
            for (BridgeDeck BD : bridgeDecks) {
                System.out.println(BD);
            }
            return bdd;
        } catch (
                SQLException throwables) {
            System.out.println("插入bdd_score失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //根据桥面系大部件（如桥面铺装）id、桥类型id确定桥面铺装等评价要素在相应类型下的权重,若后期无操作，可设置为无返回值函数类型
    public static String setBdsiWeight(List<BridgeDeck> bridgeDecks) {
        Connection conn = getConn();
        String bdd = " ";
        String sql = "select bds_weight from bds where  bdsi_no=? and type_no=? ";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i < bridgeDecks.size(); i++) {
                p.setString(1, bridgeDecks.get(i).getBdsiNo());
                p.setString(2, bridgeScore.getBridgeNo());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    bridgeDecks.get(i).setWeight(result.getDouble("bds_weight"));
                }

            }
            p.close();
            conn.close();
            System.out.println("插入bds_weight");
            for (BridgeDeck BD : bridgeDecks) {
                System.out.println(BD);
            }
            return bdd;
        } catch (
                SQLException throwables) {
            System.out.println("插入bds_weight失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //根据桥类型统计桥面系评价要素数量
    public static int getDeckEvaluateNum() {
        Connection conn = getConn();
        int num = 0;
        String sql = "select * from bds where type_no=? ";
        PreparedStatement p;

        try {
            p = conn.prepareStatement(sql);
            p.setString(1, bridgeScore.getTypeNo());
            ResultSet result = p.executeQuery();
            while (result.next()) {
                num = result.getRow();
            }
            p.close();
            conn.close();
            System.out.println(num);
            return num;
        } catch (
                SQLException throwables) {
            System.out.println("桥面系评价要素数量查询失败！");
            throwables.printStackTrace();
        }
        return 0;
    }

    //桥面系BCI计算
    public static double getDeckTotalScore(List<BridgeDeck> bridgeDecks) {
        int a = getDeckEvaluateNum();//评价要素总数
        int len = bridgeDecks.size();//含有每项评价要素的每项损坏扣分值,
        System.out.println("a:" + a + "len:" + len);
        //List<Double> totalBci = new ArrayList<>();
        List<Double> totalWeight = new ArrayList<>();
        List<Double> totalMdp = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");//设置保留两位小数
        double ratio = 0;
        double score = 0;
        double weight = 0;
        double mdp = 0;//桥面系第h类要素中损坏的综合扣分值；当MDPh＜max(DPhi)时，取值为max(DPhi)；当MDPh＞100时，取值为100；
        //计算第h类要素中MDP
        //double BSI = 0;
        double BCI = 0;
        System.out.println("scorehere");
        for (int i = 0; i < 15;) {
            System.out.println("scoreForHere");
            int index = 0;
            double maxDp = 0;
            List<Double> totalDp = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                //System.out.println("scoreForHere2");
                if (bridgeDecks.get(j).getBdsiNo().equals(bridgeDecks.get(i).getBdsiNo())) {
                    System.out.println("scoreForHere3");
                    score += bridgeDecks.get(j).getScore();//第i类要素中所有损坏项的总扣分值；
                    index = j + 1;
                    totalDp.add(bridgeDecks.get(j).getScore());
                }
                else if(j==14)
                {
                    break;
                }
            }
            System.out.println("第" + i + "类的总分数：" + score);
            Collections.sort(totalDp);//collection排序从小到大
            Collections.reverse(totalDp);
            maxDp = totalDp.get(0);
            System.out.println("第" + i + "类最大Dp值" + maxDp);
//            for (Double DP : totalDp) {
//
//                System.out.println("第" + i + "类的DP值：" + DP);
//            }
            for (int k = 0; k <15; k++) {
                if (bridgeDecks.get(k).getBdsiNo().equals(bridgeDecks.get(i).getBdsiNo())) {
                    ratio = bridgeDecks.get(k).getScore() / score;//第i类要素中第k项损坏项的比例；
                    System.out.println("第" + i + "类" + k + "项的比例：" + ratio);
                    weight = Double.parseDouble(df.format(3 * ratio * ratio * ratio - 5.5 * ratio * ratio + 3.5 * ratio));
                    //totalWeight.add(weight);
                    System.out.println("第" + i + "类" + k + "项的权重：" + weight);
                    mdp += weight * bridgeDecks.get(k).getScore();
                    System.out.println(mdp);
                }
                else if(k==14)
                {
                    break;
                }
            }
            if (mdp > 100) {
                mdp = 100;
            } else if (mdp < maxDp) {
                mdp = maxDp;
            }
            totalMdp.add(mdp);
            //totalScore.add(score);
            //获取桥面系各类评价要素的权重值
            totalWeight.add(bridgeDecks.get(i).getWeight());
            i = index;
        }
//        for (Double MDP : totalMdp) {
//
//            System.out.println("各评价要素的MDP值：" + MDP);
//        }
//            for (Double Weight : totalWeight) {
//
//            System.out.println("各评价要素的weight值：" +  Weight);
//        }

        //类型桥桥面系评价要素数量和totalMdp长度应该相等
        System.out.println(bridgeScore.getTypeNo() + "类型桥桥面系评价要素数量:" + a + "totalMdp.size():" + totalMdp.size());
        for (int i = 0; i < totalMdp.size(); i++) {
            BCI+=(100-totalMdp.get(i))*totalWeight.get(i);
        }
        //Collections.sort(totalBsi);
        //BSI = totalBsi.get(0);
       // System.out.println("桥面系BSi的值为" + BSI);
        System.out.println("桥面系BCI的值为" + BCI);
       bridgeScore.setDeckTotalScore(Double.parseDouble(df.format(BCI)));
        //该计算BCI的值，可以先验证一下BSI
        return BCI;
    }

//进行定期检测时，应根据桥id检查表中是否有该桥的检测记录，若有，则是更新定期检测表操作，而不是插入

    //根据存储定期检测的上部结构构建名称
    public static List<SuperStructure> setSuperStructrueInfo() {
        Connection conn = getConn();
        List<SuperStructure> superStructures = new ArrayList<>();
        String sql = "select * from regular_checking where bridge_no=?";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, bridgeScore.getBridgeNo());
            ResultSet result = p.executeQuery();
            while (result.next()) {
                SuperStructure superStructure = new SuperStructure();
                if (result.getString("l_name").equals("上部结构")) {
                    superStructure.setSsiName(result.getString("s_name"));
                    superStructure.setSddName(result.getString("damageType"));
                    superStructure.setSdLevel(result.getString("damageLevel"));
                    superStructures.add( superStructure);
                }

            }
            p.close();
            conn.close();
            System.out.println("桥梁编号："+bridgeScore.getBridgeNo());
            System.out.println("桥类型标号："+bridgeScore.getTypeNo());
            for (SuperStructure BD : superStructures) {
                System.out.println(BD);
            }
            return superStructures;
        } catch (
                SQLException throwables) {
            System.out.println("SuperStructure信息存储失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //根据定期检测的上部结构小部件（如主梁）set 上部结构的部件id.若后期无操作，可设置为无返回值函数类型
    public static String setSsiNo(List<SuperStructure> superStructures) {
        Connection conn = getConn();
        String bdsiNo = " ";
        String sql = "select ssi_no,sdd_No from superstructure_score_info,sd_damage_type where ssi_name=? and sdd_name=?";
        PreparedStatement p;

        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i < superStructures.size(); i++) {
                p.setString(1, superStructures.get(i).getSsiName());
                p.setString(2, superStructures.get(i).getSddName());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    superStructures.get(i).setSsiNo(result.getString("ssi_no"));
                    superStructures.get(i).setSddNo(result.getString("sdd_No"));
                }

            }
            p.close();
            conn.close();
            for (SuperStructure BD : superStructures) {
                System.out.println("here");
                System.out.println(BD);
            }
            System.out.println("插入ssiNo、sddNo");

            return bdsiNo;
        } catch (
                SQLException throwables) {
            System.out.println("插入ssiNo、sddNo失败！");
            throwables.printStackTrace();
        }
        return null;
    }

    //根据上部结构大部件（如主梁）id、损坏类型id、损坏等级set 上部结构扣分值,若后期无操作，可设置为无返回值函数类型
    public static String setSddScore(List<SuperStructure> superStructures) {
        Connection conn = getConn();
        String bdd = " ";
        String sql = "select sd_score from sd where  ssi_no=? and sdd_No=? and sd_level=? ";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i <superStructures.size(); i++) {
                p.setString(1, superStructures.get(i).getSsiNo());
                p.setString(2, superStructures.get(i).getSddNo());
                p.setString(3,superStructures.get(i).getSdLevel());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    superStructures.get(i).setScore(result.getDouble("sd_score"));
                }

            }
            p.close();
            conn.close();
            System.out.println("插入sd_score");
            for (SuperStructure BD : superStructures) {
                System.out.println(BD);
            }
            return bdd;
        } catch (
                SQLException throwables) {
            System.out.println("插入sd_score失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //根据上部结构大部件（如主梁）id、桥类型id确定主梁等评价要素在相应类型下的权重,若后期无操作，可设置为无返回值函数类型
    public static String setSsiWeight(List<SuperStructure> superStructures) {
        Connection conn = getConn();
        String bdd = " ";
        String sql = "select bss_weight from bss where  ssi_no=? and type_no=? ";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i < superStructures.size(); i++) {
                p.setString(1, superStructures.get(i).getSsiNo());
                p.setString(2, bridgeScore.getBridgeNo());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    superStructures.get(i).setWeight(result.getDouble("bss_weight"));
                }

            }
            p.close();
            conn.close();
            System.out.println("插入bss_weight");
            for (SuperStructure BD : superStructures) {
                System.out.println(BD);
            }
            return bdd;
        } catch (
                SQLException throwables) {
            System.out.println("插入bss_weight失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //上部结构BCI计算
    public static double getSuperTotalScore(List<SuperStructure> superStructures) {
        //int a = getDeckEvaluateNum();//评价要素总数
        int len = superStructures.size();//含有每项评价要素的每项损坏扣分值,
       // System.out.println("a:" + a + "len:" + len);
        //List<Double> totalBci = new ArrayList<>();
        List<Double> totalWeight = new ArrayList<>();
        List<Double> totalMdp = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");//设置保留两位小数
        double ratio = 0;
        double score = 0;
        double weight = 0;
        double mdp = 0;//桥面系第h类要素中损坏的综合扣分值；当MDPh＜max(DPhi)时，取值为max(DPhi)；当MDPh＞100时，取值为100；
        //计算第h类要素中MDP
        //double BSI = 0;
        double BCI = 0;
        System.out.println("scorehere");
        for (int i = 0; i < len;) {
            System.out.println("scoreForHere");
            int index = 0;
            double maxDp = 0;
            List<Double> totalDp = new ArrayList<>();
            for (int j = 0; j < len; j++) {

                if (superStructures.get(j).getSsiNo().equals(superStructures.get(i).getSsiNo())) {
                    score += superStructures.get(j).getScore();//第i类要素中所有损坏项的总扣分值；
                    index = j + 1;
                    totalDp.add(superStructures.get(j).getScore());
                }
            }
            System.out.println("第" + i + "类的总分数：" + score);
            Collections.sort(totalDp);//collection排序从小到大
            Collections.reverse(totalDp);
            maxDp = totalDp.get(0);
            System.out.println("第" + i + "类最大Dp值" + maxDp);
            for (Double DP : totalDp) {

                System.out.println("第" + i + "类的DP值：" + DP);
            }
            for (int k = 0; k < len; k++) {
                if (superStructures.get(k).getSsiNo().equals(superStructures.get(i).getSsiNo())) {
                    ratio = superStructures.get(k).getScore() / score;//第i类要素中第k项损坏项的比例；
                    System.out.println("第" + i + "类" + k + "项的比例：" + ratio);
                    weight = Double.parseDouble(df.format(3 * ratio * ratio * ratio - 5.5 * ratio * ratio + 3.5 * ratio));
                    //totalWeight.add(weight);
                    System.out.println("第" + i + "类" + k + "项的权重：" + weight);
                    mdp += weight *superStructures.get(k).getScore();
                }
            }
            if (mdp > 100) {
                mdp = 100;
            } else if (mdp < maxDp) {
                mdp = maxDp;
            }
            totalMdp.add(mdp);
            //totalScore.add(score);
            //获取桥面系各类评价要素的权重值
            totalWeight.add(superStructures.get(i).getWeight());
            i = index;
        }
        for (Double MDP : totalMdp) {

            System.out.println("各评价要素的MDP值：" + MDP);
        }
        for (Double Weight : totalWeight) {

            System.out.println("各评价要素的weight值：" +  Weight);
        }

        //类型桥上部结构评价要素数量和totalMdp长度应该相等
        System.out.println(bridgeScore.getTypeNo() + "totalMdp.size():" + totalMdp.size());
        for (int i = 0; i < totalMdp.size(); i++) {
            BCI+=(100-totalMdp.get(i))*totalWeight.get(i);
        }
        //Collections.sort(totalBsi);
        //BSI = totalBsi.get(0);
        // System.out.println("桥面系BSi的值为" + BSI);
        System.out.println("上部结构BCI的值为" + BCI);
        bridgeScore.setSuperStructureTotalScore(Double.parseDouble(df.format(BCI)));
        //该计算BCI的值，可以先验证一下BSI
        return BCI;
    }
    //根据存储定期检测的桥墩构建名称
    public static List<Pier> setPierInfo() {
        Connection conn = getConn();
        List<Pier> piers = new ArrayList<>();
        String sql = "select * from regular_checking where bridge_no=?";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, bridgeScore.getBridgeNo());
            ResultSet result = p.executeQuery();
            while (result.next()) {
                Pier pier = new Pier();
                if (result.getString("l_name").equals("桥墩")) {
                    pier.setPsiName(result.getString("s_name"));
                    pier.setPddName(result.getString("damageType"));
                    pier.setPdLevel(result.getString("damageLevel"));
                    piers.add( pier);
                }

            }
            p.close();
            conn.close();
            System.out.println("桥梁编号："+bridgeScore.getBridgeNo());
            System.out.println("桥类型标号："+bridgeScore.getTypeNo());
            for (Pier BD : piers) {
                System.out.println(BD);
            }
            return piers;
        } catch (
                SQLException throwables) {
            System.out.println("pier信息存储失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //根据定期检测的桥墩小部件（如主梁）set 桥墩的部件id.若后期无操作，可设置为无返回值函数类型
    public static String setPsiNo(List<Pier> piers) {
        Connection conn = getConn();
        String psiNo = " ";
        String sql = "select psi_no,pdd_No from pier_score_info,pd_damage_type where psi_name=? and pdd_Name=?";
        PreparedStatement p;

        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i < piers.size(); i++) {
                p.setString(1, piers.get(i).getPsiName());
                p.setString(2,piers.get(i).getPddName());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    piers.get(i).setPsiNo(result.getString("psi_no"));
                    piers.get(i).setPddNo(result.getString("pdd_No"));
                }

            }
            p.close();
            conn.close();
            for (Pier BD : piers) {
                System.out.println("here");
                System.out.println(BD);
            }
            System.out.println("插入psiNo、pddNo");

            return psiNo;
        } catch (
                SQLException throwables) {
            System.out.println("插入psiNo、pddNo失败！");
            throwables.printStackTrace();
        }
        return null;
    }

    //根据桥墩大部件（如盖梁）id、损坏类型id、损坏等级set 桥墩扣分值,若后期无操作，可设置为无返回值函数类型
    public static String setPddScore(List<Pier> piers) {
        Connection conn = getConn();
        String bdd = " ";
        String sql = "select pd_score from pd where  psi_no=? and pdd_No=? and pd_level=? ";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i <piers.size(); i++) {
                p.setString(1, piers.get(i).getPsiNo());
                p.setString(2, piers.get(i).getPddNo());
                p.setString(3,piers.get(i).getPdLevel());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    piers.get(i).setScore(result.getDouble("pd_score"));
                }

            }
            p.close();
            conn.close();
            System.out.println("插入pd_score");
            for (Pier BD : piers) {
                System.out.println(BD);
            }
            return bdd;
        } catch (
                SQLException throwables) {
            System.out.println("插入pd_score失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //根据桥墩大部件（如主梁）id、桥类型id确定盖梁等评价要素在相应类型下的权重,若后期无操作，可设置为无返回值函数类型
    public static String setPsiWeight(List<Pier> piers) {
        Connection conn = getConn();
        String bdd = " ";
        String sql = "select bps_weight from bps where  psi_no=? and type_no=? ";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i <  piers.size(); i++) {
                p.setString(1,  piers.get(i).getPsiNo());
                p.setString(2, bridgeScore.getBridgeNo());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    piers.get(i).setWeight(result.getDouble("bps_weight"));
                }

            }
            p.close();
            conn.close();
            System.out.println("插入bps_weight");
            for (Pier BD :  piers) {
                System.out.println(BD);
            }
            return bdd;
        } catch (
                SQLException throwables) {
            System.out.println("插入bps_weight失败！");
            throwables.printStackTrace();
        }
        return null;
    }

    //桥墩BCI计算
    public static double getPierrTotalScore(List<Pier> piers) {
        //int a = getDeckEvaluateNum();//评价要素总数
        int len = piers.size();//含有每项评价要素的每项损坏扣分值,
        // System.out.println("a:" + a + "len:" + len);
        //List<Double> totalBci = new ArrayList<>();
        List<Double> totalWeight = new ArrayList<>();
        List<Double> totalMdp = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");//设置保留两位小数
        double ratio = 0;
        double score = 0;
        double weight = 0;
        double mdp = 0;//桥墩第h类要素中损坏的综合扣分值；当MDPh＜max(DPhi)时，取值为max(DPhi)；当MDPh＞100时，取值为100；
        //计算第h类要素中MDP
        //double BSI = 0;
        double BCI = 0;
        System.out.println("scorehere");
        for (int i = 0; i < 15;) {
            System.out.println("scoreForHere");
            int index = 0;
            double maxDp = 0;
            List<Double> totalDp = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                if (piers.get(j).getPsiNo().equals(piers.get(i).getPsiNo())) {
                    score += piers.get(j).getScore();//第i类要素中所有损坏项的总扣分值；
                    index = j + 1;
                    totalDp.add(piers.get(j).getScore());
                }
            }
            System.out.println("第" + i + "类的总分数：" + score);
            Collections.sort(totalDp);//collection排序从小到大
            Collections.reverse(totalDp);
            maxDp = totalDp.get(0);
            System.out.println("第" + i + "类最大Dp值" + maxDp);
            for (Double DP : totalDp) {

                System.out.println("第" + i + "类的DP值：" + DP);
            }
            for (int k = 0; k < 15; k++) {
                if (piers.get(k).getPsiNo().equals(piers.get(i).getPsiNo())) {
                    ratio = piers.get(k).getScore() / score;//第i类要素中第k项损坏项的比例；
                    System.out.println("第" + i + "类" + k + "项的比例：" + ratio);
                    weight = Double.parseDouble(df.format(3 * ratio * ratio * ratio - 5.5 * ratio * ratio + 3.5 * ratio));
                    //totalWeight.add(weight);
                    System.out.println("第" + i + "类" + k + "项的权重：" + weight);
                    mdp += weight *piers.get(k).getScore();
                }
            }
            if (mdp > 100) {
                mdp = 100;
            } else if (mdp < maxDp) {
                mdp = maxDp;
            }
            totalMdp.add(mdp);
            //totalScore.add(score);
            //获取桥面系各类评价要素的权重值
            totalWeight.add(piers.get(i).getWeight());
            i = index;
        }
        for (Double MDP : totalMdp) {

            System.out.println("各评价要素的MDP值：" + MDP);
        }
        for (Double Weight : totalWeight) {

            System.out.println("各评价要素的weight值：" +  Weight);
        }

        //类型桥桥墩评价要素数量和totalMdp长度应该相等
        System.out.println(bridgeScore.getTypeNo() + "totalMdp.size():" + totalMdp.size());
        for (int i = 0; i < totalMdp.size(); i++) {
            BCI+=(100-totalMdp.get(i))*totalWeight.get(i);
        }
        //Collections.sort(totalBsi);
        //BSI = totalBsi.get(0);
        // System.out.println("桥面系BSi的值为" + BSI);
        System.out.println("pier BCI的值为" + BCI);
        bridgeScore.setPierTotalScore(Double.parseDouble(df.format(BCI)));
        //该计算BCI的值，可以先验证一下BSI
        return BCI;
    }

    //根据存储定期检测的bank pier构建名称
    public static List<BankPier> setBankPierInfo() {
        Connection conn = getConn();
        List<BankPier>bankPiers = new ArrayList<>();
        String sql = "select * from regular_checking where bridge_no=?";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, bridgeScore.getBridgeNo());
            ResultSet result = p.executeQuery();
            while (result.next()) {
                BankPier bankPier = new BankPier();
                if (result.getString("l_name").equals("桥台")) {
                    bankPier.setBpsiName(result.getString("s_name"));
                    bankPier.setBpName(result.getString("damageType"));
                    bankPier.setBpLevel(result.getString("damageLevel"));
                    bankPiers.add( bankPier);
                }

            }
            p.close();
            conn.close();
            System.out.println("桥梁编号："+bridgeScore.getBridgeNo());
            System.out.println("桥类型标号："+bridgeScore.getTypeNo());
            for (BankPier BD : bankPiers) {
                System.out.println(BD);
            }
            return bankPiers;
        } catch (
                SQLException throwables) {
            System.out.println("pier信息存储失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //根据定期检测的bank pier小部件（如主梁）set bank pier的部件id.若后期无操作，可设置为无返回值函数类型
    public static String setBpsiNo(List<BankPier> bankPiers) {
        Connection conn = getConn();
        String psiNo = " ";
        String sql = "select bpsi_no,bp_No from bank_pier_score_info,bp_damage_info where bpsi_name=? and bp_Name=?";
        PreparedStatement p;

        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i < bankPiers.size(); i++) {
                p.setString(1, bankPiers.get(i).getBpsiName());
                p.setString(2,bankPiers.get(i).getBpName());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    bankPiers.get(i).setBpsiNo(result.getString("bpsi_no"));
                    bankPiers.get(i).setBpNo(result.getString("bp_No"));
                }

            }
            p.close();
            conn.close();
            for (BankPier BD : bankPiers) {
                System.out.println("here");
                System.out.println(BD);
            }
            System.out.println("插入bpsiNo、bpNo");

            return psiNo;
        } catch (
                SQLException throwables) {
            System.out.println("插入psiNo、pddNo失败！");
            throwables.printStackTrace();
        }
        return null;
    }

    //根据bank pier大部件（如盖梁）id、损坏类型id、损坏等级set bank pier扣分值,若后期无操作，可设置为无返回值函数类型
    public static String setBpScore(List<BankPier> bankPiers) {
        Connection conn = getConn();
        String bdd = " ";
        String sql = "select bp_score from bp where  bpsi_no=? and bp_No=? and bp_level=? ";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i <bankPiers.size(); i++) {
                p.setString(1, bankPiers.get(i).getBpsiNo());
                p.setString(2, bankPiers.get(i).getBpNo());
                p.setString(3,bankPiers.get(i).getBpLevel());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    bankPiers.get(i).setScore(result.getDouble("bp_score"));
                }

            }
            p.close();
            conn.close();
            System.out.println("插入bp_score");
            for (BankPier BD : bankPiers) {
                System.out.println(BD);
            }
            return bdd;
        } catch (
                SQLException throwables) {
            System.out.println("插入bp_score失败！");
            throwables.printStackTrace();
        }
        return null;
    }
    //根据bank pier大部件（如主梁）id、桥类型id确定盖梁等评价要素在相应类型下的权重,若后期无操作，可设置为无返回值函数类型
    public static String setBbpsiWeight(List<BankPier> bankPiers) {
        Connection conn = getConn();
        String bdd = " ";
        String sql = "select bbps_weight from bbps where  bpsi_no=? and type_no=? ";
        PreparedStatement p;
        try {
            p = conn.prepareStatement(sql);
            for (int i = 0; i <  bankPiers.size(); i++) {
                p.setString(1,  bankPiers.get(i).getBpsiNo());
                p.setString(2, bridgeScore.getBridgeNo());
                ResultSet result = p.executeQuery();
                if (result.next()) {
                    bankPiers.get(i).setWeight(result.getDouble("bps_weight"));
                }

            }
            p.close();
            conn.close();
            System.out.println("插入bbps_weight");
            for (BankPier BD :  bankPiers) {
                System.out.println(BD);
            }
            return bdd;
        } catch (
                SQLException throwables) {
            System.out.println("插入bbps_weight失败！");
            throwables.printStackTrace();
        }
        return null;
    }

    //bank pier BCI计算
    public static double getBankPierTotalScore(List<BankPier> bankPiers) {
        //int a = getDeckEvaluateNum();//评价要素总数
        int len = bankPiers.size();//含有每项评价要素的每项损坏扣分值,
        // System.out.println("a:" + a + "len:" + len);
        //List<Double> totalBci = new ArrayList<>();
        List<Double> totalWeight = new ArrayList<>();
        List<Double> totalMdp = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");//设置保留两位小数
        double ratio = 0;
        double score = 0;
        double weight = 0;
        double mdp = 0;//桥墩第h类要素中损坏的综合扣分值；当MDPh＜max(DPhi)时，取值为max(DPhi)；当MDPh＞100时，取值为100；
        //计算第h类要素中MDP
        //double BSI = 0;
        double BCI = 0;
        System.out.println("scorehere");
        for (int i = 0; i < 15;) {
            System.out.println("scoreForHere");
            int index = 0;
            double maxDp = 0;
            List<Double> totalDp = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                if (bankPiers.get(j).getBpsiNo().equals(bankPiers.get(i).getBpsiNo())) {
                    score += bankPiers.get(j).getScore();//第i类要素中所有损坏项的总扣分值；
                    index = j + 1;
                    totalDp.add(bankPiers.get(j).getScore());
                }
            }
            System.out.println("第" + i + "类的总分数：" + score);
            Collections.sort(totalDp);//collection排序从小到大
            Collections.reverse(totalDp);
            maxDp = totalDp.get(0);
            System.out.println("第" + i + "类最大Dp值" + maxDp);
            for (Double DP : totalDp) {

                System.out.println("第" + i + "类的DP值：" + DP);
            }
            for (int k = 0; k < 15; k++) {
                if (bankPiers.get(k).getBpsiNo().equals(bankPiers.get(i).getBpsiNo())) {
                    ratio = bankPiers.get(k).getScore() / score;//第i类要素中第k项损坏项的比例；
                    System.out.println("第" + i + "类" + k + "项的比例：" + ratio);
                    weight = Double.parseDouble(df.format(3 * ratio * ratio * ratio - 5.5 * ratio * ratio + 3.5 * ratio));
                    //totalWeight.add(weight);
                    System.out.println("第" + i + "类" + k + "项的权重：" + weight);
                    mdp += weight *bankPiers.get(k).getScore();
                }
            }
            if (mdp > 100) {
                mdp = 100;
            } else if (mdp < maxDp) {
                mdp = maxDp;
            }
            totalMdp.add(mdp);
            //totalScore.add(score);
            //获取桥面系各类评价要素的权重值
            totalWeight.add(bankPiers.get(i).getWeight());
            i = index;
        }
        for (Double MDP : totalMdp) {

            System.out.println("各评价要素的MDP值：" + MDP);
        }
        for (Double Weight : totalWeight) {

            System.out.println("各评价要素的weight值：" +  Weight);
        }

        //类型桥桥墩评价要素数量和totalMdp长度应该相等
        System.out.println(bridgeScore.getTypeNo() + "totalMdp.size():" + totalMdp.size());
        for (int i = 0; i < totalMdp.size(); i++) {
            BCI+=(100-totalMdp.get(i))*totalWeight.get(i);
        }
        //Collections.sort(totalBsi);
        //BSI = totalBsi.get(0);
        // System.out.println("桥面系BSi的值为" + BSI);
        System.out.println("bank pier BCI的值为" + BCI);
        bridgeScore.setBankPierTotalScore(Double.parseDouble(df.format(BCI)));
        //该计算BCI的值，可以先验证一下BSI
        return BCI;
    }
   //根据桥类型找到各项的权重
    public static double totalBridgeScore()
    {   String type_no = "";
        Connection conn = getConn();
        String sql = "select bridge_weight from weight where type_no=?";
        PreparedStatement p;
List<Double> weight=new ArrayList<>();
        try {
            p = conn.prepareStatement(sql);
            p.setString(1, bridgeScore.getTypeNo());
            ResultSet result = p.executeQuery();
            while (result.next()) {
                weight.add(result.getDouble("bridge_weight"));
            }
           // System.out.println("桥梁类型：" + type_no);
            p.close();
            conn.close();
            //double totalScore=weight.get(0)*bridgeScore.getDeckTotalScore()+weight.get(1)*bridgeScore.getSuperStructureTotalScore()+weight.get(2)*(bridgeScore.getBankPierTotalScore()+bridgeScore.getPierTotalScore());
            double totalScore=weight.get(0)*bridgeScore.getDeckTotalScore()+weight.get(1)*bridgeScore.getDeckTotalScore()+weight.get(2)*(bridgeScore.getDeckTotalScore()+bridgeScore.getDeckTotalScore());
            bridgeScore.setTotalScore( totalScore);
            return totalScore;
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
