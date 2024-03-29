package me.dio.sdw24.adapters.out;

import feign.FeignException;
import feign.RequestInterceptor;
import me.dio.sdw24.domain.ports.GenerativeAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//O Feign é uma biblioteca especialista em HTTP
@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI")
@FeignClient(name = "geminiApi", url = "${gemini.base-url}", configuration = GoogleGeminiService.Config.class)
public interface GoogleGeminiService extends GenerativeAiService {

    //Baseando-se em: https://platform.openai.com/docs/api-reference/chat/create

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GoogleGeminiResp textOnlyInput(GoogleGeminiReq req);

    @Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s
                """.formatted(objective, context);

        GoogleGeminiReq req = new GoogleGeminiReq(
                List.of(new Content(List.of(new Part(prompt))))
        );
        try {
            GoogleGeminiResp resp = textOnlyInput(req);
            return resp.candidates().getFirst().content().parts().getFirst().text();
        } catch (FeignException httpErrors) {
            return "Deu ruim! Erro de comunicação com a Api do Google Gemini.";
        } catch (Exception unexpectedError) {
            return "Deu mais ruim ainda! O retorno da API do Google Gemini não contém os dados esperados";
        }
    }

    //Records desempenham papel de DTO (Data Transfer Object)
    record GoogleGeminiReq(List<Content> contents) { }
    record Content(List<Part> parts) { }
    record Part(String text) { }

    record GoogleGeminiResp(List<Candidate> candidates) { }
    record Candidate(Content content) { }

    // Eu não coloquei API Key do Gemini, portanto a aplicação não vai subir!
    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.query("key", apiKey);
        }
    }
}
