package application.domain;

import java.util.UUID;

public class Template {
    private UUID templateID;
    private UUID addressID;
    private String paymentPurpose;
    private String templateName;
    private String iban;

    public Template(UUID addressID, String templateName, String paymentPurpose, String iban) {
        this.addressID = addressID;
        this.templateName = templateName;
        this.paymentPurpose = paymentPurpose;
        this.iban = iban;
        this.templateID = UUID.fromString(paymentPurpose);
    }
}
