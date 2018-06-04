package co.FastApps.FastChat.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository("mysql")
public class AWS_RDS_dao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

        public List<Map<String, Object>> getInfo(String table, String Type, String name) {
        final String sql = "SELECT * FROM " + table + " WHERE " + Type + " = ?";
        System.out.println(sql);
        final List<Map<String, Object>> result = jdbcTemplate.queryForList(sql,name);
        if (result.isEmpty()){
            return  null;
        }
        return result;
    }

//    public List<Map<String, Object>> getCompanies(String Type, String name) {
//        final String sql = "SELECT * FROM Companies WHERE " + Type + " = + ?";
//        final List<Map<String, Object>> result = jdbcTemplate.queryForList(sql,name);
//        return result;
//    }
//
//    public List<Map<String, Object>> getContacts(String Type, String name) {
//        final String sql = "SELECT * FROM Contacts WHERE" + Type + " = ?";
//        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql,name);
//        return result;
//    }
//
//    public List<Map<String, Object>> getEmployees(String Type, String name) {
//        final String sql = "SELECT * FROM Employees WHERE" + Type + " = ?";
//        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql,name);
//        return result;
//    }

}
