package co.FastApps.FastChat.Service;

import co.FastApps.FastChat.Dao.AWS_RDS_dao;
import co.FastApps.FastChat.Entity.EndResult;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AWS_Service {
    @Autowired
    @Qualifier("mysql")
    private AWS_RDS_dao aws_rds_dao;

    private void query(ListMultimap<String, String> multiMap, String text, List<List<Map<String, Object>>> result) {
        for (String key : multiMap.keySet()) {
            List<String> value = multiMap.get(key);
            for (String val : value) {
                if (aws_rds_dao.testNotNull(key, val, text) != null)
                    result.add(aws_rds_dao.getInfo(key, val, text));
            }
        }
    }

    public EndResult comprehend(String text) {
        List<Entity> list;
        List<KeyPhrase> keyList;

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("key",
                "key");

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
        System.out.println("ListEntities" + list);


//       TODO logging, exception, errors
        aws_rds_dao.clearRootText();
        ListMultimap<String, String> multiMap = ArrayListMultimap.create();
        List<List<Map<String, Object>>> result = new ArrayList<>();
        for (Entity entity : list) {
            String string = entity.getType();
            switch (string) {
                case "ORGANIZATION":
                    multiMap.put("Companies", "Organization");
                    multiMap.put("Contacts", "Organization");
                    multiMap.put("Calls", "Organization");
                    multiMap.put("Assets", "Organization");
                    multiMap.put("Countries", "Organization");
                    multiMap.put("Leads", "Organization");
                    try {
                        query(multiMap, entity.getText(), result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    multiMap.clear();
                    break;
                case "LOCATION":
                    multiMap.put("Companies", "Location");
                    multiMap.put("Contacts", "Location");
                    multiMap.put("Employees", "Location");
                    multiMap.put("Countries", "City");
                    try {
                        query(multiMap, entity.getText(), result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    multiMap.clear();
                    break;
                case "DATE":
                    multiMap.put("Employees", "Birthday");
                    multiMap.put("Calls", "Date");
                    multiMap.put("Documents", "Date");
                    multiMap.put("Leads", "Date");
                    multiMap.put("Meeting", "Date");
                    multiMap.put("Projects", "Start");
                    multiMap.put("Projects", "End");
                    multiMap.put("Tasks", "Date");
                    multiMap.put("Events", "Date");
                    multiMap.put("Expenses", "Date");
                    try {
                        query(multiMap, entity.getText(), result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    multiMap.clear();
                    break;
                case "QUANTITY":
                    multiMap.put("Expenses", "Cost");
                    try {
                        query(multiMap, entity.getText(), result);

                    } catch (Exception e) {
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
                    try {
                        query(multiMap, entity.getText(), result);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    multiMap.clear();
                    break;
                case "EVENT":
                    multiMap.put("Events", "Name");
                    try {
                        query(multiMap, entity.getText(), result);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    multiMap.clear();
                    break;
                case "COMMERCIAL_ITEM":
                    multiMap.put("Companies", "Name");
                    try {
                        query(multiMap, entity.getText(), result);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    multiMap.clear();
                    break;
                case "TITLE":
                    multiMap.put("Employees", "Title");
                    try {
                        query(multiMap, entity.getText(), result);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    multiMap.clear();
                    break;
                default:
                    System.out.println("didn't go into a switch case");
            }
        }
        return new EndResult(result, aws_rds_dao.getRootText());
    }
}