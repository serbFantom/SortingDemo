/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serb.sortingdemo;

/**
 *
 * @author S.Bezuglyi
 */
public class ArrayUtil {

    static void printValues(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println("\n");
    }

    static void printValues(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println("\n");
    }

    static void bubbleSort(int[] arr) {
        printValues(arr);
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        printValues(arr);
    }
    //http://www.cyberforum.ru/java-j2se/thread141128.html
    public static int[][] bubbleSort(int[][] a) {
        int len = a.length;
        for (int k = 0; k < len; k++) {
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < a[i].length - 1; j++) {
                    if (a[i][j] > a[i][j + 1]) {
                        int temp = a[i][j];
                        a[i][j] = a[i][j + 1];
                        a[i][j + 1] = temp;
                    }
                }
            }
        }
        return a;
    }
}
