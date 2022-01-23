package application.entities;

import java.util.UUID;

public class Template {
    private final UUID uuid;
    private String templateName;
    private String iban;
    private String paymentPurpose;
    private PaymentAddress paymentAddress;

    public Template(String templateName, String iban, String paymentPurpose, PaymentAddress paymentAddress) {
        this.templateName = templateName;
        this.iban = iban;
        this.paymentPurpose = paymentPurpose;
        this.paymentAddress = paymentAddress;
        this.uuid = UUID.fromString(paymentPurpose);
    }
}
