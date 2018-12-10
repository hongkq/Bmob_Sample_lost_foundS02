package com.bmob.lostfound.Utils;

/**
 * Created by Administrator on 2018/12/10.
 */

import com.bmob.lostfound.bean.JavaBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtils {
    public JavaBean getJson(String json){
        JavaBean json1=new JavaBean();
        try {
            JSONObject object = new JSONObject(json);
//            Json.setStatus(object.optString("status"));
            json1.setReason(object.optString("reason"));
            JavaBean.Result result = json1.new Result();
            JSONObject ob1 = object.optJSONObject("result");

            ArrayList<JavaBean.Result.Data> datas = new ArrayList<>();
            JSONArray array = ob1.optJSONArray("data");

            for(int i=0;i<array.length();i++){
                JSONObject ob2 = array.optJSONObject(i);

                int stat = ob2.optInt("stat");
                String uniquekey = ob2.optString("uniquekey");
               // int oid = ob2.optInt("oid");
                String title = ob2.optString("title");
                String date = ob2.optString("date");
                String category = ob2.optString("category");
                String author_name = ob2.optString("author_name");
                String url = ob2.optString("url");
                String thumbnail_pic_s = ob2.optString("thumbnail_pic_s");
                String thumbnail_pic_s02 = ob2.optString("thumbnail_pic_s02");
               // String subject = ob2.optString("subject");
                JavaBean.Result.Data data = result.new Data(uniquekey,title,date,category,author_name,url,thumbnail_pic_s,thumbnail_pic_s02);
                datas.add(data);
            }
            result.setData(datas);
            json1.setResult(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json1;
    }
}
