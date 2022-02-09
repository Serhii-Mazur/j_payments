package application.domain;

import application.constants.PaymentStatus;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private final UUID paymentID;
    private final UUID templateID;
    private final long cardNumber;
    private final float paymentAmount;
    private PaymentStatus paymentStatus;
    private final LocalDateTime createdDateTime;
    private LocalDateTime statusChangedDateTime;

    public Payment(@NotNull UUID paymentID,
                   @NotNull UUID templateID,
                   long cardNumber,
                   float paymentAmount,
                   PaymentStatus paymentStatus,
                   @NotNull LocalDateTime createdDateTime,
                   LocalDateTime statusChangedDateTime)
    {
        this.paymentID = paymentID;
        this.templateID = templateID;
        this.cardNumber = cardNumber;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.createdDateTime = createdDateTime;
        this.statusChangedDateTime = statusChangedDateTime;
    }

    public Payment(UUID templateID, long cardNumber, float paymentAmount) {
        this(
                UUID.randomUUID(),
                templateID,
                cardNumber,
                paymentAmount,
                PaymentStatus.NEW,
                LocalDateTime.now(),
                null);
    }
}
