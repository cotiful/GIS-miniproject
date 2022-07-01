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
public class Calculation {
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
                    + " (select emdname, mygeom, CASE WHEN f1.forestnumber > 50 THEN 5 "
                    + " WHEN f1.forestnumber > 30 THEN 4 "
                    + " WHEN f1.forestnumber > 10 then 3 "
                    + " when f1.forestnumber > 3 then 2 "
                    + " when f1.forestnumber > 0 then 1 "
                    + " else 0 "
                    + " END AS pointnumber from"
                    + " (select count(*) forestnumber,dh.name emdname, dh.geom mygeom from donghaeemd3857 dh, kw_forestfire_3857 f "
                    + " where St_intersects(dh.geom, f.geom)"
                    + " group by dh.name, dh.geom) f1) table1,"
                    + " (SELECT emdname, CASE WHEN f2.forestlen > 10000 THEN 3"
                    + " WHEN f2.forestlen > 1000 THEN 2"
                    + " WHEN f2.forestlen > 0 THEN 1"
                    + " else 0"
                    + " END AS resvoirarea from "
                    + " (select dh.name emdname, sum(st_length(ST_INTERSECTION(fp.geom, dh.geom))) forestlen from forestpath fp, donghaeemd3857 dh"
                    + " group by dh.name) f2) table2,"
                    + " (SELECT emdname, CASE WHEN f3.hoslen > 10000 THEN 2"
                    + " WHEN f3.hoslen > 5000 THEN 1"
                    + " else 0 "
                    + " END AS hospitallength from"
                    + " (select emd.name emdname, min(ST_distance(ST_centroid(emd.geom), bg.geom)) hoslen from bighospital bg, donghaeemd3857 emd"
                    + " group by emd.name) f3) table3,"
                    + " (SELECT emdname, CASE WHEN f4.firestlen > 10000 THEN 2"
                    + " WHEN f4.firestlen > 5000 THEN 2"
                    + " else 0"
                    + " END AS firestationlength from "
                    + " (select emd.name emdname, min(ST_distance(ST_centroid(emd.geom), fs.geom)) firestlen from firestation3857 fs, donghaeemd3857 emd"
                    + " group by emd.name) f4) table4,"
                    + " (SElECT emdname, CASE WHEN f5.resvoarea > 5000 THEN 2"
                    + " WHEN f5.resvoarea > 1000 THEN 1"
                    + " else 0"
                    + " END AS resvoirarea from"
                    + " (select emd.name emdname, sum(st_area(ST_INTERSECTION(rs.geom, emd.geom))) resvoarea from resvoir rs, donghaeemd3857 emd"
                    + " group by emd.name)f5) table5"
                    + " where table1.emdname = table2.emdname and table2.emdname = table3.emdname and table1.emdname = table3.emdname and table1.emdname = table4.emdname "
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
            analystList.put("emdList", emdList);
            analystList.put("pointsList", pointsList);

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

    // 1.동해시에서 산불이 가장 많이 나는 곳을 파악함 읍,면,동 기준
    @GetMapping("/forest-fire")
    public HashMap<String, List<String>> forestFire() throws SQLException {

        // 데이터를 담을 리스트 선언
        List<String> emdList = new ArrayList<>();
        List<String> forestNumberList = new ArrayList<>();

        HashMap<String, List<String>> analystList = new HashMap<>();

        // 연결
        try (Connection connection = dataSource.getConnection()) {

            // 쿼리 전송
            Statement statement = connection.createStatement();
            String sql = "select emdname, forestnumber from "
                    + " (select count(*) forestnumber,dh.name emdname from donghaeemd3857 dh, kw_forestfire_3857 f"
                    + " where St_intersects(dh.geom, f.geom)"
                    + " group by dh.name) f1"
                    + " order by forestnumber desc;";
            System.out.println(sql);
            statement.execute(sql);

            // 값을 받음
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                // 리스트에 담음
                emdList.add(rs.getString("emdname"));
                forestNumberList.add(rs.getString("forestnumber"));
            }
            // 맵에 담음

            analystList.put("emdList", emdList);
            analystList.put("forestNumberList", forestNumberList);

            // 확인
            for (int i = 0; i < analystList.get("emdList").size(); i++) {
                analystList.get("emdList").get(i);
                analystList.get("forestNumberList").get(i);
            }
            // model.addAttribute("fireList", fireList);
            System.out.println(analystList.toString());
        }

        return analystList;
    }

    // 등산로가 많이 중첩된 길이가 가장 많은 곳
    @GetMapping("/forest-path")
    public HashMap<String, List<String>> forestPath() throws SQLException {

        // 데이터를 담을 리스트 선언
        List<String> emdList = new ArrayList<>();
        List<String> forestPathList = new ArrayList<>();

        HashMap<String, List<String>> analystList = new HashMap<>();

        // 연결
        try (Connection connection = dataSource.getConnection()) {

            // 쿼리 전송
            Statement statement = connection.createStatement();
            String sql = "SELECT emdname, round((forestlen::numeric),0) forestlen from"
                    + " (select dh.name emdname, sum(st_length(ST_INTERSECTION(fp.geom, dh.geom))) forestlen from forestpath fp, donghaeemd3857 dh"
                    + " group by dh.name) f2"
                    + " order by forestlen desc;";

            statement.execute(sql);

            // 값을 받음
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                // 리스트에 담음
                emdList.add(rs.getString("emdname"));
                forestPathList.add(rs.getString("forestlen"));
            }
            // 맵에 담음

            analystList.put("emdList", emdList);
            analystList.put("forestPathList", forestPathList);

            // 확인
            for (int i = 0; i < analystList.get("emdList").size(); i++) {
                analystList.get("emdList").get(i);
                analystList.get("forestPathList").get(i);
            }
            // model.addAttribute("fireList", fireList);
            System.out.println(analystList.toString());
        }

        return analystList;
    }

    // 병원과의 거리
    @GetMapping("/hospital")
    public HashMap<String, List<String>> hospital() throws SQLException {

        // 데이터를 담을 리스트 선언
        List<String> emdList = new ArrayList<>();
        List<String> hospitalList = new ArrayList<>();

        HashMap<String, List<String>> analystList = new HashMap<>();

        // 연결
        try (Connection connection = dataSource.getConnection()) {

            // 쿼리 전송
            Statement statement = connection.createStatement();
            String sql = "SELECT emdname, round((hoslen::numeric),0) hoslen from"
                    + " (select emd.name emdname, min(ST_distance(ST_centroid(emd.geom), bg.geom)) hoslen from bighospital bg, donghaeemd3857 emd"
                    + " group by emd.name) f3"
                    + " order by hoslen desc;";

            statement.execute(sql);

            // 값을 받음
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                // 리스트에 담음
                emdList.add(rs.getString("emdname"));
                hospitalList.add(rs.getString("hoslen"));
            }
            // 맵에 담음

            analystList.put("emdList", emdList);
            analystList.put("hospitalList", hospitalList);

            // 확인
            for (int i = 0; i < analystList.get("emdList").size(); i++) {
                analystList.get("emdList").get(i);
                analystList.get("hospitalList").get(i);
            }

            System.out.println(analystList.toString());
        }

        return analystList;
    }

    // 소방서와의 거리
    @GetMapping("/firestation")
    public HashMap<String, List<String>> fireStation() throws SQLException {

        // 데이터를 담을 리스트 선언
        List<String> emdList = new ArrayList<>();
        List<String> fireStationList = new ArrayList<>();

        HashMap<String, List<String>> analystList = new HashMap<>();

        // 연결
        try (Connection connection = dataSource.getConnection()) {

            // 쿼리 전송
            Statement statement = connection.createStatement();
            String sql = "SELECT emdname, round((firestlen::numeric),0) firestlen from"
                    + " (select emd.name emdname, min(ST_distance(ST_centroid(emd.geom), fs.geom)) firestlen from firestation3857 fs, donghaeemd3857 emd"
                    + " group by emd.name) f4"
                    + " order by firestlen desc;";

            statement.execute(sql);

            // 값을 받음
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                // 리스트에 담음
                emdList.add(rs.getString("emdname"));
                fireStationList.add(rs.getString("firestlen"));
            }
            // 맵에 담음

            analystList.put("emdList", emdList);
            analystList.put("fireStationList", fireStationList);

            // 확인
            for (int i = 0; i < analystList.get("emdList").size(); i++) {
                analystList.get("emdList").get(i);
                analystList.get("fireStationList").get(i);
            }

            System.out.println(analystList.toString());
        }

        return analystList;
    }

    // 저수지를 얼마나 확보하고 있나
    @GetMapping("/resv")
    public HashMap<String, List<String>> resvoir() throws SQLException {

        // 데이터를 담을 리스트 선언
        List<String> emdList = new ArrayList<>();
        List<String> resvoiList = new ArrayList<>();

        HashMap<String, List<String>> analystList = new HashMap<>();

        // 연결
        try (Connection connection = dataSource.getConnection()) {

            // 쿼리 전송
            Statement statement = connection.createStatement();
            String sql = "SElECT emdname, round((resvoarea::numeric),0) resvoarea from"
                    + " (select emd.name emdname, sum(st_area(ST_INTERSECTION(rs.geom, emd.geom))) resvoarea from resvoir rs, donghaeemd3857 emd"
                    + " group by emd.name)f5"
                    + " order by resvoarea desc;";

            statement.execute(sql);

            // 값을 받음
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                // 리스트에 담음
                emdList.add(rs.getString("emdname"));
                resvoiList.add(rs.getString("resvoarea"));
            }
            // 맵에 담음

            analystList.put("emdList", emdList);
            analystList.put("resvoiList", resvoiList);

            // 확인
            for (int i = 0; i < analystList.get("emdList").size(); i++) {
                analystList.get("emdList").get(i);
                analystList.get("resvoiList").get(i);
            }

            System.out.println(analystList.toString());
        }

        return analystList;
    }

}