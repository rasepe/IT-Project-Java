package com.it_academyproject.repositories;

import com.it_academyproject.Domains.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository <Seat, Integer>
{
    List<Seat> findByRowNumberAndColNumber (int row , int col );
}
