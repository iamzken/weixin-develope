package com.imooc.test;
import java.io.FileReader;
import java.util.Date;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class TestMD5 {
    public static void main(String[] args) {

        String APPID = "20160707000024813"; // ��Ҫ�ĳ��Լ���APP ID
        String KEY = "PFPZQ4j2XOL4d8gp0ypt"; //��Ҫ�ĳ��Լ�����Կ
        String SALT = Long.toString(new Date().getTime());//��������ٷ��ṩ���ǻ�ȡʱ��
        String SOURCE = "football";//��������
        String SIGN = "";

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        String jsFileName = "E:/test/md5.js"; // ��ȡjs�ļ�

        FileReader reader;
        try {
            reader = new FileReader(jsFileName);
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine; // ����merge��������������������
                System.out.println(APPID + SOURCE + SALT + KEY);
                SIGN = (String) invoke.invokeFunction("MD5",
                        APPID + SOURCE + SALT + KEY);
                System.out.println(SIGN);
            }

            reader.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // ִ��ָ���ű�

    }
}