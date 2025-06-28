import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class B {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> outputs = new ArrayList<>();

        int T = Integer.parseInt(br.readLine().trim());

        for (int test = 0; test < T; test++) {
            int N = Integer.parseInt(br.readLine().trim());
            String[] S = new String[N];
            String[] R = new String[N];

            for (int i = 0; i < N; i++) {
                S[i] = br.readLine().trim();
                R[i] = new StringBuilder(S[i]).reverse().toString();
            }

            String[][] dp = new String[N][2];
            for (int i = 0; i < N; i++) Arrays.fill(dp[i], null);

            dp[0][0] = "0";
            dp[0][1] = "1";

            for (int i = 1; i < N; i++) {
                for (int prev = 0; prev <= 1; prev++) {
                    if (dp[i - 1][prev] == null) continue;
                    String prevStr = prev == 0 ? S[i - 1] : R[i - 1];

                    for (int curr = 0; curr <= 1; curr++) {
                        String currStr = curr == 0 ? S[i] : R[i];
                        if (prevStr.compareTo(currStr) <= 0) {
                            String candidate = dp[i - 1][prev] + curr;
                            if (dp[i][curr] == null || candidate.compareTo(dp[i][curr]) < 0) {
                                dp[i][curr] = candidate;
                            }
                        }
                    }
                }
            }

            String res = dp[N - 1][0];
            if (dp[N - 1][1] != null && (res == null || dp[N - 1][1].compareTo(res) < 0)) {
                res = dp[N - 1][1];
            }
            outputs.add(res);
        }

        // In toàn bộ kết quả sau khi xử lý xong
        for (String line : outputs) {
            System.out.println(line);
        }
    }
}
