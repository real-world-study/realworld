package com.study.realworld.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.realworld.favorite.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
