import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SuffixArrayMatching {
    private static final int ALPHABET_SIZE = 5;

    class fastscanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        fastscanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextint() throws IOException {
            return Integer.parseInt(next());
        }
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

    public int[] computeSuffixArray(String text) {
        int[] suffixArray;

        // write your code here
        int[] order = sortCharacters(text);
        int[] charClass = computeCharClasses(text, order);
        int step = 1;
        while (step < text.length()){
            order = sortDoubled(text, step, order, charClass);
            charClass = updateClasses(order, charClass, step);
            step *= 2;
        }

        suffixArray = order.clone();
        return suffixArray;
    }

    public List<Integer> findOccurrences(String pattern, String text, int[] suffixArray) {
        List<Integer> result;

        // write your code here
        result = new ArrayList<Integer>();
        int l = 0, r = suffixArray.length;
        while (l < r){ // find top bound
            int mid = l + (r-l)/2;
            String suffix = text.substring(suffixArray[mid]);
            if (pattern.compareTo(suffix) > 0)
                l = mid + 1;
            else
                r = mid;
        }
        int t = l;
        r = suffixArray.length-1;
        while (l < r){ // find bottom bound
            int mid = l + (r-l)/2;
            String suffix = text.substring(suffixArray[mid]);
            if (suffix.startsWith(pattern))
                l = mid + 1;
            else if (pattern.compareTo(suffix) < 0)
                r = mid;
            else
                l = mid + 1;
        }
        int b = text.substring(suffixArray[l]).startsWith(pattern)? l: l-1; // check if bottom end is valid
//        while (r >= l){
//            String suffix = text.substring(suffixArray[r]);
//            if (suffix.startsWith(pattern))
//                break;
//            else
//                r--;
//        }
//        b = r;

        for (int i=t; i<=b; i++){
            result.add(suffixArray[i]);
        }
        return result;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayMatching().run();
    }

    public void print(boolean[] x) {
        for (int i = 0; i < x.length; ++i) {
            if (x[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public void run() throws IOException {
        fastscanner scanner = new fastscanner();
        String text = scanner.next() + "$";
        int[] suffixArray = computeSuffixArray(text);
        int patternCount = scanner.nextint();
        boolean[] occurs = new boolean[text.length()];
        for (int patternIndex = 0; patternIndex < patternCount; ++patternIndex) {
            String pattern = scanner.next();
            List<Integer> occurrences = findOccurrences(pattern, text, suffixArray);
            for (int x : occurrences) {
                occurs[x] = true;
            }
        }
        print(occurs);
    }
}
