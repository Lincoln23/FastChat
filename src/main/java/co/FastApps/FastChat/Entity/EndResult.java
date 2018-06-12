package co.FastApps.FastChat.Entity;

import java.util.List;
import java.util.Map;

public class EndResult {
    private List<List<Map<String, Object>>> resultList;
    private String plainText;


	public EndResult(List<List<Map<String, Object>>> resultList, String plainText) {
        this.resultList = resultList;
        this.plainText = plainText;
    }

    public List<List<Map<String, Object>>> getResultList() {
        return resultList;
    }

    public void setResultList(List<List<Map<String, Object>>> resultList) {
        this.resultList = resultList;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
}
