package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.repository.MenuRepository;

import java.util.List;

public class MenuService {
    private MenuRepository menuRepo;

    public MenuService(MenuRepository menuRepo) {
        this.menuRepo = menuRepo;
    }

    public List<MenuDataModel> getMenuData(){return menuRepo.getMenu();}
}
