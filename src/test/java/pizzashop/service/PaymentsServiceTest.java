package pizzashop.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;
import pizzashop.validator.ValidationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class PaymentsServiceTest {
    static PaymentRepository paymentRepository;
    static PaymentsService paymentsService;

    @BeforeAll
    static void init() {
        FileWriter file = null;
        try {
            file = new FileWriter("src/test/resources/data/paymentsTest.txt");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        paymentRepository = new PaymentRepository("data/paymentsTest.txt");
        paymentsService = new PaymentsService(paymentRepository);
    }

    @AfterAll
    static void finalTests(){
        File file = new File("src/test/resources/data/paymentsTest.txt");
        file.deleteOnExit();
    }


    @Test
    @DisplayName("ECP_VALID")
    @Tag("1")
    void tc1_ecp_addPayment() {
        int initialSize = paymentRepository.getAll().size();
        try {
            paymentsService.addPayment(2, PaymentType.Cash, 23.55);
            assert true;
        }catch (ValidationException e) {
            assert false;
        }
        assert paymentRepository.getAll().size() == initialSize + 1;
    }

    @Test
    @DisplayName("ECP_INVALID")
    @Tag("2")
    void tc2_ecp_addPayment() {

        int initialSize = paymentRepository.getAll().size();
        try {
            paymentsService.addPayment(3, PaymentType.Cash, -17.4);
            assert false;
        }catch (ValidationException e) {
            assert e.getMessage().equals("Invalid payment amount");
        }
        assert paymentRepository.getAll().size() == initialSize;
    }

    @Test
    @DisplayName("ECP_INVALID")
    @Tag("3")
    void tc3_ecp_addPayment() {

        int initialSize = paymentRepository.getAll().size();
        try {
            paymentsService.addPayment(10, PaymentType.Cash, 10);
            assert false;
        }catch (ValidationException e) {
            assert e.getMessage().equals("Invalid payment table number");
        }
        assert paymentRepository.getAll().size() == initialSize;
    }

    @Test
    @DisplayName("ECP_INVALID")
    @Tag("4")
    void tc4_ecp_addPayment() {

        int initialSize = paymentRepository.getAll().size();
        try {
            paymentsService.addPayment(2, null, 10);
            assert false;
        }catch (ValidationException e) {
            assert e.getMessage().equals("Invalid payment type");
        }
        assert paymentRepository.getAll().size() == initialSize;
    }






}