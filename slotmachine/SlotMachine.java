package slotmachine;

import java.math.BigDecimal;
import java.util.*;

public class SlotMachine {

    public static void main(String[] args) {
        List<String[]> bgReelsA = new ArrayList<>(5);
        List<Integer> stopPosition = new ArrayList<>();
        bgReelsA.add(new String[]{"sym2", "sym7", "sym7", "sym1", "sym1", "sym5", "sym1", "sym4", "sym5", "sym3", "sym2", "sym3", "sym8", "sym4", "sym5", "sym2", "sym8", "sym5", "sym7", "sym2"});
        bgReelsA.add(new String[]{"sym1", "sym6", "sym7", "sym6", "sym5", "sym5", "sym8", "sym5", "sym5", "sym4", "sym7", "sym2", "sym5", "sym7", "sym1", "sym5", "sym6", "sym8", "sym7", "sym6", "sym3", "sym3", "sym6", "sym7", "sym3"});
        bgReelsA.add(new String[]{"sym5", "sym2", "sym7", "sym8", "sym3", "sym2", "sym6", "sym2", "sym2", "sym5", "sym3", "sym5", "sym1", "sym6", "sym3", "sym2", "sym4", "sym1", "sym6", "sym8", "sym6", "sym3", "sym4", "sym4", "sym8", "sym1", "sym7", "sym6", "sym1", "sym6"});
        bgReelsA.add(new String[]{"sym2", "sym6", "sym3", "sym6", "sym8", "sym8", "sym3", "sym6", "sym8", "sym1", "sym5", "sym1", "sym6", "sym3", "sym6", "sym7", "sym2", "sym5", "sym3", "sym6", "sym8", "sym4", "sym1", "sym5", "sym7"});
        bgReelsA.add(new String[]{"sym7", "sym8", "sym2", "sym3", "sym4", "sym1", "sym3", "sym2", "sym2", "sym4", "sym4", "sym2", "sym6", "sym4", "sym1", "sym6", "sym1", "sym6", "sym4", "sym8"});

        int stake = 1;
        int boardHeight = 3;
        int boardWidth = 5;

        Random rng = new Random();
        List<String[]> slotFace = new ArrayList<>();
        int stopPos;
        for (String[] reel : bgReelsA) {
            stopPos = rng.nextInt(reel.length); //
            String[] slotFaceReel = selectReels(3, reel, stopPos);
            stopPosition.add(stopPos);
            slotFace.add(slotFaceReel);
        }
        System.out.println("Stop Positions:" + stopPosition);
        System.out.println("Screen:");


        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {

                System.out.print(" " + slotFace.get(col)[row]);
            }
            System.out.println();
        }

        calculateWin(slotFace, stake, boardHeight, boardWidth);


    }

    private static String[] selectReels(int boardHeight, String[] reel, int position) {
        position = 19;
        String[] boardReel = new String[boardHeight];
        for(int i = 0; i < boardHeight; i++){
            boardReel[i] = reel[(position + i) % reel.length];
        }
        return boardReel;
    }

    private static Map<String, SlotSymbolWaysPayConfig> getPayout() {

        return Map.of(
                "sym1", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3))),
                "sym2", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3))),
                "sym3", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(5))),
                "sym4", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(5), BigDecimal.valueOf(10))),
                "sym5", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(15))),
                "sym6", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(15))),
                "sym7", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(20))),
                "sym8", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(50)))
        );
    }

    private static void calculateWin(List<String[]> slotFace, int stake, int boardHeight, int boardWidth) {
        BigDecimal totalWin = BigDecimal.ZERO;
        List<WinData> winDataList = new ArrayList<>();

            for (int row = 0; row < boardHeight; row++) {

                String symToCompare = slotFace.get(0)[row]; // only first column elements need to be compared.
                boolean exists = winDataList.stream().anyMatch(sym -> sym.getSymbolName().equals(symToCompare)); // if the symbol is already compared
                if (!winDataList.isEmpty() && exists) {
                    continue;
                }

                WinData winData = checkForWinCombination(symToCompare, boardHeight, boardWidth, slotFace);
                populateWin(winData, winDataList, stake);
                if (winData.getWinAmount() != null) {
                    totalWin = totalWin.add(winData.getWinAmount());
                }
            }
        System.out.println("Total wins:" + totalWin);

        for (WinData win : winDataList) {

            System.out.println("- Ways win " + win.getPosList() + ", " + win.getSymbolName() + " X" + win.getSymCountOnEachCol().size() + ", " + win.getWinAmount() + ", Ways: " + win.getWays());
        }

    }

    private static void populateWin(WinData winData, List<WinData> winDataList, int stake) {
        SlotSymbolWaysPayConfig payOut = getPayout().get(winData.getSymbolName());
        BigDecimal symbolWin;
        int ways;
        if (payOut != null && winData.getSymCountOnEachCol().size() >= payOut.getMinimumMatch()) {
            symbolWin = payOut.getWinAmount(winData.getSymCountOnEachCol().size());

            ways = 1;
            for (Map.Entry<Integer, Integer> entry : winData.getSymCountOnEachCol().entrySet()) {
                ways *= entry.getValue();
            }
            BigDecimal finalWin = symbolWin.multiply(BigDecimal.valueOf(ways));
            winData.setWinAmount(finalWin.multiply(BigDecimal.valueOf(stake))); // multiply with stake
            winData.setWays(ways);
            winData.setBasePayout(symbolWin);
            winDataList.add(winData);
        }
    }

    private static WinData checkForWinCombination(String symToCompare, int boardHeight, int boardWidth, List<String[]> slotFace) {

        WinData winData = new WinData();
        List<Integer> posList = new ArrayList<>();
        Map<Integer, Integer> symCountPerColMap = new HashMap<>();
        int currentCol = 0;

        for (int col = 0; col < boardWidth; col++) {
            int symCountPerColumn = 0;
            int pos = col;
            if (col - currentCol > 1)
                break;
            for (int row = 0; row < boardHeight; row++) {
                String currentSym = slotFace.get(col)[row];

                if (symToCompare.equals(currentSym)) {

                    symCountPerColumn++;
                    symCountPerColMap.put(col, symCountPerColumn);

                    posList.add(pos);

                    currentCol = col;
                }
                pos += 5;
            }
        }
        winData.setPosList(posList);
        winData.setSymCountOnEachCol(symCountPerColMap);
        winData.setSymbolName(symToCompare);
        return winData;
    }


}
