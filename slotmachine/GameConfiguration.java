package slotmachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameConfiguration {
     static int boardHeight = 3;
    static int boardWidth = 5;

    public static Map<String, SlotSymbolWaysPayConfig> getPayout() {

        return Map.of(
                "sym1", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3))).addWild("W1"),
                "sym2", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3))).addWild("W1"),
                "sym3", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(5))).addWild("W1"),
                "sym4", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(5), BigDecimal.valueOf(10))).addWild("W1"),
                "sym5", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(15))).addWild("W1"),
                "sym6", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(15))).addWild("W1"),
                "sym7", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(20))).addWild("W1"),
                "sym8", new SlotSymbolWaysPayConfig(3, List.of(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(50))).addWild("W1")
        );
    }

    public static List<List<String[]>> getReelSets(){
        List<List<String[]>>gameReels = new ArrayList<>();
        List<String[]> bgReelsA = new ArrayList<>(5);
        bgReelsA.add(new String[]{"sym2", "sym7", "sym7", "sym1", "sym1", "sym5", "sym1", "sym4", "sym5", "sym3", "sym2", "sym3", "sym8", "sym4", "sym5", "sym2", "sym8", "sym5", "sym7", "sym2"});
        bgReelsA.add(new String[]{"sym1", "W1", "sym7", "sym6", "sym5", "sym5", "sym8", "W1", "sym5", "sym4", "sym7", "sym2", "sym5", "sym7", "sym1", "sym5", "sym6", "sym8", "sym7", "sym6", "sym3", "sym3", "sym6", "sym7", "sym3"});
        bgReelsA.add(new String[]{"sym5", "sym2", "sym7", "sym8", "sym3", "W1", "sym6", "sym2", "sym2", "sym5", "sym3", "sym5", "sym1", "sym6", "sym3", "sym2", "sym4", "sym1", "sym6", "sym8", "sym6", "sym3", "sym4", "sym4", "sym8", "sym1", "sym7", "sym6", "sym1", "sym6"});
        bgReelsA.add(new String[]{"sym2", "sym6", "sym3", "sym6", "sym8", "sym8", "sym3", "sym6", "sym8", "sym1", "sym5", "W1", "sym6", "sym3", "sym6", "sym7", "sym2", "sym5", "sym3", "sym6", "sym8", "sym4", "sym1", "sym5", "sym7"});
        bgReelsA.add(new String[]{"sym7", "W1", "sym2", "sym3", "sym4", "sym1", "sym3", "sym2", "sym2", "sym4", "sym4", "sym2", "sym6", "sym4", "sym1", "W1", "sym1", "sym6", "sym4", "sym8"});

        gameReels.add(bgReelsA);
        return gameReels;
    }
}
