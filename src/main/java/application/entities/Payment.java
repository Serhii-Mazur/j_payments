package application.entities;

import application.PaymentStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class Payment {
    private final UUID uuid = UUID.randomUUID();
    private Template template;
    private String cardNumber;
    private BigDecimal paymentAmount;
    private PaymentStatus paymentStatus;
    private Timestamp createTimeStamp;
    private Timestamp statusChangeTimeStamp;
}
