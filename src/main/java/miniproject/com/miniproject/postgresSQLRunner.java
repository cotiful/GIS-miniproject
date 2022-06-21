package miniproject.com.miniproject;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class postgresSQLRunner implements ApplicationRunner {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()){
            System.out.println(dataSource.getClass());
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());

            // Statement statement = connection.createStatement();
            // String sql = "CREATE TABLE t_product2(product_no INTEGER NOT NULL, product_name VARCHAR(255), PRIMARY KEY (product_no))";
            // statement.executeUpdate(sql);
            // String sql2 = "select * from t_product";
            // statement.executeUpdate(sql2);
        }
        jdbcTemplate.execute("INSERT INTO t_product2 VALUES (1, 'Big shirt')");
    }
}