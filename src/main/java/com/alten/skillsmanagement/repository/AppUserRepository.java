package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.payload.UserOfSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);

    @Query("select new com.alten.skillsmanagement.model.AppUser(au.id, au.firstName, au.lastName) from AppUser au where au.firstName like %:term% or au.lastName like %:term% ")
    List<AppUser> searchByFullName(@Param("term") String term);
}
