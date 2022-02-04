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

    public UUID getTemplateID() {
        return templateID;
    }

    public UUID getAddressID() {
        return addressID;
    }

    public void setAddressID(UUID addressID) {
        this.addressID = addressID;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
