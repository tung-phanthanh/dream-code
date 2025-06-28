import java.io.*;
import java.util.*;

public class E {
    static final int MOD = 1_000_000_007;

    static int n, m, x;
    static long C, D;
    static int[] A, B;
    static Map<String, Integer> memo;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder output = new StringBuilder();

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            C = Long.parseLong(st.nextToken());
            D = Long.parseLong(st.nextToken());

            A = new int[n];
            B = new int[m];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) A[i] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < m; i++) B[i] = Integer.parseInt(st.nextToken());

            memo = new HashMap<>();
            long result = 0;

            // Start from each A[i]
            for (int i = 0; i < n; i++) {
                int maskA = (1 << i);
                long len = A[i];
                if (len >= C && len <= D) result++;
                result = (result + dfs(0, maskA, 0, len)) % MOD;
            }

            // Start from each B[j]
            for (int j = 0; j < m; j++) {
                int maskB = (1 << j);
                long len = B[j];
                if (len >= C && len <= D) result++;
                result = (result + dfs(1, 0, maskB, len)) % MOD;
            }

            output.append(result).append("\n");
        }

        System.out.print(output);
    }

    // curMountain: 0 = A, 1 = B
    static int dfs(int curMountain, int maskA, int maskB, long totalLen) {
        // Compress totalLen to reduce memory (bucketed memo key)
        long compressedLen = totalLen / 10;
        String key = curMountain + "," + maskA + "," + maskB + "," + compressedLen;
        if (memo.containsKey(key)) return memo.get(key);

        int res = 0;

        if (curMountain == 0) {
            // A → B
            for (int j = 0; j < m; j++) {
                if ((maskB & (1 << j)) != 0) continue;
                long newLen = totalLen + x + B[j];
                if (newLen > D) continue;
                if (newLen >= C) res = (res + 1) % MOD;
                res = (res + dfs(1, maskA, maskB | (1 << j), newLen)) % MOD;
            }
        } else {
            // B → A
            for (int i = 0; i < n; i++) {
                if ((maskA & (1 << i)) != 0) continue;
                long newLen = totalLen + x + A[i];
                if (newLen > D) continue;
                if (newLen >= C) res = (res + 1) % MOD;
                res = (res + dfs(0, maskA | (1 << i), maskB, newLen)) % MOD;
            }
        }

        memo.put(key, res);
        return res;
    }
}
