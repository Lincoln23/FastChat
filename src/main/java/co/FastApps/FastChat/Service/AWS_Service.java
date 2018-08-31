package co.FastApps.FastChat.Service;

import co.FastApps.FastChat.Dao.AWS_RDS_dao;
import co.FastApps.FastChat.Entity.EndResult;
import co.FastApps.FastChat.FastChatApplication;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//TODO change logging file for local use
@Service
public class AWS_Service {
	@Autowired
	@Qualifier("mysql")
	private AWS_RDS_dao aws_rds_dao;

	@Value("${AWS_ACCESS_KEY}")
	private String accessKey;

	@Value("${AWS_SECRET_KEY}")
	private String secretKey;


	private List<String> intersection(List<String> textArray, List<String> columnNames) {
		List<String> list = new ArrayList<>();
		for (String text : columnNames) {
			for (String columnName : textArray) {
				if (text.equalsIgnoreCase(columnName)) {
					list.add(text);
				}
			}
		}
		return list;
	}

	private void query(ListMultimap<String, String> multiMap, String text, List<List<Map<String, Object>>> result, int limit) {
		for (String key : multiMap.keySet()) {
			List<String> value = multiMap.get(key);
			for (String val : value) {
				List<Map<String, Object>> tmp = aws_rds_dao.getInfo(key, val, text);
				if (tmp != null && result.size() < limit) {
					result.add(tmp);
				}
			}
		}
	}

	private void addNewValues(ListMultimap<String, String> multiMap, List<String> textArray) {
		for (String key : multiMap.keySet()) {
			List<String> columnNames = aws_rds_dao.getColumnName(key);
			List<String> commonList = intersection(textArray, columnNames);
			for (String put : commonList) {
				if (!multiMap.containsValue(put)) {
					multiMap.put(key, put);
				}
			}
		}
	}

	public EndResult comprehend(String text, int limit) {
		List<Entity> list;
		List<KeyPhrase> keyList;

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

		AmazonComprehend comprehendClient =
				AmazonComprehendClientBuilder.standard()
						.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
						.withRegion(Regions.US_EAST_1)
						.build();

		// Call detectEntities API
		DetectEntitiesRequest detectEntitiesRequest = new DetectEntitiesRequest().withText(text)
				.withLanguageCode("en");
		// Call detectKeyPhrases API
		DetectKeyPhrasesRequest detectKeyPhrasesRequest = new DetectKeyPhrasesRequest().withText(text)
				.withLanguageCode("en");
		//Entities Result
		DetectEntitiesResult detectEntitiesResult = comprehendClient.detectEntities(detectEntitiesRequest);
		//KeyPhrases Result
		DetectKeyPhrasesResult detectKeyPhrasesResult = comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest);

		keyList = detectKeyPhrasesResult.getKeyPhrases();
		list = detectEntitiesResult.getEntities();

		//removes duplicates from keylist
		for (int i = 0; i < keyList.size(); i++) {
			for (Entity aList : list) {
				if (aList.getText().equals(keyList.get(i).getText())) {
					keyList.remove(i);
				}
			}
		}
		System.out.println("KeyList" + keyList);
		System.out.println("ListEntities" + list + "\n");


		aws_rds_dao.clearRootText();
		ListMultimap<String, String> multiMap = ArrayListMultimap.create();
		List<List<Map<String, Object>>> result = new ArrayList<>();
		List<String> textArray = Arrays.asList(text.split("\\s+"));
		for (Entity entity : list) {
			String string = entity.getType();
			switch (string) {
				case "ORGANIZATION":
					//Put key and values corresponding to table and column
					multiMap.put("Companies", "Organization");
					multiMap.put("Contacts", "Organization");
					multiMap.put("Calls", "Organization");
					multiMap.put("Assets", "Organization");
					multiMap.put("Countries", "Organization");
					multiMap.put("Leads", "Organization");
					addNewValues(multiMap, textArray);
					try {
						query(multiMap, entity.getText(), result, limit);
					} catch (Exception e) {
						FastChatApplication.logger.debug(e.toString());
						e.printStackTrace();
					}
					multiMap.clear();
					break;
				case "LOCATION":
					multiMap.put("Companies", "Location");
					multiMap.put("Contacts", "Location");
					multiMap.put("Employees", "Location");
					multiMap.put("Countries", "City");
					addNewValues(multiMap, textArray);
					try {
						query(multiMap, entity.getText(), result, limit);
					} catch (Exception e) {
						FastChatApplication.logger.debug(e.toString());
						e.printStackTrace();
					}
					multiMap.clear();
					break;
				case "DATE":
					multiMap.put("Employees", "Birthday");
					multiMap.put("Calls", "Date");
					multiMap.put("Documents", "Date");
					multiMap.put("Holidays", "Date");
					multiMap.put("Leads", "Date");
					multiMap.put("Meeting", "Date");
					multiMap.put("Projects", "Start");
					multiMap.put("Projects", "End");
					multiMap.put("Tasks", "Date");
					multiMap.put("Events", "Date");
					multiMap.put("Expenses", "Date");
					addNewValues(multiMap, textArray);
					try {
						query(multiMap, entity.getText(), result, limit);
					} catch (Exception e) {
						FastChatApplication.logger.debug(e.toString());
						e.printStackTrace();
					}
					multiMap.clear();
					break;
				case "QUANTITY":
					String s = entity.getText().replaceAll("\\D+", "");
					multiMap.put("Expenses", "Cost");
					addNewValues(multiMap, textArray);
					try {
						query(multiMap, s, result, limit);

					} catch (Exception e) {
						FastChatApplication.logger.debug(e.toString());
						e.printStackTrace();
					}
					multiMap.clear();
					break;
				case "PERSON":
					multiMap.put("Employees", "Name");
					multiMap.put("Assets", "Name");
					multiMap.put("Contacts", "Name");
					multiMap.put("Calls", "Name");
					multiMap.put("Leads", "Name");
					addNewValues(multiMap, textArray);
					try {
						query(multiMap, entity.getText(), result, limit);

					} catch (Exception e) {
						FastChatApplication.logger.debug(e.toString());
						e.printStackTrace();
					}
					multiMap.clear();
					break;
				case "EVENT":
					multiMap.put("Events", "Name");
					addNewValues(multiMap, textArray);
					try {
						query(multiMap, entity.getText(), result, limit);

					} catch (Exception e) {
						FastChatApplication.logger.debug(e.toString());
						e.printStackTrace();
					}
					multiMap.clear();
					break;
				case "COMMERCIAL_ITEM":
					multiMap.put("Inventory", "Name");
					addNewValues(multiMap, textArray);
					try {
						query(multiMap, entity.getText(), result, limit);

					} catch (Exception e) {
						FastChatApplication.logger.debug(e.toString());
						e.printStackTrace();
					}
					multiMap.clear();
					break;
				case "TITLE":
					multiMap.put("Employees ", "Title");
					addNewValues(multiMap, textArray);
					try {
						query(multiMap, entity.getText(), result, limit);

					} catch (Exception e) {
						FastChatApplication.logger.debug(e.toString());
						e.printStackTrace();
					}
					multiMap.clear();
					break;
				default:
					System.out.println("Entity not detected");
			}
		}
		return new EndResult(result, aws_rds_dao.getRootText());
	}
}