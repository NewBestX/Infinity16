package pizzashop.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.MenuDataModel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MenuRepositoryTest {

    @Test
    void menuRepository_mock_test() {
        MenuRepository repo = mock(MenuRepository.class);

        MenuDataModel mdm = mock(MenuDataModel.class);
        Mockito.when(repo.getMenu()).thenReturn(Collections.singletonList(mdm));

        List<MenuDataModel> result = repo.getMenu();
        assertArrayEquals(new MenuDataModel[]{mdm}, result.toArray());
    }

    @Test
    void menuRepository_mock_entity_test() {
        MenuRepository repo = new MenuRepository("data/menuTest.txt");

        MenuDataModel mdm = mock(MenuDataModel.class);
        Mockito.when(mdm.toString()).thenReturn("test,12.3");

        int initialSize = repo.getMenu().size();
        repo.addMenuItem(mdm);

        List<MenuDataModel> result = repo.getMenu();
        assertEquals(initialSize + 1, result.size());
        assertEquals("test,12.3", result.get(initialSize).toString());
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