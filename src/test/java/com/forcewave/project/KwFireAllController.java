// package com.forcewave.project;

// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class KwFireAllController {

// @Autowired
// DataSource dataSource;

// @PostMapping("/fire-alll")
// public HashMap<String, List<String>> fireAll() throws SQLException {

// // 데이터를 담을 리스트 선언
// List<String> textList = new ArrayList<>();
// List<String> idList = new ArrayList<>();
// List<String> geomList = new ArrayList<>();
// List<String> occrrList = new ArrayList<>();
// List<String> occurdyList = new ArrayList<>();
// List<String> extingDtmList = new ArrayList<>();
// List<String> rqrmnTmList = new ArrayList<>();
// List<String> araNaList = new ArrayList<>();
// List<String> ctpvNmList = new ArrayList<>();
// List<String> sgngNmList = new ArrayList<>();
// List<String> emndnNmList = new ArrayList<>();
// List<String> occcrrRiList = new ArrayList<>();
// List<String> cuseNmList = new ArrayList<>();
// List<String> dmgAreaList = new ArrayList<>();
// List<String> dmgMoneyList = new ArrayList<>();

// // String keyword = "";
// // keyword = searchKeyword;

// HashMap<String, List<String>> gangwonList = new HashMap<>();

// // 연결
// try (Connection connection = dataSource.getConnection()) {

// // 쿼리 전송
// Statement statement = connection.createStatement();
// // String sql = "select id,geom,pnu,a2, addr from gangwon where addr like '"
// +
// // keyword + "'";
// String sql = "select st_astext(geom), id, geom, ufid, bjcd, addr, divi from
// kwemd where addr like '" + "%"
// + keyword + "%"
// + "'";
// // String sql2 = "select st_astext(geom), addr from kwemd where addr like '"
// +
// // "%" + keyword + "%"
// // + "'";
// System.out.println(sql);
// statement.execute(sql);

// // 값을 받음
// ResultSet rs = statement.getResultSet();
// while (rs.next()) {
// // 리스트에 담음
// textList.add(rs.getString("st_astext"));
// idList.add(rs.getString("id"));
// geomList.add(rs.getString("geom"));
// ufidList.add(rs.getString("ufid"));
// bjcdList.add(rs.getString("bjcd"));
// addrList.add(rs.getString("addr"));
// diviList.add(rs.getString("divi"));
// }
// // 맵에 담음
// gangwonList.put("textList", textList);
// gangwonList.put("idList", idList);
// gangwonList.put("geomList", geomList);
// gangwonList.put("ufidList", ufidList);
// gangwonList.put("bjcdList", bjcdList);
// gangwonList.put("addrList", addrList);
// gangwonList.put("diviList", diviList);

// // 확인
// for (int i = 0; i < gangwonList.get("idList").size(); i++) {
// gangwonList.get("textList").get(i);
// gangwonList.get("idList").get(i);
// gangwonList.get("geomList").get(i);
// gangwonList.get("ufidList").get(i);
// gangwonList.get("bjcdList").get(i);
// gangwonList.get("addrList").get(i);
// gangwonList.get("diviList").get(i);
// }
// // model.addAttribute("fireList", fireList);
// System.out.println(gangwonList.toString());
// }

// return gangwonList;

// }

// }
