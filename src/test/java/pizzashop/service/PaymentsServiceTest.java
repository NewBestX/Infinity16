package pizzashop.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;
import pizzashop.validator.ValidationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @ParameterizedTest
    @ValueSource(doubles = {0.1, Double.MAX_VALUE-1, Double.MAX_VALUE})
    @DisplayName("BVA_VALID")
    @Tag("7-8-9")
    void tc5_bva_addPayment(double suma) {

        int initialSize = paymentRepository.getAll().size();
        try {
            paymentsService.addPayment(1, PaymentType.Cash, suma);
            assert true;
        }catch (ValidationException e) {
            assert false;
        }
        assert paymentRepository.getAll().size() == initialSize + 1;
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, 0})
    @DisplayName("BVA_INVALID")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @Tag("5-6")
    void tc6_bva_addPayment(double suma) {

        int initialSize = paymentRepository.getAll().size();
        try {
            paymentsService.addPayment(1, PaymentType.Cash, suma);
            assert false;
        }catch (ValidationException e) {
            assert e.getMessage().equals("Invalid payment amount");
        }
        assert paymentRepository.getAll().size() == initialSize;
    }

    @ParameterizedTest
    @ValueSource(ints = {1,8})
    @DisplayName("BVA_VALID")
    @Tag("12-13")
    void tc7_bva_addPayment(int table) {

        int initialSize = paymentRepository.getAll().size();
        try {
            paymentsService.addPayment(table, PaymentType.Cash, 10);
            assert true;
        }catch (ValidationException e) {
            assert false;
        }
        assert paymentRepository.getAll().size() == initialSize + 1;
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 9})
    @DisplayName("BVA_INVALID")
    @Tag("11-14")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void tc8_bva_addPayment(int table) {

        int initialSize = paymentRepository.getAll().size();
        try {
            paymentsService.addPayment(table, PaymentType.Cash, 10);
            assert false;
        }catch (ValidationException e) {
            assert e.getMessage().equals("Invalid payment table number");
        }
        assert paymentRepository.getAll().size() == initialSize;
    }


    @Test
    @Tag("TC1")
    void getTotalAmount_NullList_Return0() {
        List<Payment> l = null;
        PaymentType type = PaymentType.Cash;

        double rez = paymentsService.getTotalAmount(l, type);

        assertEquals(0, rez);
    }

    @Test
    @Tag("TC2")
    void getTotalAmount_EmptyList_Return0() {
        List<Payment> l = new ArrayList<>();
        PaymentType type = PaymentType.Cash;

        double rez = paymentsService.getTotalAmount(l, type);

        assertEquals(0, rez);
    }

    @Test
    @Tag("TC3")
    void getTotalAmount_NoMatchingTypes_Return0() {
        List<Payment> l = new ArrayList<>();
        l.add(new Payment(1, PaymentType.Card, 10));
        PaymentType type = PaymentType.Cash;

        double rez = paymentsService.getTotalAmount(l, type);

        assertEquals(0, rez);
    }

    @Test
    @Tag("TC4")
    void getTotalAmount_MatchingTypes_Return10() {
        List<Payment> l = new ArrayList<>();
        l.add(new Payment(1, PaymentType.Card, 10));
        PaymentType type = PaymentType.Card;

        double rez = paymentsService.getTotalAmount(l, type);

        assertEquals(10, rez);
    }
    @Test
    @Tag("TC5")
    void getTotalAmount_Iterations_Return15dot5() {
        List<Payment> l = new ArrayList<>();
        l.add(new Payment(1, PaymentType.Card, 10));
        l.add(new Payment(2, PaymentType.Card, 5.5));
        PaymentType type = PaymentType.Card;

        double rez = paymentsService.getTotalAmount(l, type);

        assertEquals(15.5, rez);
    }
}