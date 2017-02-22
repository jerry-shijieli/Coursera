import java.util.*;

public class Inversions {

    private static long getNumberOfInversions(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left + 1) {
            return numberOfInversions;
        }
        int ave = (left + right) / 2;
        numberOfInversions += getNumberOfInversions(a, b, left, ave);
        numberOfInversions += getNumberOfInversions(a, b, ave, right);
        //write your code here
        for (int m=0; left+m<ave; m++){
            for (int n=0; ave+n<right; n++){
                if (a[left+m] > a[ave+n])
                    numberOfInversions++;
            }
        }
        int i=0, j=0;
        int[] tmp = new int[right-left];
        while (left+i<ave && ave+j<right){
            if (b[left+i] <= b[ave+j]){
                tmp[i+j] = b[left+i];
                i++;
            } else {
                tmp[i+j] = b[ave+j];
                j++;
            }
        }
        while (left+i<ave){
            tmp[i+j] = b[left+i];
            i++;
        }
        while (ave+j<right){
            tmp[i+j] = b[ave+j];
            j++;
        }
        for (i=0; i<right-left; i++){
            b[left+i] = tmp[i];
        }
        return numberOfInversions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        System.out.println(getNumberOfInversions(a, b, 0, a.length));
    }
}

