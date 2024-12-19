package com.hari.gatherspace.service;


import com.hari.gatherspace.model.User;
import com.hari.gatherspace.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.List;
import java.util.Optional;


@Service

public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Transactional
    public void updateUser( User user){

         User usser = userRepository.save(user);
        System.out.println(user.getAvatarId());
         return;
    }

    public List<User> getUsersById(List<String> ids){
        return userRepository.findAllById(ids);
    }
    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public Optional<User> getUser(String username){
        return userRepository.findByUsername(username);
    }


    public Optional<User> getUserById(String id){
        return userRepository.findById(id);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = getUser(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("user not found");
        }
        System.out.println(user.get().getUsername());
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),user.get().getPassword(), java.util.Collections.emptyList());
    }

    public void removeUser(String userId) {

    }
}

