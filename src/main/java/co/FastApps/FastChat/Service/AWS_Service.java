package co.FastApps.FastChat.Service;

import co.FastApps.FastChat.Dao.AWS_RDS_dao;
import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;
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
    AWS_RDS_dao aws_rds_dao;


    public List<List<Map<String, Object>>> comprehend(String text) {
        List<Entity> list;
        List<KeyPhrase> keyList;

//        DefaultAWSCredentialsProviderChain awsCreds = new DefaultAWSCredentialsProviderChain();
//
//        EnvironmentVariableCredentialsProvider env = new EnvironmentVariableCredentialsProvider();
//        env.getCredentials();


        //AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("access key",
                "secret key");

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
        //removes duplicates from keylist
        list = detectEntitiesResult.getEntities();
        for (int i = 0; i < keyList.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getText().equals(keyList.get(i).getText())) {
                    keyList.remove(i);
                }
            }
        }
        //use muti demsional array
        //use map interface entity is the key and the information are the values
        System.out.println("ListEntities" + list);
        System.out.println("KeyList" + keyList);
        List<List<Map<String, Object>>> result = new ArrayList<>();
        String string = null;
        for (int i = 0; i < list.size(); i++) {
            string = list.get(i).getType();
            System.out.println(string);
            switch (string) {
                case "ORGANIZATION":
                    try {
                        System.out.println("In Organiztion");

                        if (aws_rds_dao.getInfo("Companies", "Organization", list.get(i).getText()) != null)
                            result.add(aws_rds_dao.getInfo("Companies", "Organization", list.get(i).getText()));

                        if (aws_rds_dao.getInfo("Companies", "Organization", list.get(i).getText()) != null)
                            result.add(aws_rds_dao.getInfo("Companies", "Organization", list.get(i).getText()));

                        if (aws_rds_dao.getInfo("Contacts", "Organization", list.get(i).getText()) != null)
                            result.add(aws_rds_dao.getInfo("Contacts", "Organization", list.get(i).getText()));

                        result.add(aws_rds_dao.getInfo("Calls", "Organization", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Assets", "Organization", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Countries", "Organization", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Leads", "Organization", list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "LOCATION":
                    try {
                        System.out.println("In LOCATION");
                        result.add(aws_rds_dao.getInfo("Companies", "Location", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Contacts", "Location", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Employees", "Location", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Countries", "City", list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "DATE":
                    try {
//                        TODO Fix timezone
                        System.out.println("In DATE");
                        result.add(aws_rds_dao.getInfo("Employees", "Birthday", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Calls", "Date", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Documents", "Date", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Leads", "Date", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Meeting", "Date", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Projects", "Start", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Projects", "End", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Tasks", "Date", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Events", "Date", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Expenses", "Date", list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "QUANTITY":
                    try {
                        System.out.println("In QUANTITY");
                        result.add(aws_rds_dao.getInfo("Expenses", "Cost", list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "PERSON":
                    try {
                        System.out.println("In PERSON");
                        result.add(aws_rds_dao.getInfo("Employees", "Name", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Assets", "Name", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Contacts", "Name", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Calls", "Name", list.get(i).getText()));
                        result.add(aws_rds_dao.getInfo("Leads", "Name", list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "EVENT":
                    try {
                        System.out.println("In EVENT");
                        result.add(aws_rds_dao.getInfo("Events", "Name", list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "COMMERCIAL_ITEM":
                    try {
                        System.out.println("In COMMERCIAL_ITEM");
                        result.add(aws_rds_dao.getInfo("Companies", "Name", list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "TITLE":
                    try {
                        System.out.println("In TITLE");
                        result.add(aws_rds_dao.getInfo("Employees", "Title", list.get(i).getText()));
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
