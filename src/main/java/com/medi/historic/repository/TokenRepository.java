package com.medi.historic.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medi.historic.entity.Token;

public interface TokenRepository extends JpaRepository<Token,UUID>{

}
