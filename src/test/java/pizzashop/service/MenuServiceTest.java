package pizzashop.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.MenuDataModel;
import pizzashop.repository.MenuRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MenuServiceTest {

    @Test
    void menuService_unit_test() {
        MenuRepository repo = mock(MenuRepository.class);

        MenuDataModel mdm = mock(MenuDataModel.class);
        Mockito.when(repo.getMenu()).thenReturn(Collections.singletonList(mdm));

        MenuService service = new MenuService(repo);

        List<MenuDataModel> result = service.getMenuData();
        assertArrayEquals(new MenuDataModel[]{mdm}, result.toArray());
    }

    @Test
    void menuService_integration_repository() {
        MenuRepository repo = new MenuRepository("data/menuTest.txt");

        MenuDataModel mdm = mock(MenuDataModel.class);
        Mockito.when(mdm.toString()).thenReturn("test,12.3");

        MenuService service = new MenuService(repo);

        int initialSize = service.getMenuData().size();
        service.addMenuItem(mdm);
        List<MenuDataModel> result = service.getMenuData();

        assertEquals(initialSize + 1, result.size());
        assertEquals("test,12.3", result.get(initialSize).toString());
    }

    @Test
    void menuService_integration_repository_entity() {
        MenuRepository repo = new MenuRepository("data/menuTest.txt");

        MenuDataModel mdm = new MenuDataModel("test", 1, 1.2);

        MenuService service = new MenuService(repo);

        int initialSize = service.getMenuData().size();
        service.addMenuItem(mdm);
        List<MenuDataModel> result = service.getMenuData();

        assertEquals(initialSize + 1, result.size());
        assertEquals("test,1.2", result.get(initialSize).toString());
    }

    @AfterAll
    static void finalTests() {
        try {
            FileWriter file = new FileWriter("src/test/resources/data/menuTest.txt");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}