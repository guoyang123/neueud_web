package com.neuedu.dao;

import com.neuedu.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface ICategoryDao {

    /**
     * 查询类别信息
     * */
    public Category findCategoryById(int categoryId);

    public List<Category> findSubCategoryByCategoryId(int categoryId);

   public  int   addCategory(int parent_id,String categoryName);

    public  int   updateCategoryByCategoryId(Category category);

}
