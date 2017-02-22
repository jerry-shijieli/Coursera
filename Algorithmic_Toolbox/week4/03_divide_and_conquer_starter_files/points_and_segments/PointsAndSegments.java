import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

public class PointsAndSegments {
    public static class Segment{
        public int start;
        public int end;

        Segment(int s, int e){
            this.start = s;
            this.end = e;
        }

        public int isInside(int point){
            return (point>=this.start && point<=this.end)? 1: 0;
        }
    }

    private static int[] fastCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        //write your code here
        Segment[] segs = new Segment[starts.length];
        for (int i=0; i<starts.length; i++){
            segs[i] = new Segment(starts[i], ends[i]);
        }
        Arrays.sort(segs, new Comparator<Segment>(){
            @Override
            public int compare(Segment s1, Segment s2){
                if (s1.start < s2.start)
                    return -1;
                else if (s1.start > s2.start)
                    return 1;
                else 
                    return 0;
            }
        });
        // for (int i=0; i<segs.length; i++)
        //     System.out.println(segs[i].start+"--"+segs[i].end);
        for (int i=0; i<points.length; i++){
            int left=0, right=segs.length-1, index=0;
            while (left <= right){
                index = left + (right-left)/2;
                if (segs[index].start == points[i])
                    break;
                else if (segs[index].start < points[i])
                    left = index+1;
                else 
                    right = index-1;
            }
            for (int j=0; j<index; j++){
                if (segs[j].start > points[i])
                    break;
                if (segs[j].end < points[i])
                    continue;
                cnt[i] += segs[j].isInside(points[i]);
            }
        }
        return cnt;
    }

    private static int[] naiveCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < starts.length; j++) {
                if (starts[j] <= points[i] && points[i] <= ends[j]) {
                    cnt[i]++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        //use fastCountSegments
        // int[] cnt = naiveCountSegments(starts, ends, points);
        int[] cnt = fastCountSegments(starts, ends, points);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
        //System.out.println();
    }
}

