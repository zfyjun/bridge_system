package bridge;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
      JDBC myjdbc = new JDBC();
        myjdbc.getConn();
     //  BridgeDetectInfo bdi=new BridgeDetectInfo();
//       System.out.println("run");
//       bdi.getBdsiNo("01");
//       bdi.getBdsiName("01");
//       bdi.getSsiName("01");
//       bdi.getBpsiName("01");
//       bdi.getPsiName("01");
        //bdi.getBdsiType("01");
        //System.out.println("level");
      // bdi.getBdsiLevle("01");
        //桥名->桥梁编号->桥梁类型->BridgeDeck信息存储
        List<BridgeDeck> bridgeDecks=new ArrayList<>();
        BridgeScoreDao.getBridgeNo("测试桥");
        BridgeScoreDao.getTypeNo();
        bridgeDecks=BridgeScoreDao.setDeckInfo();
        BridgeScoreDao.setBdsiNo( bridgeDecks);
        BridgeScoreDao.setBddScore( bridgeDecks);
        BridgeScoreDao.setBdsiWeight(bridgeDecks);
        BridgeScoreDao.getDeckTotalScore( bridgeDecks);
    }
}
