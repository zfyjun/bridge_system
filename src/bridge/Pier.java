package bridge;

public class Pier {
    private String psiName;
    private String psiNo;
    private String pddName;
    private String pddNo;
    private double score;
    private String pdLevel;
    //  private String damageType;
    private double weight;

    public String getPsiName() {
        return psiName;
    }

    public void setPsiName(String psiName) {
        this.psiName = psiName;
    }

    public String getPsiNo() {
        return psiNo;
    }

    public void setPsiNo(String psiNo) {
        this.psiNo = psiNo;
    }

    public String getPddName() {
        return pddName;
    }

    public void setPddName(String pddName) {
        this.pddName = pddName;
    }

    public String getPddNo() {
        return pddNo;
    }

    public void setPddNo(String pddNo) {
        this.pddNo = pddNo;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPdLevel() {
        return pdLevel;
    }

    public void setPdLevel(String pddLevel) {
        this.pdLevel = pddLevel;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
