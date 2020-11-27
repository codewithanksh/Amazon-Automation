package enums;

import utils.CommonUtils;

public enum WaitTime {

    SuperLong_60Sec(60),
    Longest_30Sec(30),
    Longer_20Sec(20),
    Long_10Sec(10),
    Normal_5Sec(5),
    Short_3Sec(3),
    Shorter_2Sec(2),
    Shortest_1Sec(1);

    private final int value;

    public int val() {
        return value;
    }

    public int valInMS() {
        return value * 1000;
    }

    public void execute() {
        CommonUtils.wait(this.valInMS());
    }

    WaitTime(int value) {
        this.value = value;
    }
}
