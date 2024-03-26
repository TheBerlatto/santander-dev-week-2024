package me.dio.sdw24.domain.ports;

import me.dio.sdw24.domain.model.Champions;

import java.util.List;
import java.util.Optional;

//Repositório de acesso aos dados!
public interface ChampionsRepository {

    List<Champions> findAll();

    Optional<Champions> findById(Long id);
}
