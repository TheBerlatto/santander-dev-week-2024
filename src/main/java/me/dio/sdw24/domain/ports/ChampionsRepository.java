package me.dio.sdw24.domain.ports;

import me.dio.sdw24.domain.model.Champion;

import java.util.List;
import java.util.Optional;

//Repositório de acesso aos dados!
public interface ChampionsRepository {

    List<Champion> findAll();

    Optional<Champion> findById(Long id);
}
