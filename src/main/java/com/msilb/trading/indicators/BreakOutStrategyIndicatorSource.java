package com.msilb.trading.indicators;

import com.msilb.trading.model.CandleBar;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

import java.time.ZoneId;

public class BreakOutStrategyIndicatorSource extends AbstractIndicatorSource {

    // Parameters
    public static final int MOVING_AVERAGE_LENGTH = 30;
    public static final int STD_DEVIATION_LENGTH = 30;

    // Technical Indicators Used
    private final SMAIndicator smaIndicator;
    private final BollingerBandsLowerIndicator bollingerBandsLowerIndicator;
    private final BollingerBandsUpperIndicator bollingerBandsUpperIndicator;

    public BreakOutStrategyIndicatorSource(String timeSeriesName) {
        super(timeSeriesName);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(timeSeries);
        this.smaIndicator = new SMAIndicator(closePrice, MOVING_AVERAGE_LENGTH);
        StandardDeviationIndicator stdDevInd = new StandardDeviationIndicator(closePrice, STD_DEVIATION_LENGTH);
        BollingerBandsMiddleIndicator bbmi = new BollingerBandsMiddleIndicator(smaIndicator);
        this.bollingerBandsLowerIndicator = new BollingerBandsLowerIndicator(bbmi, stdDevInd);
        this.bollingerBandsUpperIndicator = new BollingerBandsUpperIndicator(bbmi, stdDevInd);
    }

    @Override
    public void update(CandleBar candleBar) {
        timeSeries.addBar(
                candleBar.getDate().atStartOfDay(ZoneId.systemDefault()),
                candleBar.getOpen(),
                candleBar.getHigh(),
                candleBar.getLow(),
                candleBar.getClose()
        );
    }

    public double getCurrentSma() {
        return smaIndicator.getValue(getLastIntervalIndex()).doubleValue();
    }

    public double getCurrentBbLower() {
        return bollingerBandsLowerIndicator.getValue(getLastIntervalIndex()).doubleValue();
    }

    public double getCurrentBbUpper() {
        return bollingerBandsUpperIndicator.getValue(getLastIntervalIndex()).doubleValue();
    }

    private int getLastIntervalIndex() {
        return timeSeries.getBarCount() - 1;
    }
}
