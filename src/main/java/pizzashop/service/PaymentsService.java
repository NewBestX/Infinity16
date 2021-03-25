package pizzashop.service;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class PaymentsService {
    private PaymentRepository payRepo;

    public PaymentsService(PaymentRepository payRepo) {
        this.payRepo = payRepo;
    }

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public void addPayment(int table, PaymentType type, double amount){
        Payment payment= new Payment(table, type, amount);
        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type){
        double total=0.0f;
        List<Payment> l=getPayments();
        if ((l==null) ||(l.size()==0)) return total;
        for (Payment p:l){
            if (p.getType().equals(type))
                total+=p.getAmount();
        }
        return total;
    }
}
