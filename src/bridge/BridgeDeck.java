package bridge;

public class BridgeDeck {
    private String bdsiName;
    private String bdsiNo;
    private String bddName;
    private String bddNo;
    private double score;
    private String bddLevel;
  //  private String damageType;
    private double weight;

    public String getBdsiName() {
        return bdsiName;
    }

    public void setBdsiName(String bdsiName) {
        this.bdsiName = bdsiName;
    }

    public String getBdsiNo() {
        return bdsiNo;
    }

    public void setBdsiNo(String bdsiNo) {
        this.bdsiNo = bdsiNo;
    }

    public String getBddName() {
        return bddName;
    }

    public void setBddName(String bddName) {
        this.bddName = bddName;
    }

    public String getBddNo() {
        return bddNo;
    }

    public void setBddNo(String bddNo) {
        this.bddNo = bddNo;
    }

    public String getBddLevel() {
        return bddLevel;
    }

    public void setBddLevel(String bddLevel) {
        this.bddLevel = bddLevel;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

//    public String getDamageType() {
//        return damageType;
//    }
//
//    public void setDamageType(String damageType) {
//        this.damageType = damageType;
//    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "BridgeDeck{" +
                "bdsiName='" + bdsiName + '\'' +
                ", bdsiNo='" + bdsiNo + '\'' +
                ", bddName='" + bddName + '\'' +
                ", bddNo='" + bddNo + '\'' +
                ", score=" + score +
                ", bddLevel='" + bddLevel + '\'' +
                ", weight=" + weight +
                '}';
    }
}
