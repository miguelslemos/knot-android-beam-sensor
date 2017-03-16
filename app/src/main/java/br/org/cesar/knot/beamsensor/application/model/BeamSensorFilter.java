package br.org.cesar.knot.beamsensor.application.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Filter;

/**
 * Created by carlos on 14/03/17.
 */

public class BeamSensorFilter {

    public enum FilterLinkType {
        And,Or,None
    }

    public enum FilterCompareValueMode {
        Equal,GreatThan,LessThan,Contains
    }

    private List<FilterItem> items;

    private class FilterItem{

        private Map<String,?> keyValueMap;
        private FilterLinkType siblingLinkType;
        private FilterCompareValueMode compareValueMode;
        private FilterItem sibling;

        public Map<String, ?> getKeyValueMap() {
            return keyValueMap;
        }

        public void setKeyValueMap(Map<String, ?> keyValueMap) {
            this.keyValueMap = keyValueMap;
        }

        public FilterCompareValueMode getCompareValueMode() {
            return compareValueMode;
        }

        public void setCompareValueMode(FilterCompareValueMode compareValueMode) {
            this.compareValueMode = compareValueMode;
        }

        public FilterItem getSibling() {
            return sibling;
        }

        public void setSibling(FilterItem sibling,FilterLinkType siblingLinkType) {
            this.sibling = sibling;
            this.siblingLinkType = siblingLinkType;
        }

        public FilterLinkType getSiblingLinkType() {
            return siblingLinkType;
        }

    }

    public BeamSensorFilter(){
        items = new ArrayList<>();
    }

    public <T> void build(String name,T value,FilterCompareValueMode compareValueMode){
        FilterItem filterItem = new FilterItem();
        Map<String, T> map = new HashMap<String, T>();
        map.put(name,value);
        filterItem.setKeyValueMap(map);
        filterItem.setCompareValueMode(compareValueMode);
        items.add(filterItem);
    }

    public <T,Z> void build(String param1,T paramValue1,FilterCompareValueMode compareValueMode1,FilterLinkType siblingLinkType,
                            String param2,Z paramValue2,FilterCompareValueMode compareValueMode2){

        FilterItem filterItem = new FilterItem();
        Map<String, T> leftMap = new HashMap<>();
        leftMap.put(param1,paramValue1);
        filterItem.setKeyValueMap(leftMap);
        filterItem.setCompareValueMode(compareValueMode1);

        FilterItem filterItem1 = new FilterItem();
        Map<String, Z> rightMap = new HashMap<>();
        rightMap.put(param2,paramValue2);
        filterItem1.setKeyValueMap(rightMap);
        filterItem1.setCompareValueMode(compareValueMode2);

        filterItem.setSibling(filterItem1,siblingLinkType);
        items.add(filterItem);
    }

    public JSONObject getQuery() throws JSONException {
        JSONObject query = new JSONObject();
        JSONArray array = new JSONArray();
        for (FilterItem item : items) {
            FilterItem sibling = item.getSibling();
            if (sibling != null) {
                array.put(create(item));
                array.put(create(item.getSibling()));
                if (item.getSiblingLinkType() == FilterLinkType.Or) {
                    query.put("$or", array);
                }
                else{
                    query.put("",array);
                }
            }
        }
        return query;
    }

    private JSONObject create(FilterItem item){
        JSONObject query = new JSONObject();
        if(item.getCompareValueMode() == FilterCompareValueMode.Equal){
            Set<? extends Map.Entry<String, ?>> entries = item.getKeyValueMap().entrySet();
            for (Map.Entry<String, ?> entry : entries) {
                try {
                    query.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return query;
    }
}
