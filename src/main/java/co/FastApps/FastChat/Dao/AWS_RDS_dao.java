package co.FastApps.FastChat.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("mysql")
public class AWS_RDS_dao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getInfo(String table, String cName, String name) {
        final String sql = "SELECT * FROM " + table + " WHERE " + cName + " = ?";
        final List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, name);
        if (result.isEmpty()) {
            return null;
        }

        for (int i = 0; i < result.size(); i++) {
            switch(table){
                case "Employees":
                    String s = "Name: " + result.get(i).get("Name") + " Phone: " + result.get(i).get("Phone") +
                            " Email: " + result.get(i).get("Email") + " Employee id: " + result.get(i).get
                            ("Employee-id");
                    result.get(i).put("Plain Text", s);
            }
            result.get(i).put("Table", table);
        }

        return result;
    }
}
