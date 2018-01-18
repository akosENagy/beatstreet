package com.cybersoft.beatstreet.repository;

import com.cybersoft.beatstreet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

}
