package co.FastApps.FastChat.Service;

import co.FastApps.FastChat.Dao.AWS_RDS_dao;
import co.FastApps.FastChat.Entity.ResultType;
import co.FastApps.FastChat.Entity.TwoLists;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class AWS_Service {

    @Autowired
    @Qualifier("mysql")
    AWS_RDS_dao aws_rds_dao;


    public List<ResultType> getEverything() {
        return aws_rds_dao.getAll();
    }


    public List<ResultType> comprehend(String text) {
        List<Entity> list;
        List<KeyPhrase> keyList;
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

        AmazonComprehend comprehendClient =
                AmazonComprehendClientBuilder.standard()
                        .withCredentials(awsCreds)
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
        List<ResultType> result = new ArrayList<>();

        if(keyList.get(0).getText().contains("phone number")){
            System.out.println("YAYA");
        }

        for (Entity entity : list) {
            if (entity.getType().equals("PERSON")) {
                result.add(aws_rds_dao.getPerson(entity.getText()));
            }
        }
        return result;

//        Gson gson = new Gson();
//
//        TwoLists twoLists = new TwoLists(list, keyList);
//        String json = gson.toJson(twoLists);
//        System.out.println("Json Sent: " + json);
//
////        Type resultListType = new TypeToken<ArrayList<ArrayList<ResultType>>>() {
////        }.getType();
////        List<ResultType> resultObj = new Gson().fromJson(result, resultListType);
////        List<ResultType> tempList;
////        for (int i = 0; i < resultObj.size(); i++) {
////            tempList = (List<ResultType>) resultObj.get(i);
////            for (int j = 0; j < tempList.size(); j++) {
////                return "Hello: " + tempList.get(j).getType() + " " + tempList.get(j).getText();
////            }
////        }


    }

}
