import java.util.*;

public class PrimitiveCalculator {
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        int[] value = new int[n+1]; 
        for (int i=2; i<=n; i++){
            int minOp = Integer.MAX_VALUE;
            int op1 = Integer.MAX_VALUE;
            int op2 = Integer.MAX_VALUE;
            int op3 = Integer.MAX_VALUE;
            if (i % 3 == 0)
                op1 = value[i/3] + 1;
            if (i % 2 == 0)
                op2 = value[i/2] + 1;
            op3 = value[i-1] + 1;
            minOp = Math.min(op1, Math.min(op2, op3));
            value[i] = minOp;
        }
        int minOp = value[n];
        //System.out.println("result: "+minOp);
        int target = n;
        sequence.add(target);
        while (minOp-- > 0){
            if (target % 3 == 0 && value[target/3] == minOp){
                target /= 3;
            }
            else if (target % 2 == 0 && value[target/2] == minOp){
                target /= 2;
            }
            else if (value[target-1] == minOp){
                target--;
            }
            sequence.add(target); 
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}

