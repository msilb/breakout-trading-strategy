package com.msilb.trading.csv;

import com.msilb.trading.marketdata.CandleBarDataSource;
import com.msilb.trading.model.CandleBar;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CandleCsvReader implements CandleBarDataSource {

    private Iterator<CandleBar> candleIterator;

    public CandleCsvReader(String fileName) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        CsvToBean<CandleBar> csvToBean = new CsvToBeanBuilder<CandleBar>(reader)
                .withType(CandleBar.class)
                .withSkipLines(1)
                .build();
        candleIterator = csvToBean.iterator();
    }

    public boolean hasMoreCandleBars() {
        return candleIterator.hasNext();
    }

    public CandleBar getNextCandleBar() {
        return candleIterator.next();
    }
}
