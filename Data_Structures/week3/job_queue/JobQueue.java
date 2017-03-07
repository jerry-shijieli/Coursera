import java.io.*;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.Comparator;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    class Worker{
        public int index;
        public long nextFreeTime;

        public Worker(int index, long nextFreeTime){
            this.index = index;
            this.nextFreeTime = nextFreeTime;
        }      
    }

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        // long[] nextFreeTime = new long[numWorkers];
        // for (int i = 0; i < jobs.length; i++) {
        //     int duration = jobs[i];
        //     int bestWorker = 0;
        //     for (int j = 0; j < numWorkers; ++j) {
        //         if (nextFreeTime[j] < nextFreeTime[bestWorker])
        //             bestWorker = j;
        //     }
        //     assignedWorker[i] = bestWorker;
        //     startTime[i] = nextFreeTime[bestWorker];
        //     nextFreeTime[bestWorker] += duration;
        // }

        // assign next job to next free worker
        PriorityQueue<Worker> workers = new PriorityQueue<Worker>(numWorkers, new Comparator<Worker>(){
            @Override
            public int compare(Worker w1, Worker w2){
            if (w1.nextFreeTime < w2.nextFreeTime)
                return -1;
            else if (w1.nextFreeTime > w2.nextFreeTime)
                return 1;
            else 
                return (w1.index - w2.index);
            }
        });
        for (int i=0; i<numWorkers; i++){
            workers.offer(new Worker(i, 0));
        }
        for (int i=0; i<jobs.length; i++){
            int duration = jobs[i];
            Worker bestWorker = workers.poll();
            assignedWorker[i] = bestWorker.index;
            startTime[i] = bestWorker.nextFreeTime;
            bestWorker.nextFreeTime += duration;
            workers.offer(bestWorker);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
