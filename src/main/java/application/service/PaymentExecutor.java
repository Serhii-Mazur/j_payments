package application.service;

import application.constants.PaymentStatus;
import application.domain.Payment;
import application.port.PaymentRepository;
import application.utils.PaymentStatusGenerator;
import dal.SqlPaymentRepository;

import java.util.ArrayList;
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
        List<Payment> newPayments = new ArrayList<>();
        int counter = 0;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.nanoTime();
            try {
                newPayments = paymentRepository.getPaymentsByStatus(PaymentStatus.NEW);
            } catch (SqlPaymentRepository.SQLPaymentRepositoryExcception e) {
                e.printStackTrace();
            }
            if (newPayments.size() == 0) {
                counter++;
            }
            if (counter > 5) {
                break;
            }
            for (Payment payment : newPayments) {
                PaymentStatus newStatus = psGenerator.getNewStatus(payment);
                if (!newStatus.equals(PaymentStatus.NEW)) {
                    payment.setPaymentStatus(newStatus);
                    try {
                        paymentRepository.updatePaymentStatus(payment.getPaymentID(), newStatus);
                    } catch (SqlPaymentRepository.SQLPaymentRepositoryExcception e) {
                        e.printStackTrace();
                    }
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
                }
            }

        }
    }
}
