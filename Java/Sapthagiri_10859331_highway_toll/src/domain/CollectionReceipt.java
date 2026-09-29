package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CollectionReceipt(
        int totalTrucks,
        BigDecimal totalReceipts,
        LocalDateTime collectedAt
) {
    public CollectionReceipt {
        if (totalTrucks < 0) {
            throw new IllegalArgumentException(
                    "Total trucks cannot be negative."
            );
        }

        if (totalReceipts == null) {
            throw new IllegalArgumentException(
                    "Total receipts cannot be null."
            );
        }

        if (collectedAt == null) {
            throw new IllegalArgumentException(
                    "Collection time cannot be null."
            );
        }
    }
}