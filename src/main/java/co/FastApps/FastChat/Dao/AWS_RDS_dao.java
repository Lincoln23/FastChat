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
	private StringBuilder rootText = new StringBuilder();

	//builds the root text message
	public String getRootText() {
		return this.rootText.toString();
	}

	//clears the root text message
	public void clearRootText() {
		rootText.setLength(0);
	}

	//check if the results return is null from Database
	public String testNotNull(String table, String cName, String name) {
		final String sql = "SELECT * FROM " + table + " WHERE " + cName + " = ?";
		final List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, name);
		if (result.isEmpty()) {
			return null;
		}
		return "worked";
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
					aResult.put("Plain Text", s);

					rootText.append("The Asset is ").append(aResult.get("Name")).append(" and it says: ").append(aResult.get("Description")).append(".");
					break;
				case "Calls":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							"; Organization: " + aResult.get("Organization");
					aResult.put("Plain Text", s);

					rootText.append("The call you have is with: ").append(aResult.get("Name")).append(" from ").append(aResult.get("Organization")).append(" at ").append(aResult.get("Date")).append(".");
					break;
				case "Companies":
					s = "Organization: " + aResult.get("Organization") + "; Location: " + aResult.get("Location");
					aResult.put("Plain Text", s);

					rootText.append("The company is: ").append(aResult.get("Organization")).append(".");
					break;
				case "Contacts":
					s = "Name: " + aResult.get("Name") + "; Organization: " + aResult.get("Organization") +
							"; Phone: " + aResult.get("Phone") + "; Email: " + aResult.get
							("Email");
					aResult.put("Plain Text", s);

					rootText.append("The Contact info I found is: ").append(aResult.get("Name")).append(", You can contact").append(" ").append("him/her at ").append(aResult.get("Phone")).append(" and by email at ").append(aResult.get("Email")).append(".");
					break;
				case "Countries":
					s = "Organization: " + aResult.get("Organization") + "; Country: " + aResult.get
							("Location");
					aResult.put("Plain Text", s);

					rootText.append("The Country for ").append(aResult.get("Organization")).append(" is located in ").append(aResult.get("Location")).append(".");
					break;
				case "Documents":
					s = "Type: " + aResult.get("Type") + "; Date: " + aResult.get
							("Date");
					aResult.put("Plain Text", s);

					rootText.append("The Document you have is of type: ").append(aResult.get("Type")).append(".");
					break;
				case "Employees":
					s = "Name: " + aResult.get("Name") + "; Phone: " + aResult.get("Phone") +
							"; Email: " + aResult.get("Email") + "; Employee id: " + aResult.get
							("Employee-id");
					aResult.put("Plain Text", s);

					rootText.append("The Employee is: ").append(aResult.get("Name")).append(" his/her id is ").append(aResult.get("Employee-id")).append(".");
					break;
				case "Events":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							"; Description: " + aResult.get("Description");
					aResult.put("Plain Text", s);

					rootText.append("The event upcoming is: ").append(aResult.get("Name")).append(" on ").append(aResult.get
							("Date")).append(".");
					break;
				case "Inventory":
					s = "Name: " + aResult.get("Name") + "; Date purchased: " + aResult.get("Date purchased") +
							"; Quantity: " + aResult.get("Quantity");
					aResult.put("Plain Text", s);

					rootText.append("The Inventory for: ").append(aResult.get("Name")).append(" is ").append(aResult.get
							("Quantity")).append(".");
					break;
				case "Expenses":
					s = "Name: " + aResult.get("Name") + "; Cost: " + aResult.get("Cost") +
							"; Description: " + aResult.get("Description");
					aResult.put("Plain Text", s);

					rootText.append("The expense is: ").append(aResult.get("Name")).append(", it cost $").append(aResult
							.get("Cost")).append(".");
					break;
				case "Holidays":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							"; Description: " + aResult.get("Description");
					aResult.put("Plain Text", s);

					rootText.append("The holiday for: ").append(aResult.get("Name")).append(".");
					break;
				case "Leads":
					s = "Name: " + aResult.get("Name") + "; Organization: " + aResult.get("Organization") +
							"; Description: " + aResult.get("Description");
					aResult.put("Plain Text", s);
					rootText.append("The Lead is: ").append(aResult.get("Name")).append(" from ").append(aResult.get
							("Organization")).append(".");
					break;
				case "Meetings":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							" Description: " + aResult.get("Description");
					aResult.put("Plain Text", s);

					rootText.append("Your upcoming meeting is: ").append(aResult.get("Name")).append(" it is about:").append(aResult.get("Description")).append(".");
					break;
				case "Projects":
					s = "Name: " + aResult.get("Name") + "; Type: " + aResult.get("Type") +
							"; Start-date: " + aResult.get("Start") + "; End-date: " + aResult.get("End");
					aResult.put("Plain Text", s);

					rootText.append("Your upcoming project is: ").append(aResult.get("Name")).append(".");
					break;
				case "Tasks":
					s = "Name: " + aResult.get("Name") + "; Date: " + aResult.get("Date") +
							"; Description: " + aResult.get("Description");
					aResult.put("Plain Text", s);

					rootText.append("Your current task is: ").append(aResult.get("Name")).append(".");
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