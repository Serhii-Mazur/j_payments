package application.service;

import application.constants.PaymentStatus;
import application.domain.Payment;
import application.port.PaymentRepository;
import application.utils.PaymentStatusGenerator;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PaymentExecutor implements Runnable {
    private final Logger logger;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusGenerator psGenerator;

    public PaymentExecutor(Logger logger, PaymentRepository paymentRepository, PaymentStatusGenerator psGenerator) {
        this.logger = logger;
        this.paymentRepository = paymentRepository;
        this.psGenerator = psGenerator;
    }

    @Override
    public void run() {
        logger.info("Payment Executor run!");
        List<Payment> newPayments;
        int counter = 0;
        while (true) {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.nanoTime();
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
                    payment.setPaymentStatus(newStatus);
                    paymentRepository.updatePaymentStatus(payment.getPaymentID(), newStatus);
                    long end = System.nanoTime();
                    String report = String.format("Update status of payment %n" +
                                    "Payment ID     : %s%n" +
                                    "Template ID    : %s%n" +
                                    "Card number    : %s%n" +
                                    "Amount         : %.2f%n" +
                                    "Status         : %s%n" +
                                    "Created        : %s%n" +
                                    "Updated        : %s%n",
                            payment.getPaymentID(),
                            payment.getTemplateID(),
                            payment.getCardNumber(),
                            payment.getPaymentAmount(),
                            payment.getPaymentStatus().name(),
                            payment.getCreatedDateTime(),
                            payment.getEtlDateTime());
                    logger.info(report + "Operation time: " + ((end - start) / 1000) + " milliseconds.");
//                    logger.info(payment.getPaymentID().toString() + " has got final status! Operation time: " + ((end - start) / 1000) + " milliseconds.");
//                    System.out.println(payment.getPaymentID().toString() + " has got new status! Operation time: " + (end - start) + " nanoseconds.");
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
