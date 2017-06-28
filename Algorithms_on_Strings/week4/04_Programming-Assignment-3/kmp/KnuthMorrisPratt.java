import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KnuthMorrisPratt {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private int[] computePrefixFunction(String allText){
        int[] result = new int[allText.length()];
        int border = 0;
        for (int i=1; i<allText.length(); i++){
            while (border>0 && allText.charAt(i)!=allText.charAt(border)){
                border = result[border-1];
            }
            if (allText.charAt(i) == allText.charAt(border))
                border++;
            else
                border = 0;
            result[i] = border;
        }
        return  result;
    }

    // Find all the occurrences of the pattern in the text and return
    // a list of all positions in the text (starting from 0) where
    // the pattern starts in the text.
    public List<Integer> findPattern(String pattern, String text) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        // Implement this function yourself
        String unionString = pattern + '$' + text;
        int[] prefixArray = computePrefixFunction(unionString);
        for (int i=pattern.length()+1; i<unionString.length(); i++){
            if (prefixArray[i] == pattern.length())
                result.add(i-2*pattern.length());
        }
        return result;
    }

    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
    }

    public void print(List<Integer> x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
    }
}
