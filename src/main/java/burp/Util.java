package burp;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    //获取用户选择
    public static String getSelect(IContextMenuInvocation invocation) {
        int start = invocation.getSelectionBounds()[0];
        int end = invocation.getSelectionBounds()[1];
        IHttpRequestResponse[] messages =invocation.getSelectedMessages();
        String select = new String(messages[0].getRequest()).substring(start, end);
        return select;
    }
    //参数溢出
    public static byte[] paramoVerflowAction (IHttpRequestResponse requestResponse, String select, int paramslen) throws UnsupportedEncodingException {
        //获取body
        byte[] request = requestResponse.getRequest();
        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(request);
        int bodyOffset = requestInfo.getBodyOffset();
        int body_length = request.length - bodyOffset;
        String body = new String(request, bodyOffset, body_length, "UTF-8");
        //获取header
        List<String> headers = BurpExtender.helpers.analyzeRequest(request).getHeaders();
        String oldStr = headers.get(0);

        //处理POST/GET选择
        byte[] bytes = new byte[]{};
        if (requestInfo.getMethod().equals("POST")) {
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < paramslen; i++) {
                str.append("&");
                str.append(select);
            }
            //修改body部分
            String newBody = body.replaceFirst(select, select+str);
            bytes = BurpExtender.helpers.buildHttpMessage(headers, newBody.getBytes());
        } else if (requestInfo.getMethod().equals("GET")) {
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < paramslen; i++) {
                str.append("&");
                str.append(select);
            }
            String newStr = oldStr.replaceFirst(select,select+str);
            headers.set(0, newStr);
            bytes = BurpExtender.helpers.buildHttpMessage(headers, body.getBytes());
        }

        return  bytes;
    }
    //垃圾数据
    public static byte[] garbageDataAction (IHttpRequestResponse requestResponse, String select) throws UnsupportedEncodingException {
        //获取body
        byte[] request = requestResponse.getRequest();
        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(request);
        int bodyOffset = requestInfo.getBodyOffset();
        int body_length = request.length - bodyOffset;
        String body = new String(request, bodyOffset, body_length, "UTF-8");

        //获取header
        List<String> headers = BurpExtender.helpers.analyzeRequest(request).getHeaders();
        String oldStr = headers.get(0);

        //处理请求
        byte[] bytes = new byte[]{};
        if (requestInfo.getMethod().equals("POST")) {
            String newBody = body.replace(select, getRandomString(Config.getKey_len(), Config.getValue_len(), Config.getNumber_len()));
            bytes = BurpExtender.helpers.buildHttpMessage(headers, newBody.getBytes());
        } else if (requestInfo.getMethod().equals("GET")) {
            String newStr = oldStr.replaceFirst(select, getRandomString(Config.getKey_len(), Config.getValue_len(), Config.getNumber_len()));
            headers.set(0, newStr);
            bytes = BurpExtender.helpers.buildHttpMessage(headers, body.getBytes());
        }
        return bytes;
    }
    //生成垃圾键值对数据 key参数长度，value数据长度， num键值对数量
    public static String getRandomString(int key, int value, int num){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();

        StringBuffer sb3=new StringBuffer();
        for (int j = 0; j < num; j++) {

            StringBuffer sb=new StringBuffer();
            for(int i=0;i<key;i++){
                int number=random.nextInt(62);
                sb.append(str.charAt(number));
            }

            StringBuffer sb2=new StringBuffer();
            for(int i=0;i<value;i++){
                int number=random.nextInt(62);
                sb2.append(str.charAt(number));
            }
            sb3.append("&");
            sb3.append(sb);
            sb3.append("=");
            sb3.append(sb2);
        }

        return sb3.toString();
    }
    //单个数组笛卡尔积（重复） 可指定个数N
    public static List<String> permutation(List<String> list, int length) {
        Stream<String> stream = list.stream();
        for (int n = 1; n < length; n++) {
            stream = stream.flatMap(i -> list.stream().map(j -> i.concat(j)));
        }
        return stream.collect(Collectors.toList());
    }
    //读取本地文本 传入List集合
    public static List<String> getFileContent(File file) {
        List<String> strList = new ArrayList<String>();
        InputStreamReader read = null;
        BufferedReader reader = null;
        try {
            read = new InputStreamReader(new FileInputStream(file),"utf-8");
            reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                strList.add(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strList;
    }
}
