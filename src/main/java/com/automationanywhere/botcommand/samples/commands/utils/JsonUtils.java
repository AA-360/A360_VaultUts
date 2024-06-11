package com.automationanywhere.botcommand.samples.commands.utils;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.DictionaryValue;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import java.util.*;

public class JsonUtils {


    public static Object getKeyJson(String key, JSONObject json){
        JSONObject stored = null;
        Object ret = null;

        for(String k: key.split("\\.")){
            if(stored == null){
                stored =  (JSONObject)json.get(k);
            }else{
                String a = stored.get(k).getClass().toString();
                if(stored.get(k).getClass() != json.getClass()){
                    ret = stored.get(k);
                    return ret;
                }
                stored =  (JSONObject)stored.get(k);
            }

        }

        return ret == null? stored:ret;
    }

    public static Boolean KeyJsonExists(String key, JSONObject json){
        Object stored = json;
        String[] keys = key.split("\\.");
        String current_key = keys[0];

        if(getKeys(json).contains(current_key)) {
            if(key.split("\\.").length > 1){
                stored = ((JSONObject) stored).get(current_key);
                if (stored instanceof JSONObject) {
                    keys = Arrays.copyOf(keys, keys.length-1);
                    String next_key = String.join(".", keys);
                    return KeyJsonExists(next_key,(JSONObject) stored);
                }
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public static Map<String, Value> JsonObjToMapValue(JSONObject json){
        Map<String, Value> Data = new HashMap<>();
        List<String> keys = getKeys(json);
        JSONObject stored = null;

        for(String k: keys){
            if(json.get(k).getClass() != json.getClass()){
                if(json.get(k) instanceof String){
                    Data.put(k,new StringValue(json.get(k)));
                }else if(json.get(k) instanceof Integer || json.get(k) instanceof Double || json.get(k) instanceof Long){
                    Data.put(k,new NumberValue(json.get(k)));
                }else if(json.get(k) instanceof JSONArray){
                    JSONArray ja = (JSONArray)json.get(k);

                    ListValue<String> ListVal = new ListValue<String>();
                    List<Value> CTRL = new ArrayList<Value>();

                    for(int n = 0; n < ja.length(); n++)
                    {
                        JSONObject jo = ja.getJSONObject(n);
                        DictionaryValue dc = new DictionaryValue();
                        dc.set(JsonObjToMapValue(jo));
                        CTRL.add(dc);

                    }
                    ListVal.set(CTRL);
                    Data.put(k,ListVal);

                    String a = "";
                }else{
                    Data.put(k,new StringValue(json.get(k)));
                }


            }else{


                DictionaryValue dc = new DictionaryValue();
                dc.set(JsonObjToMapValue((JSONObject)json.get(k)));
                Data.put(k,dc);
            }
        }

        return Data;
    }

    public static DictionaryValue JsonObjToAADict(JSONObject json){
        DictionaryValue ret = new DictionaryValue();
        Map<String, Value> v =  JsonUtils.JsonObjToMapValue(json);
        ret.set(v);
        return ret;
    }

    public static DictionaryValue StringJsonToAADict(String json){
        JSONObject data = JsonUtils.StringToObj(json);
        DictionaryValue ret = new DictionaryValue();
        Map<String, Value> v =  JsonUtils.JsonObjToMapValue(data);
        ret.set(v);
        return ret;
    }

    public static List<String> getKeys(JSONObject json){
        List<String> l = new ArrayList<>();

        Iterator<String> keys = json.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            l.add(key);
        }
        return l;

    }

    public static JSONObject XmlToObj(String xml,boolean forceString){
        JSONObject jsonObject;
        try {
            JSONObject json = XML.toJSONObject(xml,forceString);
            return json;
        }catch (JSONException err){
            throw new BotCommandException(err.toString());
        }
    }

    public static JSONObject StringToObj(String jsonString){
        JSONObject jsonObject;
        try {
            JSONObject json = new JSONObject(jsonString);
            return json;
        }catch (JSONException err){
            throw new BotCommandException(err.toString());
        }
    }

}
