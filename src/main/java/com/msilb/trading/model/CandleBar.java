package com.msilb.trading.model;

import com.msilb.trading.csv.LocalDateConverter;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CandleBar {

    @CsvCustomBindByPosition(position = 0, converter = LocalDateConverter.class)
    private LocalDate date;

    @CsvBindByPosition(position = 1)
    private double open;

    @CsvBindByPosition(position = 2)
    private double low;

    @CsvBindByPosition(position = 3)
    private double high;

    @CsvBindByPosition(position = 4)
    private double close;
}
