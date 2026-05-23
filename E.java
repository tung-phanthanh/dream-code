import java.io.*;
import java.util.*;

public class E {

    static class Toy {
        int h, r, idx;
        Toy(int h, int r, int idx) {
            this.h = h;
            this.r = r;
            this.idx = idx;
        }
    }
    static int n;
    static boolean validRadius(List<Toy> lt) {       //TC 6: 1 1 2 6 7           2 3 5 4 1
                                                        //      4 6 4 2 8
        boolean ok1 = true;
        for (int i = 1; i < n; i++) {
            if (i % 2 == 1) {
                if (!(lt.get(i - 1).r < lt.get(i).r)) {
                    ok1 = false;
                    break;
                }
            } else {
                if (!(lt.get(i - 1).r > lt.get(i).r)) {
                    ok1 = false;
                    break;
                }
            }
        }
        boolean ok2 = true;
        for (int i = 1; i < n; i++) {
            if (i % 2 == 1) {
                if (!(lt.get(i - 1).r > lt.get(i).r)) {
                    ok2 = false;
                    break;
                }
            } else {
                if (!(lt.get(i - 1).r < lt.get(i).r)) {
                    ok2 = false;
                    break;
                }
            }
        }
        return ok1 || ok2;
    }

    static boolean validHeight(List<Toy> lt) {
        int peak = 0;
        while (peak + 1 < n &&
                lt.get(peak).h < lt.get(peak + 1).h) {
            peak++;
        }
        if (peak == 0 || peak == n - 1) {
            return false;
        }
        while (peak + 1 < n &&
                lt.get(peak).h > lt.get(peak + 1).h) {
            peak++;
        }
        return peak == n - 1;
    }

    //    static void dfs(int idx, int type) {
//        if (found) return;
//        if (idx == n) {
//            if (validHeight()) {
//                found = true;
//                for (int i = 0; i < n; i++) {
//                    if (i > 0) result.append(' ');
//                    result.append(p[i] + 1);
//                }
//                result.append('\n');
//            }
//            return;
//        }
//        for (int i = 0; i < n; i++) {
//            if (used[i]) continue;
//            p[idx] = i;
    ////            if (!validRadius(idx, type)) {
    ////                continue;
    ////            }
//            used[i] = true;
//            dfs(idx + 1, type);
//            used[i] = false;
//        }
//    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder result = new StringBuilder();
        while (T-- > 0) {
            n = Integer.parseInt(br.readLine());
            int[] H = new int[n];
            int[] R = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                H[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                R[i] = Integer.parseInt(st.nextToken());
            }
            Toy[] toys = new Toy[n];
            for (int i = 0; i < n; i++) {
                toys[i] = new Toy(H[i], R[i], i + 1);
            }
            Arrays.sort(toys, (a, b) -> a.h - b.h);
            boolean found = false;
            for (int peak = 1; peak < n - 1 && !found; peak++) {
                List<Toy> lt = new ArrayList<>();
                for (int i = 0; i < peak; i++) {
                    lt.add(toys[i]);
                }
                lt.add(toys[n - 1]);
                for (int i = peak; i < n - 1; i++) {
                    lt.add(toys[n - 1 - (i - peak) - 1]);
                }
                if (validRadius(lt) && validHeight(lt)) {
                    found = true;
                    for (int i = 0; i < n; i++) {
                        if (i > 0) result.append(' ');
                        result.append(lt.get(i).idx);
                    }
                    result.append('\n');
                }
            }
            if (!found) {
                result.append("-1\n");
            }
        }
        System.out.print(result);
    }
}