package co.FastApps.FastChat.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository("mysql")
public class AWS_RDS_dao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private StringBuilder rootText = new StringBuilder();

	//builds the root text message
	public String getRootText() {
		return this.rootText.toString();
	}

	//clears the root text message
	public void clearRootText() {
		rootText.setLength(0);
	}

	public List<String> getColumnName(String table) {
		final String sql = "SELECT COLUMN_NAME FROM information_schema.columns WHERE table_schema='ChatBotDevDb' AND " +
				"table_name=?";
		final List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, table);
		System.out.println(result);
		List<String> columnName = new ArrayList<>();
		for (Map<String, Object> map : result) {
			columnName.add((String) map.get("COLUMN_NAME"));
		}
		return columnName;
	}

	//query the database for results and build a shorten string message from the data
	public List<Map<String, Object>> getInfo(String table, String cName, String name) {
		final String sql = "SELECT * FROM " + table + " WHERE " + cName + " = ?";
		final List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, name);
		if (result.isEmpty()) {
			return null;
		}
		for (Map<String, Object> aResult : result) {
			switch (table) {
				case "Assets":
					String s = "Name: " + aResult.get("Name") + "; Organization: " + aResult.get("Organization") +
							"; Description: " + aResult.get("Description");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Assets: ").append(aResult.get("Name")).append(" with ")
							.append(aResult.get("Organization")).append(" with the following description: ").append(aResult.get("Description"))
							.append(". ");
					break;
				case "Calls":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							"; Organization: " + aResult.get("Organization");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Calls: ").append(aResult.get("Name")).append(" with ")
							.append(aResult.get("Organization")).append(" at ").append(aResult.get("Date")).append(". ");
					break;
				case "Companies":
					s = "Organization: " + aResult.get("Organization") + "; Location: " + aResult.get("Location");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Companies: ").append(aResult.get("Organization"))
							.append(" in ").append(aResult.get("Location")).append(". ");
					break;
				case "Contacts":
					s = "Name: " + aResult.get("Name") + "; Organization: " + aResult.get("Organization") +
							"; Phone: " + aResult.get("Phone") + "; Email: " + aResult.get("Email");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Contacts: ").append(aResult.get("Name"))
							.append(" at ").append(aResult.get("Phone"))
							.append(" and at ").append(aResult.get("Email")).append(". ");
					break;
				case "Countries":
					s = "Organization: " + aResult.get("Organization") + "; Country: " + aResult.get("Location");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Countries: ").append(aResult.get("Organization"))
							.append(" located in ").append(aResult.get("City")).append(", ")
							.append(aResult.get("Location")).append(". ");
					break;
				case "Documents":
					s = "Type: " + aResult.get("Type") + "; Date: " + aResult.get
							("Date");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Documents: ").append(aResult.get("Name"))
							.append(" of type ").append(aResult.get("Type"))
							.append(" on date ").append(aResult.get("Date")).append(". ");
					break;
				case "Employees":
					s = "Name: " + aResult.get("Name") + "; Phone: " + aResult.get("Phone") +
							"; Email: " + aResult.get("Email") + "; Employee id: " + aResult.get
							("Employee-id");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Employees: ").append(aResult.get("Name"))
							.append(" id #").append(aResult.get("Employee-id"))
							.append(" at ").append(aResult.get("Email")).append(" and at ")
							.append(aResult.get("Phone")).append(". ");
					break;
				case "Events":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							"; Description: " + aResult.get("Description");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Events: ").append(aResult.get("Name"))
							.append(" on ").append(aResult.get("Date")).append(". ");
					break;
				case "Inventory":
					s = "Name: " + aResult.get("Name") + "; Date purchased: " + aResult.get("Date purchased") +
							"; Quantity: " + aResult.get("Quantity");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Inventory: ").append(aResult.get("Name"))
							.append(" and quantity ").append(aResult.get("Quantity")).append(". ");
					break;
				case "Expenses":
					s = "Name: " + aResult.get("Name") + "; Cost: " + aResult.get("Cost") +
							"; Description: " + aResult.get("Description");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in expenses: ID #").append(aResult.get("ID"))
							.append(" it cost $").append(aResult.get("Cost")).append(" with the description: ")
							.append(aResult.get("Description")).append(". ");
					break;
				case "Holidays":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							"; Description: " + aResult.get("Description");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Holidays: ").append(aResult.get("Name")).append(". ");
					break;
				case "Leads":
					s = "Name: " + aResult.get("Name") + "; Organization: " + aResult.get("Organization") +
							"; Description: " + aResult.get("Description");
					aResult.put("Plain Text", s);
					rootText.append("Here is what I found in Leads: ").append(aResult.get("Name"))
							.append(" from ").append(aResult.get("Organization")).append(". ");
					break;
				case "Meetings":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							" Description: " + aResult.get("Description");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Meetings: ").append(aResult.get("Name"))
							.append(" with the description: ").append(aResult.get("Description")).append(". ");
					break;
				case "Projects":
					s = "Name: " + aResult.get("Name") + "; Type: " + aResult.get("Type") +
							"; Start-date: " + aResult.get("Start") + "; End-date: " + aResult.get("End");
					aResult.put("Plain Text", s);

					rootText.append("Here is what I found in Projects: ").append(aResult.get("Name")).append(". ");
					break;
				case "Tasks":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							"; Description: " + aResult.get("Description");
					aResult.put("PlainText", s);

					rootText.append("Here is what I found in Tasks: ").append(aResult.get("Name")).append(". ");
					break;
				default:
					s = "Cannot create a plain sentence";
					aResult.put("Error", s);
			}
			//puts the table name in the object
			aResult.put("Table", table);
		}
		return result;
	}
}