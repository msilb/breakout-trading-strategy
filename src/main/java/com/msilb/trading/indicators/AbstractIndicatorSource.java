package com.msilb.trading.indicators;

import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

public abstract class AbstractIndicatorSource implements IndicatorSource {

    final TimeSeries timeSeries;

    AbstractIndicatorSource(String timeSeriesName) {
        this.timeSeries = new BaseTimeSeries.SeriesBuilder().withName(timeSeriesName).build();
    }

    public TimeSeries getTimeSeries() {
        return timeSeries;
    }
}
