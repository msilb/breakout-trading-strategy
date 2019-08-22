package com.msilb.trading.strategy;

import com.msilb.trading.simulator.enumeration.Direction;

class BreakOutSignalCalculator {

    enum Action {
        IDLE,
        BUY,
        SELL,
        CLOSE_LONG,
        CLOSE_SHORT
    }

    /**
     * Calculates break-out signal based on the following rules:
     * <pre>
     *  if current position is flat and current price is lower than the Lower Bollinger Band
     *    => BUY
     *  if current position is flat and current price is higher than the Upper Bollinger Band
     *    => SELL
     *  if current position is long and current price is higher than 30-day SMA
     *    => SELL (Close Position)
     *  if current position is short and current price is lower than 30-day SMA
     *    => BUY (Close Position)
     * </pre>
     *
     * @param currentPrice     current price of the underlying (mostly closing price on daily interval)
     * @param currentDirection current position direction: FLAT, SHORT and LONG
     * @param sma              30-DAY Simple Moving Average current value
     * @param bblower          Lower Bollinger Band current value
     * @param bbupper          Upper Bollinger Band current value
     * @return Action to take in the market
     */
    static Action calculate(double currentPrice,
                            Direction currentDirection,
                            double sma,
                            double bblower,
                            double bbupper) {
        switch (currentDirection) {
            case FLAT:
                if (currentPrice < bblower) {
                    return Action.BUY;
                } else if (currentPrice > bbupper) {
                    return Action.SELL;
                }
                break;
            case LONG:
                if (currentPrice > sma) {
                    return Action.CLOSE_LONG;
                }
                break;
            case SHORT:
                if (currentPrice < sma) {
                    return Action.CLOSE_SHORT;
                }
                break;
        }
        return Action.IDLE;
    }
}
