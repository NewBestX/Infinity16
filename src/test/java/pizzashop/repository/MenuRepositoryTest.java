package pizzashop.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.MenuDataModel;

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
    void menuRepository_test() {
        MenuRepository repo = new MenuRepository("data/menuTest.txt");

        List<MenuDataModel> result = repo.getMenu();
        assertArrayEquals(new MenuDataModel[]{}, result.toArray());
    }
}