package co.FastApps.FastChat.Entity;

import com.amazonaws.services.comprehend.model.Entity;
import com.amazonaws.services.comprehend.model.KeyPhrase;

import java.util.List;

@Deprecated
public class TwoLists {
    private List<Entity> listEntity;
    private List<KeyPhrase> listKey;

    @Deprecated
    public TwoLists(List<Entity> listEntity, List<KeyPhrase> listKey) {
        this.listEntity = listEntity;
        this.listKey = listKey;
        //removes duplicates from listEntity and listKey
        for (int i = 0; i < this.listKey.size(); i++) {
            for (int j = 0; j < this.listEntity.size(); j++) {
                if (this.listEntity.get(j).getText().equals(this.listKey.get(i).getText())) {
                    this.listKey.remove(i);
                }
            }
        }
    }
}
