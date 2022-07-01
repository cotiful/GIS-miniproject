package com.forcewave.project.testcode;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtestCont {

        @Autowired
        @PersistenceContext
        private EntityManager entityManager;

        @Autowired
        DataSource dataSource;

        @GetMapping("/plzplz")
        public List<ReseponDto> findAllDtos() {

                StringBuffer sb = new StringBuffer();
                sb.append("SELECT id,mega_nm from bighospital");

                Query query = entityManager.createNativeQuery(sb.toString(), ReseponDto.class);
                ReseponDto dto = new ReseponDto();

                System.out.println(query.getResultList());
                List<ReseponDto> list = query.getResultList();
                for (ReseponDto reseponDto : list) {
                        System.out.println(reseponDto.getMegaNm());
                }
                // JpaResultMapper mapper = new JpaResultMapper();
                // List<ReseponDto> rese = mapper.list(query, ReseponDto.class);
                return null;
        }
}