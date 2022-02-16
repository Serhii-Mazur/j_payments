package application.utils;

import application.constants.PaymentStatus;
import application.domain.Payment;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.SECONDS;

public class PaymentStatusGenerator {
    private final Random random;

    public PaymentStatusGenerator() {
        this.random = new Random();
    }

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

    public PaymentStatus getNewStatus(Payment payment) {
        PaymentStatus result = PaymentStatus.NEW;
        LocalDateTime createdDateTime = payment.getCreatedDateTime();
        long lag = ChronoUnit.SECONDS.between(createdDateTime, LocalDateTime.now().withNano(0));

        if (lag > 2) {
            int status = random.nextInt(3);
            switch (status) {
                case 1:
                    result = PaymentStatus.DONE;
                    break;
                case 2:
                    result = PaymentStatus.FAILED;
                    break;
            }
        }
        return result;
    }
}
