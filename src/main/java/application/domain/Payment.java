package application.domain;

import application.constants.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Payment {
    private final UUID paymentID;
    private final UUID templateID;
    private final long cardNumber;
    private final float paymentAmount;
    private PaymentStatus paymentStatus;
    private final LocalDateTime createdDateTime;
    private LocalDateTime etlDateTime;

    public Payment(
            UUID paymentID,
            UUID templateID,
            long cardNumber,
            float paymentAmount,
            PaymentStatus paymentStatus,
            LocalDateTime createdDateTime,
            LocalDateTime etlDateTime
    ) {
        this.paymentID = paymentID;
        this.templateID = templateID;
        this.cardNumber = cardNumber;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.createdDateTime = createdDateTime.withNano(0);
        this.etlDateTime = etlDateTime.withNano(0);
    }

    public Payment(UUID templateID, long cardNumber, float paymentAmount) {
        this(
                UUID.randomUUID(),
                templateID,
                cardNumber,
                paymentAmount,
                PaymentStatus.NEW,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return paymentID.equals(payment.paymentID) && templateID.equals(payment.templateID) && paymentStatus == payment.paymentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentID, templateID, paymentStatus);
    }

    public UUID getPaymentID() {
        return paymentID;
    }

    public UUID getTemplateID() {
        return templateID;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getEtlDateTime() {
        return etlDateTime;
    }

    public void setEtlDateTime(LocalDateTime etlDateTime) {
        this.etlDateTime = etlDateTime;
    }
}
