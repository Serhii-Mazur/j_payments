package application.service;

import application.constants.PaymentStatus;
import application.domain.Payment;
import application.port.PaymentRepository;
import application.utils.PaymentStatusGenerator;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PaymentExecutor implements Runnable {
    private final PaymentRepository paymentRepository;

    public PaymentExecutor(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void run() {
        List<Payment> newPayments;
        PaymentStatusGenerator psGenerator = new PaymentStatusGenerator();
        int counter = 0;
        do {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
            newPayments = paymentRepository.getPaymentsByStatus(PaymentStatus.NEW);
            for (Payment payment : newPayments) {
                System.out.println(payment.getPaymentID().toString() + " -> " + payment.getPaymentStatus().name());
                PaymentStatus newStatus = psGenerator.getNewStatus(counter);
                if (!newStatus.equals(PaymentStatus.NEW)) {
                    paymentRepository.updatePaymentStatus(payment.getPaymentID(), newStatus);
                    System.out.println(payment.getPaymentID().toString() + " has got new status!");
                }
            }
        } while (newPayments.size() > 0);

        System.out.println("All payments have got new status!");
    }
}
