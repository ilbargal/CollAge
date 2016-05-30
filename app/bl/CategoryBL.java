package bl;

import common.DataBaseHandler;
import models.Categories;

import java.util.List;

public class CategoryBL {
    private static CategoryBL _instance;

    private CategoryBL() {
    }

    public static CategoryBL getInstance() {
        if (_instance == null) {
            synchronized (EventBL.class) {
                if (_instance == null) {
                    _instance = new CategoryBL();
                }
            }
        }
        return _instance;
    }

    public List<Categories> getAllCategories() {
        return DataBaseHandler.getInstance().query("findAllCategories");
    }

    public Categories getCategoryById(Integer id) {
        return DataBaseHandler.getInstance().singleQueryById("findCategoryById", "catId", Integer.valueOf(id));
    }
}
