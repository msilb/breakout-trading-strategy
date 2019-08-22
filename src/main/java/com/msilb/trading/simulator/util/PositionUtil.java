package com.msilb.trading.simulator.util;

import com.msilb.trading.simulator.entity.Position;
import com.msilb.trading.simulator.entity.Transaction;

public class PositionUtil {

    public static Position processFirstTransaction(Transaction transaction) {

        double cost = -transaction.getValue();

        Position position = new Position();

        position.setQuantity(transaction.getQuantity());
        position.setCost(cost);
        position.setRealizedPL(0.0);

        return position;
    }

    public static void processTransaction(Position position, Transaction transaction) {

        // get old values
        long oldQty = position.getQuantity();
        double oldCost = position.getCost();
        double oldRealizedPL = position.getRealizedPL();
        double oldAvgPrice = position.getAveragePrice();

        // get transaction values
        long qty = transaction.getQuantity();
        double price = transaction.getPrice();
        double totalCharges = 0;
        double contractSize = 1;

        // calculate opening and closing quantity
        long closingQty = Long.signum(oldQty) != Long.signum(qty) ? Math.min(Math.abs(oldQty), Math.abs(qty)) * Long.signum(qty) : 0;
        long openingQty = Long.signum(oldQty) == Long.signum(qty) ? qty : qty - closingQty;

        // calculate new values
        long newQty = oldQty + qty;
        double newCost = oldCost + openingQty * contractSize * price;
        double newRealizedPL = oldRealizedPL;

        // handle a previously non-closed position (aldAvgPrice is Double.NaN for a previously closed position)
        if (closingQty != 0) {
            newCost += closingQty * contractSize * oldAvgPrice;
            newRealizedPL += closingQty * contractSize * (oldAvgPrice - price);
        }

        // handle commissions
        if (openingQty != 0) {
            newCost += totalCharges * openingQty / qty;
        }

        if (closingQty != 0) {
            newRealizedPL -= totalCharges * closingQty / qty;
        }

        // set values
        position.setQuantity(newQty);
        position.setCost(newCost);
        position.setRealizedPL(newRealizedPL);
    }
}
