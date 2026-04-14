package hust.soict.dsai.garbage;

import java.util.Random;

public class ConcatenationInLoops {
    public static void main(String[] args) {
        Random r = new Random(123);
        
        // --- TEST 1: Dung toan tu + cua String ---
        long start = System.currentTimeMillis();
        String s = "";
        for (int i = 0; i < 65536; i++) {
            s += r.nextInt(2); // Ghep chuoi bang dau + (Rat cham)
        }
        System.out.println("Thoi gian chay cua String: " + (System.currentTimeMillis() - start) + " ms");
        
        
        // --- TEST 2: Dung StringBuilder ---
        r = new Random(123); // Reset lai random
        start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 65536; i++) {
            sb.append(r.nextInt(2)); // Ghep chuoi bang StringBuilder (Sieu nhanh)
        }
        s = sb.toString();
        System.out.println("Thoi gian chay cua StringBuilder: " + (System.currentTimeMillis() - start) + " ms");
    }
}