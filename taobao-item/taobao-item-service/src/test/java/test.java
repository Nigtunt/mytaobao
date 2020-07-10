import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: YHQ
 * @Date: 2020/6/18 19:07
 */
public class test {
    public static int respace(String[] dictionary, String sentence) {
        int n = sentence.length();
        int [] d = new int[n+1];
        for (int i = 0; i < n; i++) {
            d[i+1]=d[i]+1;
            for(String word:dictionary){
                if(word.length()<=i+1){
                    int start = i+1-word.length();
                    if(word.equals(sentence.substring(start,start+word.length())))
                        d[i+1]=Math.min(d[i+1],d[i+1-word.length()]);
                }
            }
        }
        return d[n];
    }
    public static void main(String args[]){

        System.out.println(respace(new String[]{"looked","just","like","her","brother"},"jesslookedjustliketimherbrother"));
    }
}
