package bridge;

public class BridgeScore {
    private double deckTotalScore;//桥面系总分
    private double superStructureTotalScore;//上部结构总分
    private double bankPierTotalScore;//桥台总分
    private double PierTotalScore;//桥墩总分
    private double totalScore;//桥梁总分
    private String bridgeNo;//桥梁编号
    private String typeNo;//桥梁类型编号

    public double getDeckTotalScore() {
        return deckTotalScore;
    }

    public void setDeckTotalScore(double deckTotalScore) {
        this.deckTotalScore = deckTotalScore;
    }

    public double getSuperStructureTotalScore() {
        return superStructureTotalScore;
    }

    public void setSuperStructureTotalScore(double superStructureTotalScore) {
        this.superStructureTotalScore = superStructureTotalScore;
    }

    public double getBankPierTotalScore() {
        return bankPierTotalScore;
    }

    public void setBankPierTotalScore(double bankPierTotalScore) {
        this.bankPierTotalScore = bankPierTotalScore;
    }

    public double getPierTotalScore() {
        return PierTotalScore;
    }

    public void setPierTotalScore(double pierTotalScore) {
        PierTotalScore = pierTotalScore;
    }

    public String getBridgeNo() {
        return bridgeNo;
    }

    public void setBridgeNo(String bridgeNo) {
        this.bridgeNo = bridgeNo;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    @Override
    public String toString() {
        return "BridgeScore{" +
                "deckTotalScore=" + deckTotalScore +
                ", superStructureTotalScore=" + superStructureTotalScore +
                ", bankPierTotalScore=" + bankPierTotalScore +
                ", PierTotalScore=" + PierTotalScore +
                ", totalScore=" + totalScore +
                ", bridgeNo='" + bridgeNo + '\'' +
                ", typeNo='" + typeNo + '\'' +
                '}';
    }
}
