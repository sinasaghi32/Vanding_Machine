package model;

import java.util.Arrays;

/**
 * 거스름돈 저장소.
 * BASIC CHECK: 생성자에서 기본 10/50/100/500원 동전 각 10개로 초기화한다.
 */
public class CoinStorage {
    private final int[] units = {500, 100, 50, 10};
    private final int[] counts = new int[4];

    public CoinStorage() { Arrays.fill(counts, 10); }
    public CoinStorage(int c500, int c100, int c50, int c10) {
        counts[0] = c500; counts[1] = c100; counts[2] = c50; counts[3] = c10;
    }
    public int[] getUnits() { return units.clone(); }
    public int getCount(int unit) { int i = indexOf(unit); return i >= 0 ? counts[i] : 0; }
    public void addCoin(int unit, int count) { int i = indexOf(unit); if (i >= 0) counts[i] += count; }
    public void removeCoin(int unit, int count) { int i = indexOf(unit); if (i >= 0) counts[i] = Math.max(0, counts[i] - count); }
    public boolean canMakeChange(int amount) { return calculateChange(amount) != null; }
    public int[] calculateChange(int amount) {
        int remain = amount;
        int[] result = new int[4];
        for (int i = 0; i < units.length; i++) {
            int need = remain / units[i];
            int use = Math.min(need, counts[i]);
            result[i] = use;
            remain -= use * units[i];
        }
        return remain == 0 ? result : null;
    }
    public void payChange(int[] change) { for (int i = 0; i < change.length; i++) counts[i] -= change[i]; }
    public int collectKeepingMinimum() {
        int[] keep = {5, 5, 5, 5};
        int collected = 0;
        for (int i = 0; i < units.length; i++) {
            int removable = Math.max(0, counts[i] - keep[i]);
            counts[i] -= removable;
            collected += removable * units[i];
        }
        return collected;
    }
    public int totalAmount() { int sum = 0; for (int i = 0; i < units.length; i++) sum += units[i] * counts[i]; return sum; }
    public String toCsv() { return counts[0] + "," + counts[1] + "," + counts[2] + "," + counts[3]; }
    public static CoinStorage fromCsv(String line) {
        String[] p = line.split(",");
        return new CoinStorage(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]), Integer.parseInt(p[3]));
    }
    private int indexOf(int unit) { for (int i = 0; i < units.length; i++) if (units[i] == unit) return i; return -1; }
    @Override public String toString() { return "500원=" + counts[0] + ", 100원=" + counts[1] + ", 50원=" + counts[2] + ", 10원=" + counts[3] + ", 총 " + totalAmount() + "원"; }
}
