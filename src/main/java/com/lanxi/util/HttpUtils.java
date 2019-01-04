package com.lanxi.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by lx on 2018/4/10.
 */
public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
    /** 默认编码字符�? */
    public static final String defEnCharset = "utf-8";
    /** 默认解码字符�? */
    public static final String defDeCharset = "utf-8";
    /** 默认超时时间 */
    public static final Integer defTimeout = 10000;
    public static String sendPost(String url, String param, String charSet,String charSet1) {
        StringBuffer result = new StringBuffer();
        try {
            URL httpurl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) httpurl
                    .openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type", "text/xml;charset="+charSet);
            byte[] ss = param.getBytes(charSet);
            httpConn.setRequestProperty("Accept-Charset", charSet);
            httpConn.setRequestProperty("Content-Length", String.valueOf(ss.length));
            httpConn.setConnectTimeout(60000);
            httpConn.setReadTimeout(60000);
            OutputStream out = httpConn.getOutputStream();
            out.write(ss);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream(), charSet1));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException("发送post请求出错,url:" + url, e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的字符集,charSet:" + charSet, e);
        } catch (IOException e) {
            throw new RuntimeException("发送post请求IO出错,url:" + url, e);
        }
        return result.toString();
    }





    public static String sendPost(String url, String param, String charSet) {
        StringBuffer result = new StringBuffer();
        try {
            URL httpurl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) httpurl
                    .openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            byte[] ss = param.getBytes(charSet);
            httpConn.setRequestProperty("Accept-Charset", charSet);
            httpConn.setRequestProperty("Content-Length", String.valueOf(ss.length));
            httpConn.setConnectTimeout(60000);
            httpConn.setReadTimeout(60000);
            OutputStream out = httpConn.getOutputStream();
            out.write(ss);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream(), charSet));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException("发送post请求出错,url:" + url, e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的字符集,charSet:" + charSet, e);
        } catch (IOException e) {
            throw new RuntimeException("发送post请求IO出错,url:" + url, e);
        }
        return result.toString();
    }
    public static String sendPost(String url,Map<String,String> param,String charSet){
        StringBuffer result = new StringBuffer();
        try {
            URL httpurl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
//            httpConn.setRequestProperty("Cookie","UM_distinctid=165d2a25a4e787-045a510c3b4e6f-396b4c0b-15f900-165d2a25a4f8cc; ASP.NET_SessionId=5z2hms55ehiylpqf5hzud045; CNZZDATA5008961=cnzz_eid%3D1883724238-1536837152-%26ntime%3D1536837152; Hm_lvt_8e3ba52c8285eb1b4b4e42ff541a9c16=1536836617,1536838637,1536839059; Hm_lpvt_8e3ba52c8285eb1b4b4e42ff541a9c16=1536839059");

            PrintWriter out = new PrintWriter(httpConn.getOutputStream());
            int i = 0;
            Set<Map.Entry<String, String>> set = param.entrySet();
            for (Map.Entry<String, String> entry:set){
                out.print(entry.getKey());
                out.print("=");
                out.print(entry.getValue());
                if (i!=set.size()-1){
                    out.print("&");
                }
                i++;
            }
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charSet));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException("发送post请求出错,url:"+url,e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的字符集,charSet:"+charSet,e);
        } catch (IOException e) {
            throw new RuntimeException("发送post请求IO出错,url:"+url,e);
        }
        return result.toString();
    }


    /**
     * 字符串转map
     *  @param str
     * @return
     */
    public static Map<String,String> strToMap(String str){
        Map<String,String> map = new HashMap<>();
        String[] split = str.split("&");
        for (String s : split){
            String[] split1 = s.split("=");
            map.put(split1[0],split1[1]);
        }
        return  map;
    }
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

    }



    public static String postXml(String xml, String url, String charset) {
        return postXml(xml, url, charset, 60000);
    }

    /**
     * 发�?�xml文档数据
     *
     * @param xml
     *            xml字符�?
     * @param url
     *            接收方的地址
     * @param charset
     *            编码字符�? 默认utf-8
     * @param timeout
     *            超时时间
     * @return
     */
    public static String postXml(String xml, String url, String charset, Integer timeout) {
        return post(xml, url, charset, "txt/html", timeout);
    }


    /**
     * 通过给定的url发�?�内�?,并返回接收方返回的内�?
     *
     * @param content
     *            发�?�的内容
     * @param Url
     *            接收方地�?
     * @param charset
     *            编码解码字符�? 默认utf-8
     * @param type
     *            发�?�内容格�?
     * @param timeout
     *            超时时间
     * @return 接收方返回的内容
     */
    public static String post(String content, String Url, String charset, String type, Integer timeout) {
        try {
            charset = charset == null ? defEnCharset : charset;
            timeout = timeout == null ? defTimeout : timeout;
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // TODO 超时时间10分钟
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            if (type != null)
                conn.setRequestProperty("Content-Type", type + ";Charset=" + charset);
            conn.connect();
            post(content, conn.getOutputStream(), charset);
            if (conn.getResponseCode() == 200)
                return receive(conn.getInputStream(), charset);
        } catch (Exception e) {
            throw new RuntimeException("发�?�post请求异常", e);
        }
        return null;
    }

    /**
     * 发�?�请�?
     *
     * @param content
     *            请求内容
     * @param outStream
     *            输出�?
     * @param charset
     *            编码字符�? 默认utf-8
     */
    private static void post(String content, OutputStream outStream, String charset) {
        try {
            charset = charset == null ? defEnCharset : charset;
            OutputStreamWriter writer;
            writer = new OutputStreamWriter(outStream, charset);
            PrintWriter printer = new PrintWriter(writer);
            printer.println(content);
            printer.close();
        } catch (Exception e) {
            throw new RuntimeException("发�?�post请求异常", e);
        }
    }

    /**
     * 接受请求
     *
     * @param inStream
     *            输入�?
     * @param charset
     *            解码字符�? 默认utf-8
     * @return 请求内容
     */
    private static String receive(InputStream inStream, String charset) {
        try {
            charset = charset == null ? defDeCharset : charset;
            InputStreamReader reader = new InputStreamReader(inStream, charset);
            BufferedReader buffReader = new BufferedReader(reader);
            String temp;
            StringBuffer reply = new StringBuffer();
            while ((temp = buffReader.readLine()) != null)
                reply.append(temp);
            buffReader.close();
            return reply.toString();
        } catch (Exception e) {
            throw new RuntimeException("接收数据异常", e);
        }
    }

    /**
     * 发�?�get 请求
     *
     * @param url
     *            地址+直接参数跟随
     * @param charset
     *            字符�?
     * @return
     */
    public static String get(String url, String charset) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet();
            try{
                URL url1 = new URL(url);
                URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
                get = new HttpGet(uri);
            }catch(Exception e){
                e.printStackTrace();
            }
            HttpResponse res = client.execute(get);
            BufferedReader buffReader = new BufferedReader(
                    new InputStreamReader(res.getEntity().getContent(), charset));
            StringBuffer strBuff = new StringBuffer();
            String temp = null;
            while ((temp = buffReader.readLine()) != null)
                strBuff.append(temp);
            return strBuff.toString();
        } catch (Exception e) {
            throw new RuntimeException("发送get请求异常", e);
        }
    }




    /**
     * 将map形式的参数 转换为字符串
     * @param params
     * @return
     */
    public static String getParamStr(Map<String, String> params){
        List<String> keys=new ArrayList<String>();
        for(Map.Entry<String, String> each:params.entrySet())
            keys.add(each.getKey());
        Collections.sort(keys);
        StringBuilder temp=new StringBuilder();
        for(String each:keys){
            temp.append(each+"=");
            temp.append(params.get(each));
            temp.append("&");
        }
        return temp.toString().substring(0,temp.length()-1);
    }


    /**
     * 接受 字符串形式的参数 以及 url 编解码字符集 然后post
     * @param str
     * @param uRl
     * @param enCharset
     * @param deCharset
     * @return
     */
    public static String postStr(String str,String uRl,String enCharset,String deCharset) {
        log.info("请求url:"+uRl+"请求参数:"+str);
        StringBuilder rs=new StringBuilder();
        try {
            URL url;
            url = new URL(uRl);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            PrintWriter out=new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),enCharset));
            out.println(str);
            out.flush();
            out.close();

            BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream(), deCharset));
            String temp=null;
            while((temp=in.readLine())!=null)
                rs.append(temp);
            in.close();
            return rs.toString();
        } catch (MalformedURLException e) {
            log.error("url格式错误",e);
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的字符集",e);
        } catch (IOException e) {
            log.error("输入输出流错误",e);
        }
        return  "";
    }

}
