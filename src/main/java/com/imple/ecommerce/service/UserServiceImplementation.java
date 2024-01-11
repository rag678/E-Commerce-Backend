package com.imple.ecommerce.service;

import com.imple.ecommerce.config.JwtProvider;
import com.imple.ecommerce.exception.UserException;
import com.imple.ecommerce.model.User;
import com.imple.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserbyId(Long userid) throws UserException {
        Optional<User> user = userRepository.findById(userid);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id - "+ userid);
    }

    @Override
    public User finduserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UserException("user not found with email"+ email);
        }
        return user;

    }
}
