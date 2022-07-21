package bridge;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Frequent_check {
    private int bridge_id;
    private String check_item;
    private String perfect;
    private String damage_type;
    private String damage_degree;
    private String damage_location;
    private String remark;
    private String check_unit;
    private String check_people;
    private String check_data;
    private static Connection conn=JDBC.getConn();
    private static PreparedStatement preparedstatement;

    public void Frequent_check(int bridge_id, String check_item, String perfect, String damage_type, String damage_degree, String damage_location, String remark, String check_unit, String check_people, String check_data) {
        this.bridge_id = bridge_id;
        this.check_item = check_item;
        this.perfect = perfect;
        this.damage_type = damage_type;
        this.damage_degree = damage_degree;
        this.damage_location = damage_location;
        this.remark = remark;
        this.check_unit = check_unit;
        this.check_people = check_people;
        this.check_data = check_data;
    }

    public void setCheck_people(String check_people) {
        this.check_people = check_people;
    }
    public String getCheck_people() {
        return check_people;
    }
    public void setCheck_unit(String check_unit) {
        this.check_unit = check_unit;
    }

    public int getBridge_id() {
        return bridge_id;
    }

    public void setBridgeID(int bridgeID) {
        this.bridge_id = bridgeID;
    }

    public String getCheck_data() {
        return check_data;
    }

    public void setCheckDate(String checkDate) {
        this.check_data = checkDate;
    }

    public String getCheckItem() {
        return check_item;
    }

    public void setCheckItem(String checkItem) {
        this.check_item = checkItem;
    }

    public String getPerfect() {
        return perfect;
    }

    public void setPerfect(String perfect) {
        this.perfect = perfect;
    }

    public String getDamageType() {
        return damage_type;
    }

    public void setDamageType(String damageType) {
        this.damage_type = damageType;
    }

    public String getDamageDegree() {
        return damage_degree;
    }

    public void setDamageDegree(String damageDegree) {
        this.damage_degree = damageDegree;
    }

    public String getDamageLocation() {
        return damage_location;
    }

    public void setDamageLocation(String damageLocation) {
        this.damage_location=damageLocation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCheck_unit(){
        return check_unit;
    }


    public void addFrequentInfo2(List<Frequent_check> f2) throws SQLException {
        for(int i =0;i<11;i++)
        {
            preparedstatement =conn.prepareStatement("insert into record values(?,?,?,?,?,?,?,?,?,?)");
            preparedstatement.setInt(1,f2.get(i).getBridge_id());
            preparedstatement.setString(2,f2.get(i).getCheckItem());
            preparedstatement.setString(3,f2.get(i).getPerfect());
            preparedstatement.setString(4,f2.get(i).getDamageType());
            preparedstatement.setString(5,f2.get(i).getDamageDegree());
            preparedstatement.setString(6,f2.get(i).getDamageLocation());
            preparedstatement.setString(7,f2.get(i).getRemark());
            preparedstatement.setString(8,f2.get(i).getCheck_unit());
            preparedstatement.setString(9,f2.get(i).getCheck_people());
            preparedstatement.setString(10,f2.get(i).getCheck_data());
            preparedstatement.executeUpdate();
        }
    }



    @Override
    public String toString() {
        return "Frequent_check{" +
                "bridge_id=" + bridge_id +
                ", check_item='" + check_item + '\'' +
                ", perfect='" + perfect + '\'' +
                ", damage_type='" + damage_type + '\'' +
                ", damage_degree='" + damage_degree + '\'' +
                ", damage_location='" + damage_location + '\'' +
                ", remark='" + remark + '\'' +
                ", check_unit='" + check_unit + '\'' +
                ", check_people='" + check_people + '\'' +
                ", check_data='" + check_data + '\'' +
                '}';
    }

    public List<Frequent_check> show_table(int bridgeID,String checkDate) throws SQLException {
        List<Frequent_check> f2 = new ArrayList<>();
        PreparedStatement preparedStatement = conn.prepareStatement("select * from record where bridge_id =? and check_data=?");
        preparedStatement.setInt(1,bridgeID);
        preparedStatement.setString(2,checkDate);
        ResultSet res = preparedStatement.executeQuery();
        while(res.next())
        {
            Frequent_check f = new Frequent_check();
            int bridge_id= res.getInt("bridge_id");
            String check_item = res.getString("check_item");
            String perfect = res.getString("perfect");
            String demage_type = res.getString("demage_type");
            String demage_degree = res.getString("demage_degree");
            String demage_location = res.getString("damage_location");
            String more = res.getString("more");
            String check_unit = res.getString("check_unit");
            String check_people = res.getString("check_people");
            String check_data = res.getString("check_data");

            f.Frequent_check(bridge_id,check_item,perfect,demage_type,
                    demage_degree,demage_location,more,check_unit,
                    check_people,check_data);
            f2.add(f);
        }
        return f2;
    }
}
