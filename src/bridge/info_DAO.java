package bridge;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class info_DAO {
    public int delete_b_i(String id){

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
//        String s_id_sql="select bridge_no from bridge_info";
        String b_i_sql="delete from bridge_info where bridge_no = ?";
        connection = conn_bridge_info.getConnection();
        try {
            preparedStatement = connection.prepareStatement(b_i_sql);
            preparedStatement.setString(1, id);
            int result=preparedStatement.executeUpdate();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,resultSet);
        }
        return 0;
    }
    public int delete_s_i(String id){

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
//        String s_id_sql="select bridge_no from bridge_info";
        String b_i_sql="delete from superstructure where bridge_no = ?";
        connection = conn_bridge_info.getConnection();
        try {
            preparedStatement = connection.prepareStatement(b_i_sql);
            preparedStatement.setString(1, id);
            int result=preparedStatement.executeUpdate();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,resultSet);
        }
        return 0;
    }
    public int delete_below_i(String id){

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
//        String s_id_sql="select bridge_no from bridge_info";
        String b_i_sql="delete from belowstructure where bridge_no = ?";
        connection = conn_bridge_info.getConnection();
        try {
            preparedStatement = connection.prepareStatement(b_i_sql);
            preparedStatement.setString(1, id);
            int result=preparedStatement.executeUpdate();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,resultSet);
        }
        return 0;
    }
    public int delete_g_i(String id){

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
//        String s_id_sql="select bridge_no from bridge_info";
        String b_i_sql="delete from general_information where bridge_no = ?";
        connection = conn_bridge_info.getConnection();
        try {
            preparedStatement = connection.prepareStatement(b_i_sql);
            preparedStatement.setString(1, id);
            int result=preparedStatement.executeUpdate();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,resultSet);
        }
        return 0;
    }
    public int insert_b_i(information information){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
//        String s_id_sql="select bridge_no from bridge_info";
        String b_i_sql="insert into bridge_info(bridge_no,type_no,bridge_name,area,bridge_lng,bridge_lat) values(?,?,?,?,?,?) ";
        String s_i_sql="insert into superstructure(bridge_no,beam_type,beam_num,crossbeam_type,limit_height,bearing_type,bearing_num,deck_structure,expansion_joint_type,expansion_joint_num) values(?,?,?,?,?,?,?,?,?,?) ";
        try {
            connection= conn_bridge_info.getConnection();
//            preparedStatement=connection.prepareStatement(s_id_sql);
            preparedStatement=connection.prepareStatement(b_i_sql);
            preparedStatement.setString(1,information.getId());
            preparedStatement.setString(2,information.getBi_type());
            preparedStatement.setString(3,information.getName());
            preparedStatement.setString(4,information.getArea());
            preparedStatement.setString(5,information.getLng());
            preparedStatement.setString(6,information.getLat());

            int result=preparedStatement.executeUpdate();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,null);
        }
        return 0;
    }

    public int insert_g_i(information information){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
//        String s_id_sql="select bridge_no from bridge_info";
        String b_i_sql="insert into bridge_info(bridge_no,type_no,bridge_name,area,bridge_lng,bridge_lat) values(?,?,?,?,?,?) ";
        String g_i_sql="insert into general_information(bridge_no,supervision_unit,overall_length,overall_width) values(?,?,?,?) ";
        String s_i_sql="insert into superstructure(bridge_no,beam_type,beam_num,crossbeam_type,limit_height,bearing_type,bearing_num,deck_structure,expansion_joint_type,expansion_joint_num) values(?,?,?,?,?,?,?,?,?,?) ";
        try {
            connection= conn_bridge_info.getConnection();
//            preparedStatement=connection.prepareStatement(s_id_sql);
//            preparedStatement=connection.prepareStatement(b_i_sql);
//            preparedStatement.setString(1,information.getId());
//            preparedStatement.setString(2,information.getBi_type());
//            preparedStatement.setString(3,information.getName());
//            preparedStatement.setString(4,information.getArea());
//            preparedStatement.setString(5,information.getLng());
//            preparedStatement.setString(6,information.getLat());
//
            preparedStatement=connection.prepareStatement(g_i_sql);
            preparedStatement.setString(1,information.getId());
            preparedStatement.setString(2,information.getGi_supervision_unit());
            System.out.println(information.getGi_overall_length());
            preparedStatement.setString(3,information.getGi_overall_length());
            preparedStatement.setString(4,information.getGi_overall_width());
//
//            preparedStatement=connection.prepareStatement(s_i_sql);
//            preparedStatement.setString(1,information.getId());
//            preparedStatement.setString(2,information.getSs_bean_type());
//            preparedStatement.setString(3,information.getSs_bean_num());
//            preparedStatement.setString(4,information.getSs_crossbeam_type());
//            preparedStatement.setString(5,information.getSs_limit_height());
//            preparedStatement.setString(6,information.getSs_bearing_type());
//            preparedStatement.setString(7,information.getSs_bearing_num());
//            preparedStatement.setString(8,information.getSs_deck_structure());
//            preparedStatement.setString(9,information.getSs_expansion());
//            preparedStatement.setString(10,information.getSs_expansion_joint_num());

            int result=preparedStatement.executeUpdate();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,null);
        }
        return 0;
    }

    public int insert_s_i(information information){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
//        String s_id_sql="select bridge_no from bridge_info";
        String b_i_sql="insert into bridge_info(bridge_no,type_no,bridge_name,area,bridge_lng,bridge_lat) values(?,?,?,?,?,?) ";
        String g_i_sql="insert into general_information(bridge_no,supervision_unit,overall_length,overall_width) values(?,?,?,?) ";
        String s_i_sql="insert into superstructure(bridge_no,beam_type,beam_num,crossbeam_type,limit_height,bearing_type,bearing_num,deck_structure,expansion_joint_type,expansion_joint_num) values(?,?,?,?,?,?,?,?,?,?) ";
        try {
            connection= conn_bridge_info.getConnection();
//            preparedStatement=connection.prepareStatement(s_id_sql);
//            preparedStatement=connection.prepareStatement(b_i_sql);
//            preparedStatement.setString(1,information.getId());
//            preparedStatement.setString(2,information.getBi_type());
//            preparedStatement.setString(3,information.getName());
//            preparedStatement.setString(4,information.getArea());
//            preparedStatement.setString(5,information.getLng());
//            preparedStatement.setString(6,information.getLat());
//
//            preparedStatement=connection.prepareStatement(g_i_sql);
//            preparedStatement.setString(1,information.getId());
//            preparedStatement.setString(2,information.getGi_supervision_unit());
//            System.out.println(information.getGi_overall_length());
//            preparedStatement.setString(3,information.getGi_overall_length());
//            preparedStatement.setString(4,information.getGi_overall_width());
//
            preparedStatement=connection.prepareStatement(s_i_sql);
            preparedStatement.setString(1,information.getId());
            preparedStatement.setString(2,information.getSs_bean_type());
            preparedStatement.setString(3,information.getSs_bean_num());
            preparedStatement.setString(4,information.getSs_crossbeam_type());
            preparedStatement.setString(5,information.getSs_limit_height());
            preparedStatement.setString(6,information.getSs_bearing_type());
            preparedStatement.setString(7,information.getSs_bearing_num());
            preparedStatement.setString(8,information.getSs_deck_structure());
            preparedStatement.setString(9,information.getSs_expansion());
            preparedStatement.setString(10,information.getSs_expansion_joint_num());

            int result=preparedStatement.executeUpdate();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,null);
        }
        return 0;
    }

    public int insert_below_i(information information){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
//        String s_id_sql="select bridge_no from bridge_info";
        String b_i_sql="insert into bridge_info(bridge_no,type_no,bridge_name,area,bridge_lng,bridge_lat) values(?,?,?,?,?,?) ";
        String g_i_sql="insert into general_information(bridge_no,supervision_unit,overall_length,overall_width) values(?,?,?,?) ";
        String below_i_sql="insert into belowstructure(bridge_no,pier_type,pier_num,pier_elevation,bank_pier_type,bank_pier_num,bank_pier_elevation)values(?,?,?,?,?,?,?) ";
        try {
            connection= conn_bridge_info.getConnection();
//            preparedStatement=connection.prepareStatement(s_id_sql);
//            preparedStatement=connection.prepareStatement(b_i_sql);
//            preparedStatement.setString(1,information.getId());
//            preparedStatement.setString(2,information.getBi_type());
//            preparedStatement.setString(3,information.getName());
//            preparedStatement.setString(4,information.getArea());
//            preparedStatement.setString(5,information.getLng());
//            preparedStatement.setString(6,information.getLat());
//
//            preparedStatement=connection.prepareStatement(g_i_sql);
//            preparedStatement.setString(1,information.getId());
//            preparedStatement.setString(2,information.getGi_supervision_unit());
//            System.out.println(information.getGi_overall_length());
//            preparedStatement.setString(3,information.getGi_overall_length());
//            preparedStatement.setString(4,information.getGi_overall_width());
//
            preparedStatement=connection.prepareStatement(below_i_sql);
            preparedStatement.setString(1,information.getId());
            preparedStatement.setString(2,information.getBs_pier_type());
            preparedStatement.setString(3,information.getBs_pier_num());
            preparedStatement.setString(4,information.getBs_pier_elevation());
            preparedStatement.setString(5,information.getBs_bank_pier_type());
            preparedStatement.setString(6,information.getBs_bank_pier_num());
            preparedStatement.setString(7,information.getBs_bank_pier_elevation());

            int result=preparedStatement.executeUpdate();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,null);
        }
        return 0;
    }

    public information select(String id){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String b_i_sql="select * from bridge_info where bridge_no= ?";
        String g_i_sql="select * from general_information where bridge_no= ?";
        String s_s_sql="select * from superstructure where bridge_no= ?";
        String b_s_sql="select * from belowstructure where bridge_no= ?";
        information info=null;
        try {
            connection= conn_bridge_info.getConnection();
            preparedStatement=connection.prepareStatement(b_i_sql);
            preparedStatement.setString(1,id);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                info=new information();
                String b_no=resultSet.getString("bridge_no");
                String b_type=resultSet.getString("type_no");
                String b_name=resultSet.getString("bridge_name");
                String b_area=resultSet.getString("area");
                String b_lng=resultSet.getString("bridge_lng");
                String b_lat=resultSet.getString("bridge_lat");
                info.setId(b_no);
                info.setName(b_name);
                info.setBi_type(b_type);
                info.setArea(b_area);
                info.setLng(b_lng);
                info.setLat(b_lat);
            }
            conn_bridge_info.closeAll(connection,preparedStatement,resultSet);

            connection= conn_bridge_info.getConnection();
            preparedStatement=connection.prepareStatement(g_i_sql);
            preparedStatement.setString(1,id);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                String b_supervision=resultSet.getString("supervision_unit");
                String b_o_length=resultSet.getString("overall_length");
                String b_o_width=resultSet.getString("overall_width");
                info.setGi_supervision_unit(b_supervision);
                info.setGi_overall_length(b_o_length);
                info.setGi_overall_width(b_o_width);
            }
            conn_bridge_info.closeAll(connection,preparedStatement,resultSet);

            connection= conn_bridge_info.getConnection();
            preparedStatement=connection.prepareStatement(s_s_sql);
            preparedStatement.setString(1,id);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                String b_beantyp=resultSet.getString("beam_type");
                String b_bean_num=resultSet.getString("beam_num");
                String b_cbeantyp=resultSet.getString("crossbeam_type");
                String b_limit_height=resultSet.getString("limit_height");
                String b_bear_type=resultSet.getString("bearing_type");
                String b_bear_num=resultSet.getString("bearing_num");
                String b_deck_str=resultSet.getString("deck_structure");
                String b_e_jiontt=resultSet.getString("expansion_joint_type");
                String b_e_jiontn=resultSet.getString("expansion_joint_num");

                info.setSs_bean_type(b_beantyp);
                info.setSs_bean_num(b_bean_num);
                info.setSs_crossbeam_type(b_cbeantyp);
                info.setSs_limit_height(b_limit_height);
                info.setSs_bearing_type(b_bear_type);
                info.setSs_bearing_num(b_bear_num);
                info.setSs_deck_structure(b_deck_str);
                info.setSs_expansion(b_e_jiontt);
                info.setSs_expansion_joint_num(b_e_jiontn);
                conn_bridge_info.closeAll(connection,preparedStatement,resultSet);

                connection= conn_bridge_info.getConnection();
                preparedStatement=connection.prepareStatement(b_s_sql);
                preparedStatement.setString(1,id);
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    String b_pt=resultSet.getString("pier_type");
                    String b_pn=resultSet.getString("pier_num");
                    String b_pe=resultSet.getString("pier_elevation");
                    String b_bpt=resultSet.getString("bank_pier_type");
                    String b_bpn=resultSet.getString("bank_pier_num");
                    String b_bpe=resultSet.getString("bank_pier_elevation");
                    info.setBs_pier_type(b_pt);
                    info.setBs_pier_num(b_pn);
                    info.setBs_pier_elevation(b_pe);
                    info.setBs_bank_pier_type(b_bpt);
                    info.setBs_bank_pier_num(b_bpn);
                    info.setBs_bank_pier_elevation(b_bpe);
                }

            }
            conn_bridge_info.closeAll(connection,preparedStatement,resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn_bridge_info.closeAll(connection,preparedStatement,resultSet);
        }
        System.out.println(info);
        return info;
    }
}
