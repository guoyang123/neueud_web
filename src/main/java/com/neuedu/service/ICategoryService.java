package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.UserInfo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {


    public ServerResponse<List<Category>> findSubCategoryById(int categoryId);

    public ServerResponse<String> addCategory(int parent_id, String categoryName);

    public  ServerResponse<String> updateCategory(Integer categoryId,String categoryName);


    public Set<Integer> findAllChildByCategory(Set<Integer> categoryidList, Integer categoryid);


}
