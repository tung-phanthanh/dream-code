import java.io.*;
import java.util.*;

public class B {
    static final long MOD = 1_000_000_007L;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder out = new StringBuilder();
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            long[] A = new long[n];
            st = new StringTokenizer(br.readLine().trim());
            for (int i = 0; i < n; i++) {
                A[i] = Long.parseLong(st.nextToken());
            }
            long[] diff = new long[n + 2];
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine().trim());
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                diff[l - 1]++;
                if (r < n) {
                    diff[r]--;
                }
            }
            long[] count = new long[n];
            count[0] = diff[0];
            for (int i = 1; i < n; i++) {
                count[i] = count[i - 1] + diff[i];
            } //T5: c= 1234567
            // T6: c = 1111111111
            Arrays.sort(A);
            Arrays.sort(count);
            long maxSum = 0;
            for (int i = 0; i < n; i++) {
                maxSum += A[i] * count[i];
            }
            long ways = 1;
            int i = 0;
            while (i < n) {
                int j = i;
                while (j < n && count[j] == count[i]) {
                    j++;
                }
                int len = j - i;
                ways = (ways * factorial(len)) % MOD;
                i = j;
            }
            out.append(maxSum).append(' ').append(ways).append('\n');
        }
        System.out.print(out);
    }
    static long factorial(int n) {
        long a = 1;
        for (int i = 2; i <= n; i++) a = (a * i) % MOD;
        return a;
    }
}