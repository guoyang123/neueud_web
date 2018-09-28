package com.neuedu.dao;

import com.neuedu.pojo.Shopping;

public interface IAddressDao {

    public Shopping selectShippingByuserid(Integer userid);
}
