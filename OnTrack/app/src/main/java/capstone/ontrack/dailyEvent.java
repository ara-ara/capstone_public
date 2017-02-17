package capstone.ontrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rleppelmeier on 11/14/16.
 */
public class dailyEvent {
    private List<String> name = new ArrayList<>();
    private List<String> type = new ArrayList<>();

    public void addEventName(String eventName){
        name.add(eventName);
    }

    public void addType(String eventType){
        type.add(eventType);
    }

    public List<String> getEventName(){
        return name;
    }

    public List<String> getType(){
        return type;
    }

    public Integer size(){
        return name.size();
    }
}
