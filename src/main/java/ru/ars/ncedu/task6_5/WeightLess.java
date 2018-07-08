package ru.ars.ncedu.task6_5;


public class WeightLess {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 5, 1, 8},
                {1, 2, 3, 7},
                {1, 10, 2, 5},
                {1, 1, 1, 1}
        };
        int[][] marker = new int[matrix.length][matrix[0].length];
        for (int k = 0; k < marker.length; k++) {
            for (int i = 0; i < marker[k].length; i++) {
                marker[k][i] = -1;
            }
        }
        marker[0][0] = matrix[0][0];

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (row != matrix.length - 1 && col != matrix[row].length - 1) {
                    if (marker[row][col + 1] == -1) {
                        marker[row][col + 1] = marker[row][col] + matrix[row][col + 1];
                    } else {
                        int val = marker[row][col] + matrix[row][col + 1];
                        if (val < marker[row][col + 1]) {
                            marker[row][col + 1] = val;
                        }
                    }

                    if (marker[row + 1][col] == -1) {
                        marker[row + 1][col] = marker[row][col] + matrix[row + 1][col];
                    } else {
                        int val = marker[row][col] + matrix[row + 1][col];
                        if (val < marker[row + 1][col]) {
                            marker[row + 1][col] = val;
                        }
                    }
                } else {
                    if (col != matrix[row].length - 1) {
                        if (marker[row][col + 1] == -1) {
                            marker[row][col + 1] = marker[row][col] + matrix[row][col + 1];
                        } else {
                            int val = marker[row][col] + matrix[row][col + 1];
                            if (val < marker[row][col + 1]) {
                                marker[row][col + 1] = val;
                            }
                        }
                    }

                    if (row != matrix.length - 1) {
                        marker[row + 1][col] = marker[row][col] + matrix[row + 1][col];
                    }
                }
            }
        }
        System.out.println(marker[marker.length - 1][marker[marker.length - 1].length - 1]);
    }
}