package com.dmoracco;

import java.util.Random;
import java.util.Arrays;

import static com.dmoracco.GetCpuTime.getCpuTime;

public class Main {

    public static void main(String[] args) {

        // Validation
        validateSearchMethods();

        // Tests
        runTimeTests(1000000000,  1, 1000000000);

    }

    public static void validateSearchMethods(){

        int testList[] = {-10, -6, -3, -1, 0, 3, 5, 7, 9, 11, 13, 21};
        int largeList[] = new int[1000000];
        int min = -100;
        int max = 100;
        largeList = generateSortedList(1000000, min , max);
        Random r = new Random();
        int randomInt = (int) Math.random() * (((max-min)+1)+min);

        System.out.println("Validating sequentialSearch: ");
        for(int i: testList){
            System.out.print(i + ", ");
        }
        System.out.println();

        System.out.println("Find 11: " + sequentialSearch(testList, 11));
        System.out.println("Find -6: " + sequentialSearch(testList, -6));
        System.out.println("Find 0: " + sequentialSearch(testList, 0));
        System.out.println("Find 42: " + sequentialSearch(testList, 42));
        System.out.println("Find -1000: " + sequentialSearch(testList, -1000));

/*
        System.out.printf("\nLarge List sequential search:");
        while (sequentialSearch(largeList, randomInt) < 0) {
            randomInt = r.nextInt(((max-min)+1)+min);
        }
        while (sequentialSearch(largeList, randomInt) >= 0) {
            randomInt = r.nextInt(((max-min)+1)+min);
        }
        System.out.println("OK");
*/

        System.out.println();
        System.out.println("Validating binarySearch: ");
        for(int i: testList){
            System.out.print(i + ", ");
        }
        System.out.println();

        System.out.println("Find 11: " + binarySearch(testList, 11));
        System.out.println("Find -6: " + binarySearch(testList, -6));
        System.out.println("Find 0: " + binarySearch(testList, 0));
        System.out.println("Find 42: " + binarySearch(testList, 42));
        System.out.println("Find -1000: " + binarySearch(testList, -1000));

/*
        System.out.printf("\nLarge List binary search:");
        while (binarySearch(largeList, randomInt) < 0) {
            randomInt = r.nextInt(((max-min)+1)+min);
        }
        while (binarySearch(largeList, randomInt) >= 0) {
            randomInt = r.nextInt(((max-min)+1)+min);
        }
        System.out.println("OK");
*/

    }

    public static int[] generateSortedList(int length, int min, int max){
        Random r = new Random();
        int randomNumberList[] = new int[length];

        for (int i = 0; i < length; i++){
            //randomNumberList[i] = (int) Math.random() * (((max - min)+1) + min );
            randomNumberList[i] = r.nextInt((max-min)+min);
            //System.out.printf("%d, ", randomNumberList[i]);
        }

        Arrays.sort(randomNumberList);
        return randomNumberList;

        // https://www.javatpoint.com/post/java-random
    }

    public static int sequentialSearch(int[] list, int key){

        for (int i = 0; i < list.length; i++){
            if (key == list[i]) return i;
        }

        return -1;
    }

    public static int binarySearch(int[] list, int key){
        int i = 0;
        int j = list.length / 2;
        int k = list.length - 1;

        while (i <= k){
            if (key == list[j]) return j;
            else {
                if (list[j] < key) i = j+1;
                else k = j-1;
                j = (i+k)/2;
            }
        }
        return -1;

    }

    public static void runTimeTests(long  maximumTime, int min_N, int max_N){

        long lastRunTime = 0;
        long averageSequentialTime = 0;
        long averageBinaryTime = 0;
        long totalTime = 0;
        long startTime = 0;
        long endTime = 0;
        String seqActualRatio = "na";
        String seqExpectedRatio = "na";
        String binActualRatio = "na";
        String binExpectedRatio = "na";
        double seqPreviousTime = 0;
        double binPreviousTime = 0;
        int MININT = Integer.MIN_VALUE;
        int MAXINT = Integer.MAX_VALUE;
        var printSeqRatio= "na";
        var printBinRatio= "na";
        long runTimeStart =0;
        long runTimeEnd = 0;

        // Print Header
        System.out.printf("\n%20s%-20s%45s%-20s", "", "Sequential","", "Binary");
        System.out.printf("\n%-20s%-20s%-20s%-20s     %-20s%-20s%-20s\n\n", "N", "Time", "Ratio", "Expected Ratio",
                "Time", "Ratio", "Expected Ratio");

        for (int n = min_N; n < max_N; n = n*2){

            runTimeStart = getCpuTime();
            // Data Generation
            int sortedList[] = new int[n];
            sortedList = generateSortedList(n, MININT, MAXINT);
/*
            for (int test = 0; test < 10; test++){
                System.out.println(sortedList[test]);
            }
*/

            Random r = new Random();
            int randomInt = r.nextInt(((MAXINT - MININT)+MININT));

            // Run tests
            if (lastRunTime >= maximumTime){
/*
                System.out.println("Times up! at: " + lastRunTime);
                break;
*/
            }
            else {

                for (int runSeq = 0; runSeq < 10000; runSeq++){
                   startTime = getCpuTime();
                   sequentialSearch(sortedList, randomInt);
                   endTime = getCpuTime();
                   totalTime = totalTime + (endTime - startTime);
                }
                seqPreviousTime = averageSequentialTime;
                averageSequentialTime = totalTime / 10000;
                totalTime = 0;

                for (int runBin = 0; runBin < 10000; runBin++){
                   startTime = getCpuTime();
                   binarySearch(sortedList, randomInt);
                   endTime = getCpuTime();
                   totalTime = totalTime + (endTime - startTime);
                }
                binPreviousTime = averageBinaryTime;
                averageBinaryTime = totalTime / 10000;
                totalTime = 0;

                // Calculate Ratios
                if (seqPreviousTime != 0){
                    seqActualRatio= Double.toString((averageSequentialTime / seqPreviousTime));
                    seqActualRatio = seqActualRatio.substring(0, Math.min(seqActualRatio.length(), 4));
                    // https://stackoverflow.com/questions/8499698/trim-a-string-based-on-the-string-length
                }
                if (binPreviousTime != 0){
                    binActualRatio = Double.toString( (averageBinaryTime / binPreviousTime));
                    binActualRatio = binActualRatio.substring(0, Math.min(binActualRatio.length(), 4));
                }
                if (n > 1){
                    seqExpectedRatio = "~2";
                    if (n>2){
                        binExpectedRatio = String.format("~%2.2f", (double)(Math.log(n)/Math.log(2))/(Math.log(n/2)/Math.log(2)));
                    } else {
                        binExpectedRatio = "na";
                    }

                } else {
                    seqExpectedRatio = "na";
                    seqExpectedRatio = "na";
                }

                //print test results
                System.out.printf("%-20s%-20s%-20s%-20s     %-20s%-20s%-20s\n", n, averageSequentialTime, seqActualRatio, seqExpectedRatio,
                        averageBinaryTime, binActualRatio, binExpectedRatio);

                runTimeEnd = getCpuTime();
                lastRunTime = runTimeEnd - runTimeStart;
            }




        }
    }
}
