package application.entities;

import java.util.UUID;

public class PaymentAddress {
    private final UUID uuid = UUID.randomUUID();
    private final String address;
    private User user;

    public PaymentAddress(User user, String address) {
        this.user = user;
        this.address = address;
    }
}
