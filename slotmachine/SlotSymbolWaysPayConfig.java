package slotmachine;

import java.math.BigDecimal;
import java.util.List;


public class SlotSymbolWaysPayConfig {

    private int minimumMatch;

    private List<BigDecimal> winAmounts;

    public BigDecimal getWinAmount(int matchedColumnsCount) {
        if (matchedColumnsCount < minimumMatch) return BigDecimal.ZERO;

        if (matchedColumnsCount - minimumMatch == 0) {
            return winAmounts.getFirst();
        } else if (matchedColumnsCount - minimumMatch == 1) {
            return winAmounts.get(1);
        } else {
            return winAmounts.get(2);
        }
    }

    public int getMinimumMatch() {
        return minimumMatch;
    }

    public SlotSymbolWaysPayConfig(int minimumMatch, List<BigDecimal> winAmounts) {
        this.minimumMatch = minimumMatch;
        this.winAmounts = winAmounts;
    }

}
