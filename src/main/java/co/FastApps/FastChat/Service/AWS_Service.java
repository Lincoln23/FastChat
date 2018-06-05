package co.FastApps.FastChat.Service;

import co.FastApps.FastChat.Dao.AWS_RDS_dao;
import co.FastApps.FastChat.Entity.Result;
import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AWS_Service {
    @Autowired
    @Qualifier("mysql")
    AWS_RDS_dao aws_rds_dao;

    public List<List<Map<String, Object>>> comprehend(String text) {
        List<Entity> list;
        //       List<KeyPhrase> keyList;

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
//        DetectKeyPhrasesRequest detectKeyPhrasesRequest = new DetectKeyPhrasesRequest().withText(text)
//                .withLanguageCode("en");
        //Entities Result
        DetectEntitiesResult detectEntitiesResult = comprehendClient.detectEntities(detectEntitiesRequest);
        //KeyPhrases Result
//        DetectKeyPhrasesResult detectKeyPhrasesResult = comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest);

//        keyList = detectKeyPhrasesResult.getKeyPhrases();
        list = detectEntitiesResult.getEntities();


        //removes duplicates from keylist
//        @Deprecated
//        for (int i = 0; i < keyList.size(); i++) {
//            for (int j = 0; j < list.size(); j++) {
//                if (list.get(j).getText().equals(keyList.get(i).getText())) {
//                    keyList.remove(i);
//                }
//            }
//        }
//      System.out.println("KeyList" + keyList);
        System.out.println("ListEntities" + list);


//        Result result = new Result();
//        Map<String,Result> resultMap = new HashMap<>();
//        resultMap.put("COMMERCIAL_ITEM",result);
//        resultMap.put("DATE",result);
//        resultMap.put("EVENT",result);
//        resultMap.put("LOCATION",result);
//        resultMap.put("ORGANIZATION",result);
//        resultMap.put("OTHER",result);
//        resultMap.put("PERSON",result);
//        resultMap.put("COMMERCIAL_ITEM",result);
//        resultMap.put("TITLE",result);
//
//        for (Entity entity : list) {
//            for(Map.Entry<String,Result> entry : resultMap.entrySet()){
//                if(entity.getType().equals(entry.getKey())){
//                    entry.getValue().location(entity.getText());
//                }
//            }
//        }
//        return result.returnList();







        Map<String, String> map = new HashMap<>();
        List<List<Map<String, Object>>> result = new ArrayList<>();
        for (Entity entity : list) {
            String string = entity.getType();
            System.out.println(string);
            switch (string) {
                case "ORGANIZATION":
                    try {
                        map.put("Companies", "Organization");
                        map.put("Contacts", "Organization");
                        map.put("Calls", "Organization");
                        map.put("Assets", "Organization");
                        map.put("Countries", "Organization");
                        map.put("Leads", "Organization");
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()) != null)
                                result.add(aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()));
                        }
                        map.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "LOCATION":
                    try {
                        map.put("Companies", "Location");
                        map.put("Contacts", "Location");
                        map.put("Employees", "Location");
                        map.put("Countries", "City");

                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()) != null)
                                result.add(aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()));
                        }
                        map.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "DATE":
                    try {
                        map.put("Employees", "Birthday");
                        map.put("Calls", "Date");
                        map.put("Documents", "Date");
                        map.put("Leads", "Date");
                        map.put("Meeting", "Date");
                        map.put("Projects", "Start");
                        map.put("Projects", "End");
                        map.put("Tasks", "Date");
                        map.put("Events", "Date");
                        map.put("Expenses", "Date");
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()) != null)
                                result.add(aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()));
                        }
                        map.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "QUANTITY":
                    try {
                        map.put("Expenses", "Cost");
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()) != null)
                                result.add(aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()));
                        }
                        map.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "PERSON":
                    try {
                        map.put("Employees", "Name");
                        map.put("Assets", "Name");
                        map.put("Contacts", "Name");
                        map.put("Calls", "Name");
                        map.put("Leads", "Name");
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()) != null)
                                result.add(aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()));
                        }
                        map.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "EVENT":
                    try {
                        map.put("Events", "Name");
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()) != null)
                                result.add(aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()));
                        }
                        map.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "COMMERCIAL_ITEM":
                    try {
                        map.put("Companies", "Name");
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()) != null)
                                result.add(aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()));
                        }
                        map.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "TITLE":
                    try {
                        map.put("Employees", "Title");
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()) != null)
                                result.add(aws_rds_dao.getInfo(entry.getKey(), entry.getValue(), entity.getText()));
                        }
                        map.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("didn't go into a switch case");
            }
        }
        return result;
    }
}