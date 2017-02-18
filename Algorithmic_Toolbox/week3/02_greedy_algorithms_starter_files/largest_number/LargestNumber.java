import java.util.*;

public class LargestNumber {
    private static String largestNumber(String[] s) {
        //write your code here
        StringBuilder result = new StringBuilder();
        // for (int i = 0; i < a.length; i++) {
        //     result += a[i];
        // }
        Arrays.sort(s, new Comparator<String>(){
            @Override
            public int compare(String a, String b){
                if (isGreaterOrEqual(a, b)>0)
                    return -1;
                else if (isGreaterOrEqual(a, b)<0)
                    return 1;
                else 
                    return 0;
            }
        }); // descending sorting
        for (int i=0; i<s.length; i++){
            result.append(s[i]);
        }
        return result.toString();
    }

    private static int isGreaterOrEqual(String a, String b){
        if (a.length()==0) return -1;
        if (a.length()==0 && b.length()==0) return 0;
        if (b.length()==0) return 1;
        int result = 0;
        if (a.charAt(0) > b.charAt(0))
            result = 1;
        else if (a.charAt(0) < b.charAt(0))
            result = -1;
        else {
            int x = Integer.parseInt(a+b), y = Integer.parseInt(b+a);
            result = x - y;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}

