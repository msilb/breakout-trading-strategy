package com.msilb.trading.strategy;

import com.msilb.trading.indicators.BreakOutStrategyIndicatorSource;
import com.msilb.trading.marketdata.CandleBarDataSource;
import com.msilb.trading.model.CandleBar;
import com.msilb.trading.simulator.entity.Position;
import com.msilb.trading.simulator.entity.trade.MarketOrder;
import com.msilb.trading.simulator.enumeration.Direction;
import com.msilb.trading.simulator.enumeration.Side;
import com.msilb.trading.simulator.simulation.Simulator;
import lombok.extern.slf4j.Slf4j;
import org.ta4j.core.TimeSeries;

import java.time.LocalDate;

import static com.msilb.trading.strategy.BreakOutSignalCalculator.Action;
import static com.msilb.trading.strategy.BreakOutSignalCalculator.calculate;

@Slf4j
public class BreakOutStrategy implements TradingStrategy {

    // Parameters
    private static final int INVESTMENT_SIZE = 1000000;

    // Collaborators
    private final CandleBarDataSource candleBarDataSource;
    private final BreakOutStrategyIndicatorSource breakOutStrategyIndicatorSource;
    private final Simulator simulator;

    public BreakOutStrategy(CandleBarDataSource candleBarDataSource,
                            BreakOutStrategyIndicatorSource breakOutStrategyIndicatorSource,
                            Simulator simulator) {
        this.candleBarDataSource = candleBarDataSource;
        this.simulator = simulator;
        this.breakOutStrategyIndicatorSource = breakOutStrategyIndicatorSource;
    }

    @Override
    public void init() {
        TimeSeries timeSeries = breakOutStrategyIndicatorSource.getTimeSeries();
        while (candleBarDataSource.hasMoreCandleBars() &&
                timeSeries.getBarCount() < BreakOutStrategyIndicatorSource.MOVING_AVERAGE_LENGTH) {
            CandleBar candleBar = candleBarDataSource.getNextCandleBar();
            breakOutStrategyIndicatorSource.update(candleBar);
        }
        log.debug("Moving average and BBands warmed up with {} ticks", BreakOutStrategyIndicatorSource.MOVING_AVERAGE_LENGTH);
    }

    @Override
    public void execute() {
        while (candleBarDataSource.hasMoreCandleBars()) {
            CandleBar candleBar = candleBarDataSource.getNextCandleBar();

            LocalDate currentDate = candleBar.getDate();
            double currentPrice = candleBar.getClose();

            breakOutStrategyIndicatorSource.update(candleBar);

            double sma = breakOutStrategyIndicatorSource.getCurrentSma();
            double bblower = breakOutStrategyIndicatorSource.getCurrentBbLower();
            double bbupper = breakOutStrategyIndicatorSource.getCurrentBbUpper();
            Direction currentDirection = getCurrentDirection();

            log.debug(
                    "date=[{}], price=[{}], direction=[{}] SMA=[{}], BB_LOWER=[{}], BB_UPPER=[{}]",
                    currentDate,
                    currentPrice,
                    currentDirection,
                    sma,
                    bblower,
                    bbupper
            );

            simulator.setCurrentPrice(candleBar.getClose());

            Action action = calculate(currentPrice, currentDirection, sma, bblower, bbupper);

            switch (action) {
                case BUY:
                case CLOSE_SHORT:
                    buy();
                    break;
                case SELL:
                case CLOSE_LONG:
                    sell();
                    break;
            }

            if (action == Action.CLOSE_LONG || action == Action.CLOSE_SHORT) {
                printCashBalance();
            }
        }
    }

    private Direction getCurrentDirection() {
        Position position = simulator.getPosition();
        if (position != null) {
            return position.getDirection();
        }
        return Direction.FLAT;
    }

    private void buy() {
        MarketOrder order = MarketOrder.builder().side(Side.BUY).quantity(INVESTMENT_SIZE).build();
        simulator.sendOrder(order);
    }

    private void sell() {
        MarketOrder order = MarketOrder.builder().side(Side.SELL).quantity(INVESTMENT_SIZE).build();
        simulator.sendOrder(order);
    }

    private void printCashBalance() {
        log.info("Current cash balance is {}", simulator.getCashBalance());
    }
}
