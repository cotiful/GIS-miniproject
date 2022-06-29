package com.forcewave.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Anaylsy {
    @Autowired
    DataSource dataSource;

    @GetMapping("/analyst")
    public HashMap<String, List<String>> emd() throws SQLException {

        // 데이터를 담을 리스트 선언
        List<String> textList = new ArrayList<>();
        List<String> emdList = new ArrayList<>();
        List<String> pointsList = new ArrayList<>();

        HashMap<String, List<String>> analystList = new HashMap<>();

        // 연결
        try (Connection connection = dataSource.getConnection()) {

            // 쿼리 전송
            Statement statement = connection.createStatement();
            String sql = "select ST_asTEXT(table1.mygeom),table1.emdname, table1.pointnumber+table2.resvoirarea+table3.hospitallength+table4.firestationlength+table5.resvoirarea points from "
                    + "(select emdname, mygeom, CASE WHEN f1.forestnumber > 50 THEN 5 "
                    + "WHEN f1.forestnumber > 30 THEN 4 "
                    + "WHEN f1.forestnumber > 10 then 3 "
                    + "when f1.forestnumber > 3 then 2 "
                    + "when f1.forestnumber > 0 then 1 "
                    + "else 0 "
                    + "END AS pointnumber from"
                    + "(select count(*) forestnumber,dh.name emdname, dh.geom mygeom from donghaeemd3857 dh, kw_forestfire_3857 f "
                    + "where St_intersects(dh.geom, f.geom)"
                    + "group by dh.name, dh.geom) f1) table1,"
                    + "(SELECT emdname, CASE WHEN f2.forestlen > 10000 THEN 3"
                    + "WHEN f2.forestlen > 1000 THEN 2"
                    + "WHEN f2.forestlen > 0 THEN 1"
                    + " else 0"
                    + " END AS resvoirarea from "
                    + "(select dh.name emdname, sum(st_length(ST_INTERSECTION(fp.geom, dh.geom))) forestlen from forestpath fp, donghaeemd3857 dh"
                    + "group by dh.name) f2) table2,"
                    + "(SELECT emdname, CASE WHEN f3.hoslen > 10000 THEN 2"
                    + "WHEN f3.hoslen > 5000 THEN 1"
                    + "else 0 "
                    + "END AS hospitallength from"
                    + "(select emd.name emdname, min(ST_distance(ST_centroid(emd.geom), bg.geom)) hoslen from bighospital bg, donghaeemd3857 emd"
                    + "group by emd.name) f3) table3,"
                    + "(SELECT emdname, CASE WHEN f4.firestlen > 10000 THEN 2"
                    + "WHEN f4.firestlen > 5000 THEN 2"
                    + "else 0"
                    + "END AS firestationlength from "
                    + "(select emd.name emdname, min(ST_distance(ST_centroid(emd.geom), fs.geom)) firestlen from firestation3857 fs, donghaeemd3857 emd"
                    + "+group by emd.name) f4) table4,"
                    + "(SElECT emdname, CASE WHEN f5.resvoarea > 5000 THEN 2"
                    + "WHEN f5.resvoarea > 1000 THEN 1"
                    + "else 0"
                    + "END AS resvoirarea from"
                    + "(select emd.name emdname, sum(st_area(ST_INTERSECTION(rs.geom, emd.geom))) resvoarea from resvoir rs, donghaeemd3857 emd"
                    + " group by emd.name)f5) table5"
                    + "where table1.emdname = table2.emdname and table2.emdname = table3.emdname and table1.emdname = table3.emdname and table1.emdname = table4.emdname "
                    + " and table2.emdname = table4.emdname and table3.emdname = table4.emdname"
                    + " and table1.emdname = table5.emdname and table2.emdname = table5.emdname and table3.emdname = table5.emdname"
                    + " and table4.emdname = table5.emdname"
                    + " order by points DESC";
            System.out.println(sql);
            statement.execute(sql);

            // 값을 받음
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                // 리스트에 담음
                textList.add(rs.getString("st_astext"));
                emdList.add(rs.getString("emdname"));
                pointsList.add(rs.getString("points"));
            }
            // 맵에 담음
            analystList.put("textList", textList);
            analystList.put("textList", emdList);
            analystList.put("textList", pointsList);

            // 확인
            for (int i = 0; i < analystList.get("emdList").size(); i++) {
                analystList.get("textList").get(i);
                analystList.get("emdList").get(i);
                analystList.get("pointsList").get(i);
            }
            // model.addAttribute("fireList", fireList);
            System.out.println(analystList.toString());
        }

        return analystList;

    }

}
