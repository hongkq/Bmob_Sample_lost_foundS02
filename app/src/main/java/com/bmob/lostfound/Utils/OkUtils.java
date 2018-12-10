package com.bmob.lostfound.Utils;

/**
 * Created by Administrator on 2018/12/10.
 */
import android.content.Context;
import android.net.ConnectivityManager;

import com.bmob.lostfound.Newsheadlines;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkUtils {
    private Context context;
    private OkHttpClient okClient;
    private LoadData loadData;
    public OkUtils(Context context,LoadData loadData){
        this.context = context;
        this.loadData = loadData;
    }
    public OkHttpClient getOkClient(){
        if(okClient==null){
            synchronized (this){
                if(okClient==null){
                    okClient = new OkHttpClient.Builder()
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(5,TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return okClient;
    }
    public boolean isConnected(){
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager.getActiveNetworkInfo()!= null){
            flag = manager.getActiveNetworkInfo().isConnected();
        }
        return flag;
    }

    public void getRequest(String path){
        if(isConnected()){
            OkHttpClient client = getOkClient();
            Request request = new Request.Builder()
                    .url(path)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str = response.body().string();
                    if(str!=null){
                        ((Newsheadlines)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadData.loadData(str);
                            }
                        });
                    }

                }
            });
        }
    }
    public interface LoadData {
        void loadData(String json);
    }
}
