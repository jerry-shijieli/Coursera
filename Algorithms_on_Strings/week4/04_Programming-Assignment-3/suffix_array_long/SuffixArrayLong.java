import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixArrayLong {
    private static final int ALPHABET_SIZE = 5;

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

    public class Suffix implements Comparable {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    public int[] computeSuffixArray(String text) {
        int[] result = new int[text.length()];

        // write your code here
        int[] order = sortCharacters(text);
        int[] charClass = computeCharClasses(text, order);
        int step = 1;
        while (step < text.length()){
            order = sortDoubled(text, step, order, charClass);
            charClass = updateClasses(order, charClass, step);
            step *= 2;
        }

        result = order.clone();
        return result;
    }

    private int[] updateClasses(int[] order, int[] charClass, int step) {
        int[] newClass = new int[order.length];
        newClass[order[0]] = 0;
        for (int i=1; i<order.length; i++){
            int cur=order[i], prev=order[i-1];
            int mid=(cur+step)%order.length, midPrev=(prev+step)%order.length;
            if (charClass[cur]!=charClass[prev] || charClass[mid]!=charClass[midPrev])
                newClass[cur] = newClass[prev] + 1;
            else
                newClass[cur] = newClass[prev];
        }
        return newClass;
    }

    private int[] sortDoubled(String text, int step, int[] order, int[] charClass) {
        int[] count = new int[text.length()];
        int[] newOrder = new int[text.length()];
        for (int i=0; i<text.length(); i++){
            count[charClass[i]]++;
        }
        for (int i=1; i<text.length(); i++){
            count[i] += count[i-1];
        }
        for (int i=text.length()-1; i>=0; i--){
            int start = (order[i] - step + text.length()) % text.length();
            int cl = charClass[start];
            count[cl]--;
            newOrder[count[cl]] = start;
        }
        return newOrder;
    }

    private int[] computeCharClasses(String text, int[] order) {
        int[]  charClass = new int[text.length()];
        if (text==null || text.length()==0) return charClass;
        charClass[order[0]] = 0;
        for (int i=1; i<text.length(); i++){
            if (text.charAt(order[i]) != text.charAt(order[i-1]))
                charClass[order[i]] = charClass[order[i-1]] + 1;
            else
                charClass[order[i]] = charClass[order[i-1]];
        }
        return charClass;
    }

    private int[] sortCharacters(String text) {
        int[] order = new int[text.length()];
        int[] count = new int[ALPHABET_SIZE];
        if (text==null || text.length()==0) return order;
        for (int i=0; i<text.length(); i++){
            int code = c2n(text.charAt(i));
            count[code]++;
        }
        for (int i=1; i<ALPHABET_SIZE; i++){
            count[i] += count[i-1];
        }
        for (int i=text.length()-1; i>=0; i--){
            int code = c2n(text.charAt(i));
            count[code]--;
            order[count[code]] = i;
        }
        return order;
    }
    private int c2n(char letter){ // letter/character to number
        switch (letter){
            case '$': return 0;
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            default: return -1;
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }
}
