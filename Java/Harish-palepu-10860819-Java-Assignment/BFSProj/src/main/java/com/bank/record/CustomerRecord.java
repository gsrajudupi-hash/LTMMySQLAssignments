package com.bank.record;

import com.bank.model.CustomerType;

/**
 * Immutable customer summary, including account holdings, used for reporting.
 */
public record CustomerRecord(int customerId,
                             String name,
                             String city,
                             CustomerType customerType,
                             long accountCount,
                             double depositBalance,
                             double loanOutstanding) {
}
