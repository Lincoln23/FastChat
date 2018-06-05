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

    public List<Map<String, Object>> getInfo(String table, String cName, String name) {
        final String sql = "SELECT * FROM " + table + " WHERE " + cName + " = ?";
        final List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, name);
        if (result.isEmpty()) {
            return null;
        }

        for (int i = 0; i < result.size(); i++) {
            String s = null;
            switch (table) {
                case "Assets":
                    s = "Name: " + result.get(i).get("Name") + "; Organization: " + result.get(i).get("Organization") +
                            "; Description: " + result.get(i).get("Description");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Calls":
                    s = "Name: " + result.get(i).get("Name") + "; Date: " + result.get(i).get("Date") +
                            "; Organization: " + result.get(i).get("Organization");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Companies":
                    s = "Organization: " + result.get(i).get("Organization") + "; Location: " + result.get(i).get
                            ("Location");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Contacts":
                    s = "Name: " + result.get(i).get("Name") + "; Organization: " + result.get(i).get("Organization") +
                            "; Phone: " + result.get(i).get("Phone") + "; Email: " + result.get(i).get
                            ("Email");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Countries":
                    s = "Organization: " + result.get(i).get("Organization") + "; Country: " + result.get(i).get
                            ("Location");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Documents":
                    s = "Type: " + result.get(i).get("Type") + "; Date: " + result.get(i).get
                            ("Date");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Employees":
                    s = "Name: " + result.get(i).get("Name") + "; Phone: " + result.get(i).get("Phone") +
                            "; Email: " + result.get(i).get("Email") + "; Employee id: " + result.get(i).get
                            ("Employee-id");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Events":
                    s = "Name: " + result.get(i).get("Name") + "; Date: " + result.get(i).get("Date") +
                            "; Description: " + result.get(i).get("Description");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Expenses":
                    s = "Name: " + result.get(i).get("Name") + "; Cost: " + result.get(i).get("Cost") +
                            "; Description: " + result.get(i).get("Description");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Holidays":
                    s = "Name: " + result.get(i).get("Name") + "; Date: " + result.get(i).get("Date") +
                            "; Description: " + result.get(i).get("Description");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Leads":
                    s = "Name: " + result.get(i).get("Name") + "; Organization: " + result.get(i).get("Organization") +
                            "; Description: " + result.get(i).get("Description");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Meetings":
                    s = "Name: " + result.get(i).get("Name") + "; Date: " + result.get(i).get("Date") +
                            " Description: " + result.get(i).get("Description");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Projects":
                    s = "Name: " + result.get(i).get("Name") + "; Type: " + result.get(i).get("Type") +
                            "; Start-date: " + result.get(i).get("Start") + "; End-date: " + result.get(i).get("End");
                    result.get(i).put("Plain Text", s);
                    break;
                case "Tasks":
                    s = "Name: " + result.get(i).get("Name") + "; Date: " + result.get(i).get("Date") +
                            "; Description: " + result.get(i).get("Description");
                    result.get(i).put("Plain Text", s);
                    break;
                default:
                    s = "Cannot create a plain sentence";
                    result.get(i).put("Error", s);
            }
            result.get(i).put("Table", table);
        }

        return result;
    }
}