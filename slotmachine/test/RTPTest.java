package slotmachine.test;

import slotmachine.SlotMachine;
import slotmachine.WinData;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RTPTest {

    static int runs = 1000_000_0;


    public static void main(String[] args) {
        ExecutorService executorService;
        int numOfAvailableThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("available number of threads =  " + numOfAvailableThreads);

        executorService = Executors.newFixedThreadPool(numOfAvailableThreads);
        int stake1 = 1;
        int stake2 = 2;
        int stake3 = 3;
        executorService.submit(() -> playGame(stake1));
        executorService.submit(() -> playGame(stake2));
        executorService.submit(() -> playGame(stake3));
    }

    private static void playGame(int stake) {
        BigDecimal totalWin = BigDecimal.ZERO;
        for (int i = 0; i < runs; i++) {
            List<WinData> roundWin = SlotMachine.play(stake);
            for (WinData win : roundWin) {
                totalWin = totalWin.add(win.getWinAmount());
            }
        }
        int totalStake = stake * runs;
        BigDecimal rtp = totalWin.divide(BigDecimal.valueOf(totalStake));
        System.out.println("RTP is " + rtp);
    }
}
