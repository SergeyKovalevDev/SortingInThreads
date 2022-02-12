import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        (new Main()).taskStarter();
    }

    private void taskStarter() throws InterruptedException {
        int lengthOfArray = 9;
        int[] array = arrayOfRandIntGenerator(lengthOfArray);
        System.out.println("Source array " + Arrays.toString(array));
        int[] array1 = Arrays.copyOfRange(array, 0, lengthOfArray/2);
        System.out.println("First part of array " + Arrays.toString(array1));
        int[] array2 = Arrays.copyOfRange(array, lengthOfArray/2, lengthOfArray);
        System.out.println("Second part array " + Arrays.toString(array2));
        Thread th1 = new Thread(() -> sorting(array1));
        Thread th2 = new Thread(() -> sorting(array2));
        th1.start();
        th2.start();
        th1.join();
        th2.join();
        System.out.println("Result array " + Arrays.toString(arraysMerge(array1, array2)));
//        int[] sortedArray = arraysMerge(array1, array2);
    }

    private int[] arrayOfRandIntGenerator(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * 100000);
        }
        return array;
    }

    private void sorting(int[] array) {
        Arrays.sort(array);
    }

    private int[] arraysMerge(int[] array1, int[] array2) {
        int[] resultArray = new int[array1.length + array2.length];
        int i = 0, j = 0, r = 0;
        while (i < array1.length && j < array2.length) {
            if (array1[i] < array2[j]) {
                resultArray[r] = array1[i];
                i++;
            } else {
                resultArray[r] = array2[j];
                j++;
            }
            r++;
        }
        if (i < array1.length) System.arraycopy(array1, i, resultArray, r, array1.length - i);
        if (j < array2.length) System.arraycopy(array2, j, resultArray, r, array2.length - j);
        return resultArray;
    }
}
