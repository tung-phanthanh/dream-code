import java.io.*;
import java.util.*;

public class D {
    static int n, m, x;
    static long C, D;
    static int[] east, west;
    static long[] sumEast, sumWest;
    static final int MOD = 1_000_000_007;

    static class State {
        int maskE, maskW, last;
        public State(int maskE, int maskW, int last) {
            this.maskE = maskE;
            this.maskW = maskW;
            this.last = last;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return maskE == s.maskE && maskW == s.maskW && last == s.last;
        }

        @Override
        public int hashCode() {
            return Objects.hash(maskE, maskW, last);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder output = new StringBuilder();

        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            C = Long.parseLong(st.nextToken());
            D = Long.parseLong(st.nextToken());

            east = new int[n];
            west = new int[m];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) east[i] = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < m; i++) west[i] = Integer.parseInt(st.nextToken());

            int sizeEast = 1 << n;
            int sizeWest = 1 << m;

            // Precompute sums
            sumEast = new long[sizeEast];
            sumWest = new long[sizeWest];
            for (int mask = 0; mask < sizeEast; mask++) {
                long sum = 0;
                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) sum += east[i];
                }
                sumEast[mask] = sum;
            }
            for (int mask = 0; mask < sizeWest; mask++) {
                long sum = 0;
                for (int i = 0; i < m; i++) {
                    if ((mask & (1 << i)) != 0) sum += west[i];
                }
                sumWest[mask] = sum;
            }

            Map<State, Long> dp = new HashMap<>();

            // Start with one East trail
            for (int i = 0; i < n; i++) {
                int maskE = 1 << i;
                dp.put(new State(maskE, 0, 0), 1L);
            }
            // Start with one West trail
            for (int i = 0; i < m; i++) {
                int maskW = 1 << i;
                dp.put(new State(0, maskW, 1), 1L);
            }

            Queue<State> queue = new ArrayDeque<>(dp.keySet());

            while (!queue.isEmpty()) {
                State s = queue.poll();
                long ways = dp.get(s);
                if (ways == 0) continue;

                if (s.last == 0) { // Last was East, try West
                    for (int j = 0; j < m; j++) {
                        if ((s.maskW & (1 << j)) == 0) {
                            int newMaskW = s.maskW | (1 << j);
                            State next = new State(s.maskE, newMaskW, 1);
                            if (!dp.containsKey(next)) queue.add(next);
                            dp.put(next, (dp.getOrDefault(next, 0L) + ways) % MOD);
                        }
                    }
                } else { // Last was West, try East
                    for (int i = 0; i < n; i++) {
                        if ((s.maskE & (1 << i)) == 0) {
                            int newMaskE = s.maskE | (1 << i);
                            State next = new State(newMaskE, s.maskW, 0);
                            if (!dp.containsKey(next)) queue.add(next);
                            dp.put(next, (dp.getOrDefault(next, 0L) + ways) % MOD);
                        }
                    }
                }
            }

            long result = 0;
            for (Map.Entry<State, Long> entry : dp.entrySet()) {
                State s = entry.getKey();
                long ways = entry.getValue();
                int countE = Integer.bitCount(s.maskE);
                int countW = Integer.bitCount(s.maskW);
                if (countE + countW == 0) continue;

                long totalLength = sumEast[s.maskE] + sumWest[s.maskW] + (long)(countE + countW - 1) * x;
                if (totalLength >= C && totalLength <= D) {
                    result = (result + ways) % MOD;
                }
            }

            output.append(result).append("\n");
        }

        System.out.print(output);
    }
}
