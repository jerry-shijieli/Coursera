import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Random;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    // for hash function
    private static int prime = 1000000007;
    private static int multiplier;

    private static long polyHash(String s){
        long hash = 0;
        for (int i=s.length()-1; i>=0; --i){
            hash = (hash*multiplier + s.charAt(i)) % prime;
        }
        return hash;
    }

    private static long[] precomputeHashes(String text, int plength){
        long[] hashes = new long[text.length() - plength+1];
        hashes[text.length() - plength] = polyHash(text.substring(text.length() - plength));
        long y = 1;
        for (int i=0; i<plength; i++)
            y = (y * multiplier) % prime;
        for(int i=text.length()-plength-1; i>=0; --i){
            hashes[i] = (hashes[i+1]*multiplier + (int)text.charAt(i) - y * (int)text.charAt(i+plength)) % prime;
        }
        return hashes;
    }

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        Random rand = new Random();
        multiplier = rand.nextInt(prime-1) + 1;
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        long[] hashes = precomputeHashes(t, m);
        long pHash = polyHash(s);
        for (int i = 0; i + m <= n; ++i) {
    	    boolean equal = true;
    	 //    for (int j = 0; j < m; ++j) {
    		//     if (s.charAt(j) != t.charAt(i + j)) {
    		//           equal = false;
     	// 	         break;
    		// }
	       //}
            if (pHash != hashes[i])
                continue;
            if (!t.substring(i, i+m).equals(s))
                equal = false;
            if (equal)
                occurrences.add(i);
	    }
        return occurrences;
    }

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

