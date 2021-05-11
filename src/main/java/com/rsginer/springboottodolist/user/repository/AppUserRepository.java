package com.rsginer.springboottodolist.user.repository;

import com.rsginer.springboottodolist.user.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

public interface AppUserRepository extends CrudRepository<AppUser, UUID> {
     AppUser findByUsername(String username);
}
