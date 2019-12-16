package com.it_academyproject.repositories;


import com.it_academyproject.Domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>
{
    Role findOneById( Integer id );
}
