package com.leafive.test.simple;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Analysis {
    public static void main(String[] args) {
        Map<String,int[]> resultMap = new HashMap<>();
        //测试文本
        File file1= new File("orig.txt"); 
        File file2=new File("orig.txt");
   
        
//        try {
//			List<String> words = Files.readAllLines(Paths.get(System.getProperty("user.dir"), "orig.txt"));
//			String[] strs = words.toArray(new String[words.size()]);
//			System.out.println(Arrays.deepToString(strs));
//		} catch (IOException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
        String a=replaceSpecialStr(txttest.txt2String(file1));
        String b=replaceSpecialStr(txttest.txt2String(file2));
        
        System.out.println(a+"\n");
        System.out.println(b);
       String text1 =a;
        String text2 =b;
        //统计
        statistics(resultMap, IKUtils.divideText(text1),1);
        statistics(resultMap, IKUtils.divideText(text2),0);
        //计算类
        final Calculation calculation = new Calculation();
        resultMap.forEach((k,v)->{
            int[] arr = resultMap.get(k);
            calculation.setNumerator( arr[0] * arr[1]);
            calculation.setElementA(arr[0] * arr[0]);
            calculation.setElementB(arr[1] * arr[1]);
        });
 
       System.out.println("文本相似度：" + calculation.result());
    }
    public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }
 
    private static void statistics(Map<String,int[]> map,List<String> words ,int direction){
        if(null == words || words.size() == 0){
            return ;
        }
        int[] in = null;
        boolean flag = direction(direction);
        for (String word : words){
            int[] wordD = map.get(word);
            if(null == wordD){
                if(flag){
                    in = new int[]{1,0};
                }else {
                    in = new int[]{0,1};
                }
                map.put(word,in);
            }else{
                if(flag){
                    wordD[0]++;
                }else{
                    wordD[1]++;
                }
            }
        }
    }
    
    //判断不同句子
    private static boolean direction(int direction){
        return direction == 1?true:false;
    }
 
}

 class IKUtils {

    public static List<String> divideText(String text){
        if(null == text || "".equals(text.trim())){
            return null;
        }
        List<String> resultList = new ArrayList<>();
        StringReader re = new StringReader(text);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex = null;
        try {
            while ((lex = ik.next()) != null) {
                resultList.add(lex.getLexemeText());
            }
        } catch (Exception e) {
            //TODO
        }
        return resultList;
    }
 
}


 class Calculation{
	 
    private  double elementA=0;
    private  double elementB=0;
    private  double numerator=0;
    
    public void setElementA(double sum) {
    	
    	elementA=sum+elementA;
    }
 public void setElementB(double sum) {
    	
    	elementB=sum+elementB;
    }
 
 public void setNumerator(double sum) {
 	
	 numerator=sum+numerator;
 }
    
    public double result(){
        return numerator / Math.sqrt(elementA * elementB);
    }
 }
 class txttest {
     public static String txt2String(File file){
         String result = "";
         try{
             BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
             String s = null;
             while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                 result = result + "\n" +s;
             }
             br.close();    
         }catch(Exception e){
             e.printStackTrace();
         }
         return result;
     }
 }
     
    //省略get/set


