import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class A {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        List<Long> results = new ArrayList<>();

        for (int t = 0; t < T; t++) {
            String[] parts = br.readLine().trim().split(" ");
            int N = Integer.parseInt(parts[0]);
            int X = Integer.parseInt(parts[1]);
            int Y = Integer.parseInt(parts[2]);

            long[] V = new long[N];
            String[] speedParts = br.readLine().trim().split(" ");
            for (int i = 0; i < N; i++) {
                V[i] = Long.parseLong(speedParts[i]);
            }


            double minOpponent = Double.MAX_VALUE;
            for (int i = 0; i <= N - 1; i++) {
                if (V[i] != 0) {
                    minOpponent = Math.min(minOpponent, (double) X / V[i]);
                }
            }


            double myTimeWithoutBoost = (double) X / V[N - 1];
            if (myTimeWithoutBoost < minOpponent) {
                results.add(0L);
                continue;
            }


            long low = 1, high = Y;
            long answer = -1;

            while (low <= high) {
                long mid = (low + high) / 2;
                long remaining = X - mid;

                double myTime;
                if (mid >= X) {
                    myTime = 1.0;
                } else {
                    if (V[N - 1] == 0) {
                        myTime = Double.MAX_VALUE;
                    } else {
                        myTime = 1.0 + (double) remaining / V[N - 1];
                    }
                }

                if (myTime < minOpponent) {
                    answer = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            results.add(answer);
        }


        for (long res : results) {
            System.out.println(res);
        }

    }
}
