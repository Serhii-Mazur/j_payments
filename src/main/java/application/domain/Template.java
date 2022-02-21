package application.domain;

import java.util.Objects;
import java.util.UUID;

public class Template {
    private final UUID templateID;
    private UUID addressID;
    private String paymentPurpose;
    private String templateName;
    private String iban;

    public Template(UUID templateID, UUID addressID, String templateName, String paymentPurpose, String iban) {
        this.templateID = templateID;
        this.addressID = addressID;
        this.templateName = templateName;
        this.paymentPurpose = paymentPurpose;
        this.iban = iban;
    }

    public Template(UUID addressID, String templateName, String paymentPurpose, String iban) {
        this(UUID.randomUUID(), addressID, templateName, paymentPurpose, iban);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return templateID.equals(template.templateID) && addressID.equals(template.addressID) && iban.equals(template.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateID, addressID, iban);
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
