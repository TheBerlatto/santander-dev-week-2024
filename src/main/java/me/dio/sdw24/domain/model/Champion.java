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
    public String generateContextByQuestion(String question) {
        return """
                Pergunta: %s
                Nome do Campeão: %s
                Função: %s
                Lore (História): %s
                """.formatted(question, this.name, this.role, this.lore);
    }
}
