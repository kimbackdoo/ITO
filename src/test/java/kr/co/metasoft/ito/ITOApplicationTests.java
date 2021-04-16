package kr.co.metasoft.ito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ITOApplicationTests {

    @Test
    void contextLoads() {
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        String url = null;
        String id = "metasofthomepage";
        String pw = "ms20170901!";

        try {
            url ="jdbc:mariadb://meta-soft.co.kr:3306/metasofthomepage";
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(url,id,pw);
            System.out.println("DB connection complete");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from tb_notice");

            while(rs.next()){
                String no = rs.getString(1);
                String name = rs.getString(2);
                String grade = rs.getString(3);
                System.out.println(no+", " + name +", "+grade);
            }

            stmt.close();
            conn.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}