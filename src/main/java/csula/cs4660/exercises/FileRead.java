package csula.cs4660.exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Introduction Java exercise to read file
 */
public class FileRead {
    private int[][] numbers;
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
    }

    /**
     * Read the file assuming following by the format of split by space and next
     * line. Display the sum for each line and tell me
     * which line has the highest mean.
     *
     * lineNumber starts with 0 (programming friendly!)
     */
    public int mean(int lineNumber) {
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
        }
        return max;
    }

    public int min(int lineNumber) {
    	int min = numbers[lineNumber][0];
    	for (int row = 1; row<numbers[lineNumber].length; row++){
    		if (min > numbers[lineNumber][row]){
    			min = numbers[lineNumber][row];
    		}
        }
        return min;
    }

    public int sum(int lineNumber) {
    	int sum = 0;
    	for (int row = 0; row<numbers[lineNumber].length; row++){
    		sum += numbers[lineNumber][row];
        }
        return sum;
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
