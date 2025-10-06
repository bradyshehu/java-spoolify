package org.lessons.spoolify.java.java_spoolify.services;

import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.models.User;
import org.lessons.spoolify.java.java_spoolify.repositories.UserRepository;
import org.lessons.spoolify.java.java_spoolify.security.DBUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DBUserDetailsService implements UserDetailsService{
    
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String username){

        Optional<User> userAttempt = userRepository.findByUsername(username);

        if(userAttempt.isPresent()){
            return new DBUserDetails(userAttempt.get());
        }
        else{
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
    }
}