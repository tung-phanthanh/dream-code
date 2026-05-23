import java.io.*;
import java.util.*;

public class D {

    static class Toy {
        int id;
        long h, r;

        Toy(int id, long h, long r) {
            this.id = id;
            this.h = h;
            this.r = r;
        }
    }
//    static boolean validRadius(List<E.Toy> lt) {       //TC 6: 1 1 2 6 7           2 3 5 4 1
//        //      4 6 4 2 8
//        boolean ok1 = true;
//        for (int i = 1; i < n; i++) {
//            if (i % 2 == 1) {
//                if (!(lt.get(i - 1).r < lt.get(i).r)) {
//                    ok1 = false;
//                    break;
//                }
//            } else {
//                if (!(lt.get(i - 1).r > lt.get(i).r)) {
//                    ok1 = false;
//                    break;
//                }
//            }
//        }
//        boolean ok2 = true;
//        for (int i = 1; i < n; i++) {
//            if (i % 2 == 1) {
//                if (!(lt.get(i - 1).r > lt.get(i).r)) {
//                    ok2 = false;
//                    break;
//                }
//            } else {
//                if (!(lt.get(i - 1).r < lt.get(i).r)) {
//                    ok2 = false;
//                    break;
//                }
//            }
//        }
//        return ok1 || ok2;
//    }
//
//    static boolean validHeight(List<E.Toy> lt) {
//        int peak = 0;
//        while (peak + 1 < n &&
//                lt.get(peak).h < lt.get(peak + 1).h) {
//            peak++;
//        }
//        if (peak == 0 || peak == n - 1) {
//            return false;
//        }
//        while (peak + 1 < n &&
//                lt.get(peak).h > lt.get(peak + 1).h) {
//            peak++;
//        }
//        return peak == n - 1;
//    }
//
//    //    static void dfs(int idx, int type) {
////        if (found) return;
////        if (idx == n) {
////            if (validHeight()) {
////                found = true;
////                for (int i = 0; i < n; i++) {
////                    if (i > 0) result.append(' ');
////                    result.append(p[i] + 1);
////                }
////                result.append('\n');
////            }
////            return;
////        }
////        for (int i = 0; i < n; i++) {
////            if (used[i]) continue;
////            p[idx] = i;
//    ////            if (!validRadius(idx, type)) {
//    ////                continue;
//    ////            }
////            used[i] = true;
////            dfs(idx + 1, type);
////            used[i] = false;
////        }
////    }

    static boolean check(List<Toy> lt) {
        int n = lt.size();
        // rule 1
        int peak = -1;
        for (int i = 1; i < n; i++) {
            if (lt.get(i).h > lt.get(i - 1).h) {
                peak = i;
            } else {
                break;
            }
        }
        if (peak <= 0 || peak >= n - 1) return false;
        for (int i = peak + 1; i < n; i++) {
            if (lt.get(i).h >= lt.get(i - 1).h) {
                return false;
            }
        }
        // rule 2
        int prev = 0;
        for (int i = 1; i < n; i++) {
            long x = lt.get(i - 1).r;
            long y = lt.get(i).r;
            if (x == y) return false;
            int cur = (y > x ? 1 : -1);
            if (i >= 2 && cur == prev) {
                return false;
            }
            prev = cur;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder result = new StringBuilder();
        while (T-- > 0) {
            int n = Integer.parseInt(br.readLine());
            long[] H = new long[n];
            long[] R = new long[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                H[i] = Long.parseLong(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                R[i] = Long.parseLong(st.nextToken());
            }
            Toy[] toys = new Toy[n];
            for (int i = 0; i < n; i++) {
                toys[i] = new Toy(i + 1, H[i], R[i]);
            }
            Arrays.sort(toys, (a, b) -> {
                if (a.h != b.h) {
                    return Long.compare(a.h, b.h);
                }
                return Long.compare(a.r, b.r);
            });
            boolean found = false;
            int m = n - 1;
            for (int mask = 1; mask < (1 << m) - 1 && !found; mask++) {
                List<Toy> left = new ArrayList<>();
                List<Toy> right = new ArrayList<>();
                for (int i = 0; i < m; i++) {

                    if (((mask >> i) & 1) == 1) {
                        left.add(toys[i]);
                    } else {
                        right.add(toys[i]);
                    }
                }
                List<Toy> arr = new ArrayList<>();
                for (Toy t : left) {
                    arr.add(t);
                }
                arr.add(toys[n - 1]);
                for (int i = right.size() - 1; i >= 0; i--) {
                    arr.add(right.get(i));
                }
                if (check(arr)) {
                    for (Toy t : arr) {
                        result.append(t.id).append(' ');
                    }
                    result.append('\n');
                    found = true;
                }
            }
            if (!found) {
                result.append("-1\n");
            }
        }
        System.out.print(result);
    }
}