package bl;

import common.DataBaseHandler;
import models.Categories;

import java.util.List;

public class CategoryBL {
    private static CategoryBL _instance;

    private static Integer caregoryId;

    public synchronized static Integer getNextId() {
        return ++caregoryId;
    }

    private CategoryBL() {
        //initCategoryIndex();
    }

    private void initCategoryIndex() {
        caregoryId = ((Categories)DataBaseHandler.getInstance().query("findMaxCategoryId").get(0)).getId();
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

    public List<Categories> getAllCategories(String content) {
        return DataBaseHandler.getInstance().queryByParams("findCategoriesByContent", content);
    }

    public void addCategory(Categories newCategory) {
        DataBaseHandler.getInstance().Persist(newCategory);
    }
}
