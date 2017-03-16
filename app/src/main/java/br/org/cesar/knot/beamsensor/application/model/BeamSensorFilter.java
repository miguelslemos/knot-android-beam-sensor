package br.org.cesar.knot.beamsensor.application.model;

import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    private class FilterItem<T>{

        private Map<String,T> keyValueMap;
        private FilterLinkType linkType;
        private FilterCompareValueMode compareValueMode;

        public FilterLinkType getLinkType() {
            return linkType;
        }

        public void setLinkType(FilterLinkType linkType) {
            this.linkType = linkType;
        }

        public Map<String, T> getKeyValueMap() {
            return keyValueMap;
        }

        public void setKeyValueMap(Map<String, T> keyValueMap) {
            this.keyValueMap = keyValueMap;
        }

        public FilterCompareValueMode getCompareValueMode() {
            return compareValueMode;
        }

        public void setCompareValueMode(FilterCompareValueMode compareValueMode) {
            this.compareValueMode = compareValueMode;
        }
    }

    public BeamSensorFilter(){
        items = new ArrayList<>();
    }

    public <T> void build(String name,T value,FilterCompareValueMode comparerValueMode,FilterLinkType linkType){
        FilterItem<T> filterItem = new FilterItem<>();
        Map<String, T> map = new HashMap<String, T>();
        map.put(name,value);
        filterItem.setKeyValueMap(map);
        filterItem.setLinkType(linkType);
        filterItem.setCompareValueMode(comparerValueMode);
        items.add(filterItem);
    }

    public JSONObject getQuery(){
        JSONObject query = new JSONObject();
        for (FilterItem item :
                items) {

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
        }
        return query;
    }
}
