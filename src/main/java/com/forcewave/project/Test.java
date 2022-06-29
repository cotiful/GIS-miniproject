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
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    DataSource dataSource;

    @lombok.NonNull
    @GetMapping("/testcode")
    public HashMap<String, List<String>> test() throws SQLException {

        // 데이터를 담을 리스트 선언
        List<String> textList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        List<String> addrList = new ArrayList<>();

        HashMap<String, List<String>> gangwonList = new HashMap<>();

        // 연결
        try (Connection connection = dataSource.getConnection()) {

            // 쿼리 전송
            Statement statement = connection.createStatement();
            // String sql = "select id,geom,pnu,a2, addr from gangwon where addr like '" +
            // keyword + "'";
            String sql = "select st_astext(geom), id, addr from kwemd ";
            System.out.println(sql);
            statement.execute(sql);

            // 값을 받음
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                // 리스트에 담음
                textList.add(rs.getString("st_astext"));
                idList.add(rs.getString("id"));
                addrList.add(rs.getString("addr"));

            }
            // 맵에 담음
            gangwonList.put("idList", idList);
            gangwonList.put("textList", textList);
            gangwonList.put("addrList", addrList);

            // 확인
            for (int i = 0; i < gangwonList.get("idList").size(); i++) {

                gangwonList.get("idList").get(i);
                gangwonList.get("textList").get(i);
                gangwonList.get("addrList").get(i);
            }
            // model.addAttribute("fireList", fireList);
            System.out.println(gangwonList.toString());
        }

        return gangwonList;

    }

}
