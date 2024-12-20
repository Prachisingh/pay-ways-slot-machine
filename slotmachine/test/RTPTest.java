package slotmachine.test;

import slotmachine.SlotMachine;
import slotmachine.WinData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RTPTest {

    static int runs = 1000_000;
    static int finishedCount = 0;
    static long startingTime ;



    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService;
        int numOfAvailableThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("available number of threads =  " + numOfAvailableThreads);
        startingTime = System.currentTimeMillis();
        executorService = Executors.newFixedThreadPool(numOfAvailableThreads);
        int stake1 = 1;
        int stake2 = 2;
        int stake3 = 3;

        CountDownLatch countDownLatch = new CountDownLatch(3);


        executorService.submit(() -> playGame(stake1, countDownLatch));
        executorService.submit(() -> playGame(stake2, countDownLatch));
        executorService.submit(() -> playGame(stake3, countDownLatch));
        executorService.shutdown();
        countDownLatch.await();
        long timeTakes = System.currentTimeMillis() - startingTime;
        System.out.println("Over all Time taken by thread " + timeTakes);

    }

    private static void playGame(int stake, CountDownLatch latch) {
        long time = System.currentTimeMillis();
        List<WinData> roundWin = new ArrayList<>();
        BigDecimal baseGameWinCounter = BigDecimal.ZERO;
        BigDecimal totalWin = BigDecimal.ZERO;
        for (int i = 0; i < runs; i++) {
             roundWin = SlotMachine.play(stake);
            for (WinData win : roundWin) {
                totalWin = totalWin.add(win.getWinAmount());
            }
            if(!roundWin.isEmpty()){
               baseGameWinCounter =  baseGameWinCounter.add(BigDecimal.ONE);
            }

        }
        int totalStake = stake * runs;
       long timeTakes =  System.currentTimeMillis() - time;
        System.out.println("time taken : " + timeTakes );
        BigDecimal rtp = totalWin.divide(BigDecimal.valueOf(totalStake));
        System.out.println("RTP is " + rtp.multiply(BigDecimal.valueOf(10)) + "%");
        System.out.println("BaseGameHitRate : " + baseGameWinCounter.divide(BigDecimal.valueOf(runs)));
//        finished();
        latch.countDown();
    }

    private static synchronized  void finished(){
        finishedCount++;
        if(finishedCount == 3){
            long timeTakes = System.currentTimeMillis() - startingTime;
            System.out.println("Over all Time taken by thread " + timeTakes);
        }
    }
}
