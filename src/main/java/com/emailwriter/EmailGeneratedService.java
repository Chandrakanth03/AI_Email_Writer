package com.emailwriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailGeneratedService {

    private static final Logger logger = LoggerFactory.getLogger(EmailGeneratedService.class);
    private final WebClient webClient;


    @Value("${spring.ai.openai.api.url}")
    private String openaiApiUrl;

    @Value("${spring.ai.openai.api.key}")
    private String openaiApiKey;

    @Value("${spring.ai.openai.api.model}")
    private String openaiModel;


    public EmailGeneratedService(WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder.build();
    }

    public String generateEmailReply(EmailRequestDTO emailResponse) {
        // Build the prompt
        String prompt = buildPrompt(emailResponse);

        // Craft OpenAI request body
        Map<String, Object> requestBody = Map.of(
                "model", openaiModel,
                "messages", new Object[] {
                        Map.of("role", "system", "content", "You are an AI assistant that writes professional email replies."),
                        Map.of("role", "user", "content", prompt)
                }
        );

        try {
            String response = webClient.post()
                    .uri(openaiApiUrl)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + openaiApiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return extractResponseContent(response);
        } catch (Exception e) {
            logger.error("Error calling OpenAI API", e);
            return "Error calling OpenAI API: " + e.getMessage();
        }
    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            return rootNode.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            logger.error("Error processing OpenAI response", e);
            return "Error processing OpenAI response: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequestDTO emailResponse) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Write a professional email reply. Do not include a subject line. ");
        if (emailResponse.getTone() != null && !emailResponse.getTone().isEmpty()) {
            prompt.append("Use a ").append(emailResponse.getTone()).append(" tone. ");
        }
        prompt.append("Original email: ").append(emailResponse.getContent());
        return prompt.toString();
    }
}
