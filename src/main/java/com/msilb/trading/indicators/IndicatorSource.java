package com.msilb.trading.indicators;

import com.msilb.trading.model.CandleBar;

public interface IndicatorSource {

    /**
     * Update state of the Technical Indicators with the latest candle bar
     *
     * @param candleBar latest candle bar (OLHC)
     */
    void update(CandleBar candleBar);
}
