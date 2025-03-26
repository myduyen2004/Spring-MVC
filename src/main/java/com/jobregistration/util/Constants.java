package com.jobregistration.util;

import java.math.BigDecimal;

/**
 * Constants for the application
 * @author YourName
 * @version YYYY_MM_DD
 */
public class Constants {

    // Employee levels day rates
    public static final BigDecimal LEVEL_1_DAY_RATE = new BigDecimal("500000");
    public static final BigDecimal LEVEL_2_DAY_RATE = new BigDecimal("600000");
    public static final BigDecimal LEVEL_3_DAY_RATE = new BigDecimal("700000");

    // Special programming languages
    public static final String LANGUAGE_COBOL = "COBOL";
    public static final String LANGUAGE_RPG = "RPG";

    // Special job statuses
    public static final String STATUS_CLOSED = "Closed";

    // Bonus percentage
    public static final BigDecimal SPECIAL_LANGUAGE_BONUS = new BigDecimal("0.1");

    // Maximum workdays
    public static final int MAX_WORKDAYS = 20;

    private Constants() {
        // Private constructor to prevent instantiation
    }
}