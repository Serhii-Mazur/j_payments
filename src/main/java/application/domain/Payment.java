package application.domain;

import application.constants.PaymentStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class Payment {
    private UUID paymentID = UUID.randomUUID();
    private UUID templateID;
    private String cardNumber;
    private BigDecimal paymentAmount;
    private PaymentStatus paymentStatus;
    private Timestamp createTimeStamp;
    private Timestamp statusChangeTimeStamp;
}
