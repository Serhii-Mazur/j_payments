package application.service;

import application.constants.PaymentStatus;
import application.domain.Payment;
import application.port.PaymentRepository;
import application.utils.PaymentStatusGenerator;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PaymentExecutor implements Runnable {
    private final PaymentRepository paymentRepository;
    private final PaymentStatusGenerator psGenerator;

    public PaymentExecutor(PaymentRepository paymentRepository, PaymentStatusGenerator psGenerator) {
        this.paymentRepository = paymentRepository;
        this.psGenerator = psGenerator;
    }

    @Override
    public void run() {
        List<Payment> newPayments;
        int counter = 0;
        while(true) {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newPayments = paymentRepository.getPaymentsByStatus(PaymentStatus.NEW);
            if (newPayments.size() == 0) {
                counter++;
            }
            if (counter > 10) {
                break;
            }
            for (Payment payment : newPayments) {
                System.out.println(payment.getPaymentID().toString() + " -> " + payment.getPaymentStatus().name());
                PaymentStatus newStatus = psGenerator.getNewStatus(payment);
                if (!newStatus.equals(PaymentStatus.NEW)) {
                    paymentRepository.updatePaymentStatus(payment.getPaymentID(), newStatus);
                    System.out.println(payment.getPaymentID().toString() + " has got new status!");
                }
            }
        }

//        do {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            newPayments = paymentRepository.getPaymentsByStatus(PaymentStatus.NEW);
//            for (Payment payment : newPayments) {
//                System.out.println(payment.getPaymentID().toString() + " -> " + payment.getPaymentStatus().name());
//                PaymentStatus newStatus = psGenerator.getNewStatus(payment);
//                if (!newStatus.equals(PaymentStatus.NEW)) {
//                    paymentRepository.updatePaymentStatus(payment.getPaymentID(), newStatus);
//                    System.out.println(payment.getPaymentID().toString() + " has got new status!");
//                }
//            }
//        } while (newPayments.size() > 0);

//        System.out.println("All payments have got new status!");
    }
}
