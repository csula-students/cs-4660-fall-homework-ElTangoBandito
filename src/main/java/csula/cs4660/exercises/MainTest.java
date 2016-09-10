package csula.cs4660.exercises;

import java.io.File;
import java.io.IOException;

/**
 * Created by Kuo-Cheng on 8/27/2016.
 */
public class MainTest {
    public static void main(String[] args) throws IOException {
        File file = new File("C:/Users/Kuo-Cheng/Desktop/cs4660-fall-2016/src/main/resources/array.txt");
        FileRead tester = new FileRead(file);
        
        System.out.println(tester.sum(0));
        System.out.println(tester.mean(0));
        System.out.println(tester.max(0));
        System.out.println(tester.min(0));
    }
}
