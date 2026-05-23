import java.io.*;
import java.util.*;

public class C {
    static final long MOD = 1_000_000_007L;
    static int R, C, N;
    static char[][] rec;
    static String fav;
    static int[][][] pref;
    static long[][][] memo;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder out = new StringBuilder();
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            rec = new char[R][C];
            for (int i = 0; i < R; i++) {
                rec[i] = br.readLine().toCharArray();
            }
            fav = br.readLine();
            buildPrefix();
            memo = new long[N][R][C];
            for (int k = 0; k < N; k++) {
                for (int i = 0; i < R; i++) {
                    Arrays.fill(memo[k][i], -1);
                }
            }
            out.append(dfs(0, 0, 0)).append('\n');
        }
        System.out.print(out);
    }

    static long dfs(int k, int r, int c) {                                      // S .
                                                                                // B R  SBR      dfs(000) -> dfs (110) B R ->
                                                                                //   SBR                      BR
        if (memo[k][r][c] != -1) {
            return memo[k][r][c];
        }
//        System.out.println(k+""+r+""+c);
        char need = fav.charAt(k);
        // last
        if (k == N - 1) {
            return memo[k][r][c] = hasFruit(r, c, R - 1, C - 1, need) ? 1 : 0;
        }
        long ans = 0;
        // hori
        for (int nr = r; nr < R - 1; nr++) {
            if (hasFruit(r, c, nr, C - 1, need)) {
                ans += dfs(k + 1, nr + 1, c);
                ans %= MOD;
            }
        }
        // verti
        for (int nc = c; nc < C - 1; nc++) {
            if (hasFruit(r, c, R - 1, nc, need)) {
                ans += dfs(k + 1, r, nc + 1);
                ans %= MOD;
            }
        }
        return memo[k][r][c] = ans;
    }

    static void buildPrefix() {
        pref = new int[3][R + 1][C + 1];
        for (int t = 0; t < 3; t++) {
            char fruit = (t == 0 ? 'S' : (t == 1 ? 'B' : 'R'));
            for (int i = 1; i <= R; i++) {
                for (int j = 1; j <= C; j++) {
                    pref[t][i][j] = pref[t][i - 1][j] + pref[t][i][j - 1] - pref[t][i - 1][j - 1];
                    if (rec[i - 1][j - 1] == fruit) {
                        pref[t][i][j]++;
                    }
                }
            }
        }
    }

    //S...          hor -> valid -> .SR. -> dfs 110 -> hor row 2 -> valid -> dfs 220 -> invalid -> 110 ->
    //.SR.                          ...B
    //...B
    //SSRB


    static boolean hasFruit(int r1, int c1, int r2, int c2, char fruit) {
        int t = (fruit == 'S' ? 0 : (fruit == 'B' ? 1 : 2));
        int a = pref[t][r2 + 1][c2 + 1] - pref[t][r1][c2 + 1] - pref[t][r2 + 1][c1] + pref[t][r1][c1];
        return a > 0;
    }
}