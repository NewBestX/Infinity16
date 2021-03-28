package pizzashop.validator;

import pizzashop.model.Payment;

public class PaymentValidator {
    public static void validPayment(Payment p) throws ValidationException {
        if (p.getTableNumber() < 1 || p.getTableNumber() > 8)
            throw new ValidationException("Invalid payment table number");
        if (p.getAmount() <= 0)
            throw new ValidationException("Invalid payment amount");
        if (p.getType() == null)
            throw new ValidationException("Invalid payment type");
    }
}
