import java.io.*;
import java.util.*;

public class A {
    static int[] pos = new int[26];
    static char[] letters;
    static char[] tScore;
    static boolean[] used;
    static char[] p;
    static String result;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder out = new StringBuilder();
        while (T-- > 0) {
            String S = br.readLine().trim();
            char[] grid = new char[9];
            for (int i = 0; i < 3; i++) {
                String row = br.readLine().trim();
                for (int j = 0; j < 3; j++) {
                    grid[i * 3 + j] = row.charAt(j);
                }
            }
            Arrays.fill(pos, -1);
            for (int i = 0; i < 9; i++) {
                pos[grid[i] - 'A'] = i;
            }
            tScore = calScr(S.toCharArray());
            letters = grid.clone();
            Arrays.sort(letters);
            used = new boolean[9];
            p = new char[9];
            result = null;
            dfs(0);
            out.append(new String(tScore)).append(' ').append(result).append('\n');
        }
        System.out.print(out);
    } static void dfs(int depth) {
        if (result != null) return;
        if (depth == 9) {
            char[] score = calScr(p);
            for (int i = 0; i < 9; i++) {
                if (score[i] != tScore[i]) {
                    return;
                }
            }
            result = new String(p);
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (used[i]) continue;
            used[i] = true;
            p[depth] = letters[i];
            dfs(depth + 1);
            used[i] = false;
            if (result != null) return;
        }
    }
    static char[] calScr(char[] s) {


        // 0 1 2
        // 3 4 5
        // 6 7 8

        char[] res = new char[9];
        int coloredMask = 0;
        for (int step = 0; step < 9; step++) {
            int p = pos[s[step] - 'A'];
            coloredMask |= (1 << p);
            int score = 0;
            // row
            int row = p / 3;
            int rowMask = (1 << (row * 3))
                    | (1 << (row * 3 + 1))
                    | (1 << (row * 3 + 2));
            if ((coloredMask & rowMask) == rowMask) {
                score++;
            }
            // col
            int col = p % 3;
            int colMask = (1 << col)
                    | (1 << (col + 3))
                    | (1 << (col + 6));
            if ((coloredMask & colMask) == colMask) {
                score++;
            }
            // main
            if (p == 0 || p == 4 || p == 8) {
                int diagMask = (1 << 0) | (1 << 4) | (1 << 8);

                if ((coloredMask & diagMask) == diagMask) {
                    score++;
                }
            }
            // ot
            if (p == 2 || p == 4 || p == 6) {
                int diagMask = (1 << 2) | (1 << 4) | (1 << 6);
                if ((coloredMask & diagMask) == diagMask) {
                    score++;
                }
            }
            res[step] = (char) ('0' + score);
        }
        return res;
    }
}