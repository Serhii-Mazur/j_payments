package application.utils;

import application.constants.PaymentStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;

public class PaymentStatusGenerator {
//    private final UUID paymentID;
    private final Random random;

    public PaymentStatusGenerator(/*UUID paymentID*/) {
//        this.paymentID = paymentID;
        this.random = new Random();
    }

//    public UUID getPaymentID() {
//        return paymentID;
//    }

    public PaymentStatus getNewStatus(int counter) {
        PaymentStatus result = null;
        if (counter > 2) {
            int status = random.nextInt(3);
            switch (status) {
                case 0:
                    result = PaymentStatus.NEW;
                    break;
                case 1:
                    result = PaymentStatus.DONE;
                    break;
                case 2:
                    result = PaymentStatus.FAILED;
                    break;
            }
        } else {
            result = PaymentStatus.NEW;
        }
        return result;
    }
}
