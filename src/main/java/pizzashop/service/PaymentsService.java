package pizzashop.service;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;
import pizzashop.validator.PaymentValidator;
import pizzashop.validator.ValidationException;

import java.util.List;

public class PaymentsService {
    private PaymentRepository payRepo;

    public PaymentsService(PaymentRepository payRepo) {
        this.payRepo = payRepo;
    }

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public void addPayment(int table, PaymentType type, double amount) throws ValidationException {
        Payment payment= new Payment(table, type, amount);
        PaymentValidator.validPayment(payment);
        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type){
        double total=0.0f;
        List<Payment> l=getPayments();
        if (l==null || l.isEmpty()) return total;
        for (Payment p:l){
            if (p.getType().equals(type))
                total+=p.getAmount();
        }
        return total;
    }
}
