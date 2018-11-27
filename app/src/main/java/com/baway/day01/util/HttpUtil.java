package com.baway.day01.util;

import android.os.AsyncTask;

import com.google.common.io.CharStreams;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者：方诗康
 * 封装Http请求工具类
 */
public class HttpUtil {
    HttpLinear linear;

    public HttpUtil(HttpLinear linear) {
        this.linear = linear;
    }

    public HttpUtil get(String url) {
        doHttp(url,"GET","");
        return this;
    }

    public HttpUtil post(String url,String s) {
        doHttp(url,"POST",s);
        return this;
    }

    public void doHttp(String url,String method,String string) {
        new MyAsyncTask(url,method,string).execute();
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, String> {

        String pathUrl, method, string;

        public MyAsyncTask(String pathUrl, String method, String string) {
            this.pathUrl = pathUrl;
            this.method = method;
            this.string = string;
        }

        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            try {
                URL url = new URL(pathUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                connection.setReadTimeout(5000);
                if ("POST".equals(method)) {
                    PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                    printWriter.write(string);
                    printWriter.flush();
                    printWriter.close();
                }
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    data = CharStreams.toString(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                }else {
                    data = "0";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("0".equals(s)){
                //请求失败
                linear.fail();
            }else {//请求成功
                linear.success(s);
            }
        }
    }

    public interface HttpLinear {
        void success(String data);

        void fail();
    }
}
