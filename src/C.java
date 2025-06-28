import java.io.*;
import java.util.*;

public class C {
    static long[][] dp;
    static List<Integer>[] t;
    static int[] v;

    static void dfs(int u) {
        dp[u][1] = v[u];
        long sum = 0;
        long need = Long.MIN_VALUE;
        boolean hasChild = false;

        for (int v : t[u]) {
            dfs(v);
            hasChild = true;
            dp[u][1] += dp[v][0];

            long chooseV = dp[v][1];
            long notChooseV = dp[v][0];
            sum += Math.max(chooseV, notChooseV);


            need = Math.max(need, chooseV - Math.max(chooseV, notChooseV));
        }

        if (!hasChild) {
            dp[u][0] = 0;
        } else {
            dp[u][0] = sum + need;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder res = new StringBuilder();

        while (T-- > 0) {
            int n = Integer.parseInt(br.readLine());
            v = new int[n + 1];
            t = new ArrayList[n + 1];
            dp = new long[n + 1][2];

            for (int i = 1; i <= n; i++) {
                t[i] = new ArrayList<>();
            }

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                v[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            int root = 0;
            for (int i = 1; i <= n; i++) {
                int p = Integer.parseInt(st.nextToken());
                if (p == 0) {
                    root = i;
                } else {
                    t[p].add(i);
                }
            }

            dfs(root);
            res.append(Math.max(dp[root][0], dp[root][1])).append("\n");
        }

        System.out.print(res);
    }
}
