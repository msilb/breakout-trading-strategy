package com.msilb.trading.simulator.entity.trade;

import com.msilb.trading.simulator.enumeration.Side;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Fill {
    private Side side;
    private long quantity;
    private double price;
    private MarketOrder order;
}
