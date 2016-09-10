package csula.cs4660.exercises;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
=======
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
>>>>>>> 7eb841e6d38a4aef90ca9998c4f63d8e09738e1e

/**
 * Introduction Java exercise to read file
 */
public class FileRead {
    private int[][] numbers;
<<<<<<< HEAD
    private List<ArrayList<Integer>> numberList = new ArrayList();

    public FileRead(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        
        String line = br.readLine();
        
        while(line != null){
            List<Integer> tempList = new ArrayList();
            for(String number: line.split(" ")){
            	int digit = Integer.parseInt(number);
            	tempList.add(digit);
            }
            numberList.add((ArrayList) tempList);
            line = br.readLine();
        }
        
        numbers = new int[numberList.size()][numberList.get(0).size()];
        int i = 0;
        for (List<Integer> numList: numberList){
        	int j = 0;
        	for (int num: numList){
        		numbers[i][j] = num;
        		j++;
        	}
        	i++;
        }
=======
    /**
     * Read the file and store the content to 2d array of int
     * @param file read file
     */
    public FileRead(File file) {
        // TODO: read the file content and store content into numbers
        List<List<Integer>> listOfNumbers = Lists.newArrayList();
        try (Stream<String> stream = Files.lines(file.toPath())) {
            stream.forEach(line -> {
                List<Integer> lineNumbers = Lists.newArrayList();
                for (String token: line.split(" ")) {
                    lineNumbers.add(Integer.parseInt(token));
                }
                System.out.println(line);
                listOfNumbers.add(lineNumbers);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        numbers = converList(listOfNumbers);
>>>>>>> 7eb841e6d38a4aef90ca9998c4f63d8e09738e1e
    }

    /**
     * Read the file assuming following by the format of split by space and next
     * line. Display the sum for each line and tell me
     * which line has the highest mean.
     *
     * lineNumber starts with 0 (programming friendly!)
     */
    public int mean(int lineNumber) {
<<<<<<< HEAD
    	int sum = sum(lineNumber);
    	int noe = numbers[lineNumber].length;
        return sum/noe;
    }

    public int max(int lineNumber) {
    	int max = numbers[lineNumber][0];
    	for (int row = 1; row<numbers[lineNumber].length; row++){
    		if (max < numbers[lineNumber][row]){
    			max = numbers[lineNumber][row];
    		}
=======
        return sum(lineNumber) / numbers[lineNumber].length;
    }

    public int max(int lineNumber) {
        int max = Integer.MIN_VALUE;
        for (int i : numbers[lineNumber]) {
            max = Integer.max(max, i);
>>>>>>> 7eb841e6d38a4aef90ca9998c4f63d8e09738e1e
        }
        return max;
    }

    public int min(int lineNumber) {
<<<<<<< HEAD
    	int min = numbers[lineNumber][0];
    	for (int row = 1; row<numbers[lineNumber].length; row++){
    		if (min > numbers[lineNumber][row]){
    			min = numbers[lineNumber][row];
    		}
=======
        int min = Integer.MAX_VALUE;
        for (int i : numbers[lineNumber]) {
            min = Integer.min(min, i);
>>>>>>> 7eb841e6d38a4aef90ca9998c4f63d8e09738e1e
        }
        return min;
    }

    public int sum(int lineNumber) {
<<<<<<< HEAD
    	int sum = 0;
    	for (int row = 0; row<numbers[lineNumber].length; row++){
    		sum += numbers[lineNumber][row];
        }
        return sum;
=======
        int sum = 0;
        for (int i : numbers[lineNumber]) {
            sum += i;
        }
        return 0;
>>>>>>> 7eb841e6d38a4aef90ca9998c4f63d8e09738e1e
    }

    private int[][] converList(List<List<Integer>> arrayList) {
        int[][] array = new int[arrayList.size()][];
        for (int i = 0; i < arrayList.size(); i++) {
            List<Integer> row = arrayList.get(i);
            array[i] = new int[row.size()];
            for (int j = 0; j < row.size(); j ++) {
                array[i][j] = row.get(j);
            }
        }
        return array;
    }
}
