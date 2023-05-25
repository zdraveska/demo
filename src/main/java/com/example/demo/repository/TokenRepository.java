package com.example.demo.repository;


import com.example.demo.domain.common.UrlToken;
import com.example.demo.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<UrlToken, Long> {

  Optional<UrlToken> findByToken(String token);

  Optional<UrlToken> findByUser(User user);

}