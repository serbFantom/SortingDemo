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
        System.out.println("{");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + ", ");
            }
            System.out.print("\n");
        }
        System.out.println("}");
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

    //TODO: bubbleSort(int[][] a)
    public static int[][] bubbleSort(int[][] arr) {
        System.out.println("ArrayUtil start bubbleSort a.length= "+arr.length);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                for (int k=0; k < arr.length; k++) {
                    for (int l = 0; l < arr[k].length; l++) {
                        //System.out.println("arr["+k+"].length= "+arr[k].length);
                        if (l != arr[k].length-1 && (arr[k][l+1] < arr[k][l])) {
                            //swap
                            int tmp = arr[k][l+1];
                            arr[k][l+1] = arr[k][l];
                            arr[k][l] = tmp;
                        }

                    }
                    if (k != arr.length-1 && (arr[k+1][0] < arr[k][0])) {
                        //swap
                        int tmp = arr[k+1][0];
                        arr[k+1][0] = arr[k][0];
                        arr[k][0] = tmp;
                    }

                }
            }
        }
        return arr;
    }
}
