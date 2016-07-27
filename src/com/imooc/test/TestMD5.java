package com.imooc.test;
import java.io.FileReader;
import java.util.Date;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class TestMD5 {
    public static void main(String[] args) {

        String APPID = "20160707000024813"; // 需要改成自己的APP ID
        String KEY = "PFPZQ4j2XOL4d8gp0ypt"; //需要改成自己的密钥
        String SALT = Long.toString(new Date().getTime());//随机数，官方提供的是获取时间
        String SOURCE = "football";//翻译内容
        String SIGN = "";

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        String jsFileName = "E:/test/md5.js"; // 读取js文件

        FileReader reader;
        try {
            reader = new FileReader(jsFileName);
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine; // 调用merge方法，并传入两个参数
                System.out.println(APPID + SOURCE + SALT + KEY);
                SIGN = (String) invoke.invokeFunction("MD5",
                        APPID + SOURCE + SALT + KEY);
                System.out.println(SIGN);
            }

            reader.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 执行指定脚本

    }
}