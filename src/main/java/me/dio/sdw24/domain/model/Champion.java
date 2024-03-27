package me.dio.sdw24.domain.model;

//Camada de domínio a aplicação não conhecem o Spring, obedecendo a Clean Architecture
//O modelo é um reflexo do banco de dados!
public record Champion(
        Long id,
        String name,
        String role,
        String lore,
        String imageUrl
) {

}
