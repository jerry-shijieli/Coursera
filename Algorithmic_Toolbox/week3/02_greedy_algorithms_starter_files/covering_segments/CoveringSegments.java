import java.util.*;

public class CoveringSegments {

    private static int[] optimalPoints(Segment[] segments) {
        //write your code here
        Arrays.sort(segments, new Comparator<Segment>(){
            @Override
            public int compare(Segment a, Segment b){
                if (a.start < b.start)
                    return -1;
                else if (a.start > b.start)
                    return 1;
                else 
                    return 0;
            }
        });
        List<Integer> schedules = new ArrayList<Integer>();
        int index = 0;
        while (index < segments.length){
            int endMin = Integer.MAX_VALUE;
            for (int i=index; i<segments.length; i++){
                if (segments[i].end < endMin){
                    endMin = segments[i].end;
                }
            }
            int id = index;
            for (; id<segments.length; id++){
                if (segments[id].start > endMin)
                    break;
            }
            if (id != index)
                schedules.add(endMin);
            index=id;
        }
        //int[] points = new int[2 * segments.length];
        // for (int i = 0; i < segments.length; i++) {
        //     points[2 * i] = segments[i].start;
        //     points[2 * i + 1] = segments[i].end;
        // }
        int[] points = new int[schedules.size()];
        for (int i=0; i<schedules.size(); i++)
            points[i] = schedules.get(i);
        return points;
    }

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        int[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}
 
