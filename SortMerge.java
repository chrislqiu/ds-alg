public class SortMerge {

    public static void sort(int[] arr) {
        //TO BE IMPLEMENTED
        Queue<int[]> pos = qSubArr(arr);
        Queue<int[]> arrs = new Queue<>(arr.length);

        while (!pos.isEmpty()) {
            int[] dequeuedPos;
            int[] subArr;
            dequeuedPos = pos.dequeue();
            subArr = getArr(arr, dequeuedPos[0], dequeuedPos[1]);
            arrs.enqueue(subArr);
        }
        while (arrs.size() != 1) {
            int[] array1 = arrs.dequeue();
            int[] array2 = arrs.dequeue();
            mergeArr(arrs, array1, array2);
        }
        int[] finalArr;
        finalArr = arrs.dequeue();

        for (int i = 0; i < finalArr.length; i++) {
            arr[i] = finalArr[i];
        }
    }

    static Queue<int[]> qSubArr(int[] arr) {
        Queue<int[]> queuePos = new Queue<>(arr.length);
        int start = 0;
        int end = 0;
        int curr = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] <= arr[i + 1]) {
                curr++;
            } else {
                end = curr;
                curr++;
                queuePos.enqueue(new int[]{start, end});
                start = i + 1;
            }
        }
        end = curr;
        queuePos.enqueue(new int[]{start, end});
        return queuePos;
    }
    static int[] getArr(int[] arr, int start, int end) {
        int[] out = new int[end - start + 1];
        for (int i = 0; i < out.length; i++) {
            out[i] = arr[start++];
        }
        return out;
    }

    static int[] mergeArr(Queue<int[]> arrs, int[] arr1, int[] arr2) {
        int i = 0, j = 0, k = 0;
        int [] merged = new int[arr1.length + arr2.length];
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            merged[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            merged[k++] = arr2[j++];
        }
        arrs.enqueue(merged);
        return merged;
    }
}
	
