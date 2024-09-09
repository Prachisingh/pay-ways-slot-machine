package slotmachine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinData {

    List<Integer> posList;
    String symbolName;
    BigDecimal winAmount;

    public void setBasePayout(BigDecimal basePayout) {
        this.basePayout = basePayout;
    }

    int ways;
    BigDecimal basePayout;

    Map<Integer, Integer> symCountOnEachCol = new HashMap<>();
    public List<Integer> getPosList() {
        return posList;
    }

    public void setPosList(List<Integer> posList) {
        this.posList = posList;
    }



    public int getWays() {
        return ways;
    }

    public void setWays(int ways) {
        this.ways = ways;
    }

    public Map<Integer, Integer> getSymCountOnEachCol() {
        return symCountOnEachCol;
    }

    public void setSymCountOnEachCol(Map<Integer, Integer> symCountOnEachRow) {
        this.symCountOnEachCol = symCountOnEachRow;
    }


    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }



    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }
}
