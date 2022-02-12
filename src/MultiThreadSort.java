import java.util.Arrays;

public class MultiThreadSort {

    public static void main(String[] args) throws InterruptedException {
        MultiThreadSort multiThreadSort = new MultiThreadSort();
        multiThreadSort.taskStarter(15);
        multiThreadSort.taskStarter(50000000);
    }

    private void taskStarter(int lengthOfArray) throws InterruptedException {
        int[] sourceArray = arrayOfRandIntGenerator(lengthOfArray);
        if (sourceArray.length < 20) {
            System.out.println("Source array " + Arrays.toString(sourceArray));
        }
        System.out.println("Time spent sorting in the \"main\" thread " + sortInMainThread(sourceArray) + " ms");
        System.out.println("Time spent sorting in two separate threads th1 and th2 " + sortInTwoSeparateThreads(sourceArray) + " ms");
    }

    private int[] arrayOfRandIntGenerator(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        return array;
    }

    private long sortInMainThread(int[] sourceArray) {
        int[] copyOfArray = Arrays.copyOf(sourceArray, sourceArray.length);
        long timestamp1 = System.currentTimeMillis();
        Arrays.sort(copyOfArray);
        long timestamp2 = System.currentTimeMillis();
        if (sourceArray.length < 20) {
            System.out.print("Result array after sort in the \"main\" thread ");
            System.out.println(Arrays.toString(copyOfArray));
        }
        return timestamp2 - timestamp1;
    }

    private long sortInTwoSeparateThreads(int[] sourceArray) throws InterruptedException {
        int[] copyOfArray1 = Arrays.copyOfRange(sourceArray, 0, sourceArray.length/2);
        int[] copyOfArray2 = Arrays.copyOfRange(sourceArray, sourceArray.length/2, sourceArray.length);
        Thread th1 = new Thread(() -> Arrays.sort(copyOfArray1));
        Thread th2 = new Thread(() -> Arrays.sort(copyOfArray2));
        long timestamp1 = System.currentTimeMillis();
        th1.start();
        th2.start();
        th1.join();
        th2.join();
        int[] resultArray = arraysMerge(copyOfArray1, copyOfArray2);
        long timestamp2 = System.currentTimeMillis();
        if (resultArray.length < 20) {
            System.out.print("Result array after sort in two separate threads ");
            System.out.println(Arrays.toString(resultArray));
        }
        return timestamp2 - timestamp1;
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
