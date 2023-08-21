package com.codeit.coronavirustracker;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeit.coronavirustracker.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
 
}

