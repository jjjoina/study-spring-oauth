package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.entity.User;

// CRUD 함수를 JpaRepository가 들고 있음
// @Repository가 없어도 IoC된다. JpaRepository를 상속했기 때문이다.
public interface UserRepository extends JpaRepository<User, Integer> {

}
