package cs250.hw2;
import java.io.File;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class Memory {
    volatile static int loopCounterVolatile;
    static int loopCounterNonvolatile;
    static long runningTotalRegular = 0;
    public static void main(String[] args) {
        task1(args[0], args[1]);
        task2(args[0], args[1], args[2]);
        task3(args[0], args[1], args[2]);
        //System.out.println("hello world");
    }

    //one loop for a volatile one, one for the non volatile
    public static void task1(String arg1, String arg2){
        long runningTotal = 0;
        long runningTotalRegular = 0;
        long runningTotalVolatile = 0;
        double totalVolatile = 0.0;
        double totalRegular = 0.0;
        int size = Integer.parseInt(arg1);
        double regularSum = 0;
        double volatileSum = 0;
        long startTime = 0;
        long endTime = 0;
        int count = 0;
        int experiments = Integer.parseInt(arg2);
        //System.out.println(count);
        //System.out.println(experiments);
        while (count < experiments){
            startTime = System.nanoTime();
            for (loopCounterVolatile = 0; loopCounterVolatile < size; loopCounterVolatile++){
                if (loopCounterVolatile % 2 == 0){
                    runningTotal += loopCounterVolatile;
                }
                else {
                    runningTotal -= loopCounterVolatile;
                }
                System.out.flush();
            }
            endTime = System.nanoTime();
            runningTotalRegular += (endTime - startTime);
            //System.out.println(runningTotalRegular);
            //System.out.println(count);
            count++;
            //System.out.println(runningTotal);
        }
        //System.out.println(runningTotalRegular);
        totalRegular = ((1.0 /1E9)*(runningTotalRegular/experiments));
        //System.out.println(runningTotalRegular);
        //totalRegular = totalRegular/experiments;
        //System.out.println(runningTotalRegular);
        regularSum = runningTotal/(1.0*experiments);
        
        
        count =  0;
        runningTotal = 0;
        //System.out.println(count);
        while (count < experiments){
            startTime = System.nanoTime();
            for (int loopCounterNonvolatile = 0; loopCounterNonvolatile < size; loopCounterNonvolatile++){
                if (loopCounterNonvolatile % 2 == 0){
                    runningTotal += loopCounterNonvolatile;
                }
                else {
                    runningTotal -= loopCounterNonvolatile;
                }
                System.out.flush();
            }
            endTime = System.nanoTime();
            runningTotalVolatile += (endTime - startTime);
            //System.out.println(count);
            count++;
            //System.out.println(runningTotal);
        }
        totalVolatile = (runningTotalVolatile * 1.0e-9);
        totalVolatile = totalVolatile/(1.0*experiments);
        volatileSum = (runningTotal/(1.0*experiments));

        String formattedTotalRegular = String.format("%.5f", totalRegular);
        String formattedTotalVolatile = String.format("%.5f", totalVolatile);
        String formattedRegularSum = String.format("%.2f", regularSum);
        String formattedVolatileSum = String.format("%.2f", volatileSum);

        System.out.println("Task 1 \nRegular: " + formattedTotalRegular + " seconds\nVolatile: " + formattedTotalVolatile + " seconds\nAvg regular sum: " + formattedRegularSum + "\nAvg volatile sum: " + formattedVolatileSum + "\n");
    }

    public static void task2(String arg1, String arg2, String arg3){
        int size = Integer.parseInt(arg1);
        int experiments = Integer.parseInt(arg2);
        Integer seed = Integer.parseInt(arg3);
        //ArrayList<Integer> arr = new ArrayList<>(size);
        Integer[] arr = new Integer[size];
        int count = 0;
        Random rand = new Random(seed);
        int firstTenPercent = (int) Math.ceil(size * 0.1);
        //int lastTenPercent = (int) Math.ceil(size * 0.1);      
        long startTime = 0;
        long endTime = 0;
        Integer arrVal = 0;
        int sumOfElements = 0;
        double sumOfFirstAccess = 0;
        double sumOfLastAccess = 0;
        double avgFirstAccess = 0;
        double avgLastAccess = 0;
        double avgElements = 0;

        
        for (int i = 0; i < size; i++){
            //arr.add((Integer) rand.nextInt(seed));
            arr[i] = ((Integer) rand.nextInt());
        }
        //System.out.println(firstTenPercent);
        while (count < experiments){
            for (int i = 0; i < firstTenPercent; i++){
                //if (i % size < firstTenPercent){
                    //time start
                    startTime = System.nanoTime();
                    //access the element
                    //arrVal = arr.get(i);
                    arrVal = arr[i];
                    //time end
                    endTime = System.nanoTime(); 
                    //sum the time it takes
                    sumOfFirstAccess += (endTime - startTime);
                    sumOfElements += arrVal;
                //}                   
                
            }
            //access the last 10% element
            //int startIndex = size - lastTenPercent; 
            //int randomIndex = startIndex + rand.nextInt(lastTenPercent);
            int randomIndex = size - rand.nextInt(firstTenPercent);
            //int randomIndex = size - firstTenPercent + rand.nextInt(firstTenPercent - 1);
            //System.out.println(firstTenPercent);
            startTime = System.nanoTime();
            arrVal = arr[randomIndex];
            endTime = System.nanoTime();
            //System.out.println(randomIndex);

            //sum the time it takes for last 10%
            sumOfLastAccess += (endTime - startTime);
            //System.out.println(sumOfLastAccess);
            //sum the sum of the elements
            sumOfElements += arrVal;

            count++;
        }
        //average for time : time it takes/experiments
        sumOfFirstAccess = sumOfFirstAccess/(1.0*firstTenPercent);
        avgFirstAccess = sumOfFirstAccess/(1.0*experiments);
        //another average for time
        avgLastAccess = sumOfLastAccess/(1.0*experiments);
        //average for sum : sum of the sum/experiments
        //System.out.println(sumOfElements);
        avgElements =  sumOfElements/(1.0*experiments);
        //System.out.println(avgElements);
        String formattedAvgFirstAccess = String.format("%.2f", avgFirstAccess);
        String formattedAvgLastAccess = String.format("%.2f", avgLastAccess);
        String formattedAvgElements = String.format("%.2f", avgElements);

        System.out.println("Task 2 \nAvg time to access known element: " + formattedAvgFirstAccess + " nanoseconds\nAvg time to access random element: " + formattedAvgLastAccess + " nanoseconds\nSum: " + formattedAvgElements + "\n");
    }

    public static void task3(String arg1, String arg2, String arg3){
        int size = Integer.parseInt(arg1);
        int experiments = Integer.parseInt(arg2);
        int seed = Integer.parseInt(arg3);
        int count = 0;
        Random rand = new Random(seed);
        int randNum = -1;
        long startTime = 0;
        long endTime = 0;
        long sumTreeAccess = 0;
        long sumListAccess = 0;
        double avgTree = 0;
        double avgList = 0;
        TreeSet<Integer> tree = new TreeSet<Integer>();
        LinkedList<Integer> list = new LinkedList<Integer>();


        for (int i = 0; i < size; i++){
            tree.add(i);
        }

        for (int i = 0; i < size; i++){
            list.add(i);
        }

        while (count < experiments){
            randNum = rand.nextInt(size); 
            startTime = System.nanoTime();
            tree.contains(randNum);
            endTime = System.nanoTime();
            sumTreeAccess += (endTime - startTime);

            startTime = System.nanoTime(); 
            list.contains(randNum);
            endTime = System.nanoTime();
            sumListAccess += (endTime - startTime);


            count++;
        }
        avgTree = sumTreeAccess/(1.0*experiments);
        avgList = sumListAccess/(1.0*experiments);

        String formattedAvgTree = String.format("%.2f", avgTree);
        String formattedAvgList = String.format("%.2f", avgList);

        System.out.println("Task 3\nAvg time to find in set: " + formattedAvgTree + " nanoseconds\nAvg time to find in list: " + formattedAvgList + " nanoseconds\n");

    }


    

}