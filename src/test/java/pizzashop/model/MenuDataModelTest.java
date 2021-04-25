package pizzashop.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pizzashop.validator.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class MenuDataModelTest {
    @Test
    void constructor_test() {
        MenuDataModel mdm = new MenuDataModel("testItem", 10, 5.5);

        assertEquals("testItem", mdm.getMenuItem());
        assertEquals(10, mdm.getQuantity());
        assertEquals(5.5, mdm.getPrice());
    }

    @Test
    void setter_test() {
        MenuDataModel mdm = new MenuDataModel("testItem", 10, 5.5);

        mdm.setMenuItem("newItem");
        mdm.setQuantity(1);
        mdm.setPrice(1.5);

        assertEquals("newItem", mdm.getMenuItem());
        assertEquals(1, mdm.getQuantity());
        assertEquals(1.5, mdm.getPrice());
    }
}