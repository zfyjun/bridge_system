package servlet;

import bridge.info_DAO;
import bridge.information;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/b_search")
public class info_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String operation=req.getParameter("b_operation");
        String id=req.getParameter("id");
        if(operation.equals("search")){
            info_DAO generationInfoDao=new info_DAO();
            information gen=generationInfoDao.select(id);
//        System.out.println(stu);
            JSONObject s_o=new JSONObject();
            s_o.put("code",gen);
            PrintWriter pw= resp.getWriter();
            pw.print(s_o);
            pw.close();
        }
        else if(operation.equals("insert")){
            info_DAO infoDao=new info_DAO();
            information info=new information();
            info.setId(req.getParameter("b_no"));
            info.setName(req.getParameter("b_name"));
            info.setBi_type(req.getParameter("b_type"));
            info.setArea(req.getParameter("b_area"));
            info.setLng(req.getParameter("b_lng"));
            info.setLat(req.getParameter("b_lat"));
            info.setGi_overall_length(req.getParameter("b_length"));
            info.setGi_overall_width(req.getParameter("b_width"));
            info.setGi_supervision_unit(req.getParameter("b_gi_supervision_unit"));

            info.setSs_bean_type(req.getParameter("ss_bean_type"));
            info.setSs_bean_num(req.getParameter("ss_bean_num"));
            info.setSs_crossbeam_type(req.getParameter("ss_crossbeam_type"));
            info.setSs_limit_height(req.getParameter("ss_limit_height"));
            info.setSs_bearing_type(req.getParameter("ss_bearing_type"));
            info.setSs_bearing_num(req.getParameter("ss_bearing_num"));
            info.setSs_deck_structure(req.getParameter("ss_deck_structure"));
            info.setSs_expansion(req.getParameter("ss_expansion"));
            info.setSs_expansion_joint_num(req.getParameter("ss_expansion_joint_num"));

            info.setBs_pier_type(req.getParameter("bs_pier_type"));
            info.setBs_pier_num(req.getParameter("bs_pier_num"));
            info.setBs_pier_elevation(req.getParameter("bs_pier_elevation"));
            info.setBs_bank_pier_type(req.getParameter("bs_bank_pier_type"));
            info.setBs_bank_pier_num(req.getParameter("bs_bank_pier_num"));
            info.setBs_bank_pier_elevation(req.getParameter("bs_bank_pier_elevation"));

            System.out.println(info);
            infoDao.insert_b_i(info);
            infoDao.insert_g_i(info);
            infoDao.insert_s_i(info);
            infoDao.insert_below_i(info);

            JSONObject s_o=new JSONObject();
            String str="true";
            s_o.put("code",str);
            PrintWriter pw= resp.getWriter();
            pw.print(s_o);
            pw.close();
        }else if(operation.equals("delete")){
            info_DAO infoDao=new info_DAO();
            String delete_id=req.getParameter("id");

            infoDao.delete_b_i(delete_id);
            infoDao.delete_g_i(delete_id);
            infoDao.delete_s_i(delete_id);
            infoDao.delete_below_i(delete_id);

            JSONObject s_o=new JSONObject();
            String str="true";
            s_o.put("code",str);
            PrintWriter pw= resp.getWriter();
            pw.print(s_o);
            pw.close();
        }
    }
}
