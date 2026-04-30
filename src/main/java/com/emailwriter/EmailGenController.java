package com.emailwriter;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@AllArgsConstructor
public class EmailGenController {
    private static final Logger logger = LoggerFactory.getLogger(EmailGenController.class);
    private final EmailGeneratedService emailGeneratedService;

    @PostMapping("/generate")
    public ResponseEntity<EmailResponseDTO> generateEmail(@RequestBody EmailRequestDTO emailRequest) {
        try {
            logger.info("Received email generation request with tone: {}", emailRequest.getTone());
            String generatedEmail = emailGeneratedService.generateEmailReply(emailRequest);

            if (generatedEmail.startsWith("Error")) {
                logger.error("Error generating email: {}", generatedEmail);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new EmailResponseDTO(null, "ERROR", generatedEmail));
            }

            logger.info("Email generated successfully");
            return ResponseEntity.ok(new EmailResponseDTO(generatedEmail, "SUCCESS", "Email generated successfully"));
        } catch (Exception e) {
            logger.error("Unexpected error in email generation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmailResponseDTO(null, "ERROR", "An unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Email Writer API is running");
    }
}
