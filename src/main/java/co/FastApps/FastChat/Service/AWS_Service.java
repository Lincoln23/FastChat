package co.FastApps.FastChat.Service;

import co.FastApps.FastChat.Dao.AWS_RDS_dao;
import co.FastApps.FastChat.Entity.ResultType;
import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import javax.xml.transform.Result;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Service
public class AWS_Service {

    @Autowired
    @Qualifier("mysql")
    AWS_RDS_dao aws_rds_dao;


    public List<List<ResultType>> comprehend(String text) {
        List<Entity> list;
        List<KeyPhrase> keyList;

//        DefaultAWSCredentialsProviderChain awsCreds = new DefaultAWSCredentialsProviderChain();
//
//        EnvironmentVariableCredentialsProvider env = new EnvironmentVariableCredentialsProvider();
//        env.getCredentials();


        //AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("Acess key",
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
        list = detectEntitiesResult.getEntities();
        for (int i = 0; i < keyList.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getText().equals(keyList.get(i).getText())) {
                    keyList.remove(i);
                }
            }
        }
        System.out.println("ListEntities" + list);
        System.out.println("KeyList" + keyList);
        List<List<ResultType>> result = new ArrayList<>();
        String string = null;
        for (int i = 0; i < list.size(); i++) {
            string = list.get(i).getType();
            System.out.println(string);
            switch (string) {
                case "ORGANIZATION" :
                    try {
                        result.add(aws_rds_dao.getCompanies("Organization",list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "LOCATION":
                    try {
                        result.add(aws_rds_dao.getContact(list.get(i).getText()));
                        result.add(aws_rds_dao.getCompanies("Location",list.get(i).getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
        return result;
    }
}
