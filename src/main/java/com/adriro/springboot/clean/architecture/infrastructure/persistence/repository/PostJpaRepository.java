package com.adriro.springboot.clean.architecture.infrastructure.persistence.repository;

import com.adriro.springboot.clean.architecture.infrastructure.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long>, PagingAndSortingRepository<PostEntity, Long> {

}
