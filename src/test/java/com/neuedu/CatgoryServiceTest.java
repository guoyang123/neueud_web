package com.neuedu;

import com.neuedu.service.ICategoryService;
import com.neuedu.service.impl.CategoryServiceImpl;
import org.junit.Test;

public class CatgoryServiceTest {


    @Test
    public void  test(){

        ICategoryService categoryService=new CategoryServiceImpl();
        System.out.println( categoryService.findSubCategoryById(100032));

    }

}
