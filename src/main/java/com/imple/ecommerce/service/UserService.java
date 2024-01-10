package com.imple.ecommerce.service;

import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.User;

public interface UserService {
    public User findUserbyId(Long userid) throws UserException;
    public User finduserProfileByJwt(String jwt) throws UserException;
}
