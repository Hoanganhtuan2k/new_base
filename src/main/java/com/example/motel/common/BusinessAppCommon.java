package com.example.motel.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BusinessAppCommon {

    public static String getOrderNumberKey() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDate.now().format(formatter);
        return AppConstants.ORDER_NUMBER_COUNT + "_" + date;
    }

}
