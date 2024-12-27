package org.example.builder;

import io.hypersistence.utils.hibernate.type.range.Range;

import java.time.LocalDate;
import java.util.Scanner;

public class DateRangeBuilder {
    public static Range<LocalDate> dateRangeBuilder() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter date range (e.g. [2025-01-10,2025-01-15]):");
        String inputRange = sc.nextLine();
        String trimmed = inputRange.replaceAll("[\\[\\]\\(\\)]", "");
        String[] parts = trimmed.split(",");
        if (parts.length != 2) {
            System.out.println("Invalid date range format. Use [YYYY-MM-DD,YYYY-MM-DD].");
            return null; // or throw an exception
        }

        LocalDate start = LocalDate.parse(parts[0].trim());
        LocalDate end   = LocalDate.parse(parts[1].trim());

        Range<LocalDate> dateRange = Range.closed(start, end);
        return dateRange;
    }
}
