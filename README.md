# Email Writer - AI Email Generator

A Spring Boot application that generates professional email replies using the OpenAI API (via OpenRouter).

## Features

✨ **Key Features:**
- **AI-Powered Email Generation**: Uses GPT-3.5 Turbo to generate intelligent email replies
- **Customizable Tone**: Choose from multiple tone options (professional, friendly, formal, casual, grateful, apologetic)
- **RESTful API**: Clean REST API for integration with other applications
- **Interactive Web UI**: Modern, user-friendly web interface for testing
- **Error Handling**: Comprehensive error handling and logging
- **Reactive Programming**: Uses Spring WebFlux for non-blocking operations

## Technology Stack

- **Framework**: Spring Boot 4.0.5
- **Language**: Java 21
- **Build Tool**: Maven
- **API Client**: Spring WebFlux
- **JSON Processing**: Jackson
- **Logging**: SLF4J
- **Frontend**: Vanilla JavaScript, HTML5, CSS3

## Project Structure

```
emailwriter/
├── src/
│   ├── main/
│   │   ├── java/com/emailwriter/
│   │   │   ├── EmailwriterApplication.java      # Main Spring Boot application
│   │   │   ├── EmailGenController.java          # REST controller
│   │   │   ├── EmailGeneratedService.java       # Core service logic
│   │   │   ├── EmailRequestDTO.java             # Request DTO
│   │   │   ├── EmailResponseDTO.java            # Response DTO
│   │   │   └── WebClientConfig.java             # WebClient configuration
│   │   └── resources/
│   │       ├── application.properties           # Configuration file
│   │       └── static/
│   │           └── index.html                   # Frontend UI
│   └── test/
│       └── java/com/emailwriter/
│           └── EmailRequestDTOTest.java         # Unit tests
├── pom.xml                                       # Maven configuration
└── README.md                                     # This file
```

## Prerequisites

- Java 21+
- Maven 3.6+
- Active OpenRouter API key (uses OpenAI's GPT-3.5 Turbo model)

## Installation & Setup

### 1. Clone the project
```bash
cd C:\Users\chand\OneDrive\Desktop\SpringBootCohort\emailwriter
```

### 2. Configure API Keys
Edit `src/main/resources/application.properties` and update your API key:

```properties
spring.ai.openai.api.url=https://openrouter.ai/api/v1/chat/completions
spring.ai.openai.api.model=gpt-3.5-turbo
spring.ai.openai.api.key=sk-or-v1-YOUR_API_KEY_HERE
```

### 3. Build the application
```bash
mvn clean install
```

### 4. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Generate Email Reply
**Endpoint**: `POST /api/v1/email/generate`

**Request Body**:
```json
{
  "content": "Your email content here",
  "tone": "professional"
}
```

**Tone Options**:
- `professional` - Formal and business-like
- `friendly` - Warm and approachable
- `formal` - Very formal and official
- `casual` - Relaxed and conversational
- `grateful` - Appreciative and thankful
- `apologetic` - Sorry and conciliatory

**Response** (Success):
```json
{
  "content": "Generated email reply here...",
  "status": "SUCCESS",
  "message": "Email generated successfully"
}
```

**Response** (Error):
```json
{
  "content": null,
  "status": "ERROR",
  "message": "Error details here"
}
```

### 2. Health Check
**Endpoint**: `GET /api/v1/email/health`

**Response**:
```
Email Writer API is running
```

## Using the Web Interface

1. Open your browser and navigate to `http://localhost:8080`
2. Paste the email you want to reply to in the "Email Content" field
3. Select your preferred tone from the dropdown
4. Click "Generate Email Reply"
5. The AI-generated reply will appear below
6. Copy the generated email and use it as needed

## Example Usage

### Using cURL
```bash
curl -X POST http://localhost:8080/api/v1/email/generate \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hi, can you provide an update on the project status?",
    "tone": "professional"
  }'
```

### Using JavaScript/Fetch
```javascript
fetch('/api/v1/email/generate', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    content: 'Your email here',
    tone: 'professional'
  })
})
.then(response => response.json())
.then(data => console.log(data.content))
.catch(error => console.error('Error:', error));
```

## Configuration

### application.properties
```properties
spring.application.name=emailwriter              # Application name
server.port=8080                                 # Server port
spring.ai.openai.api.url=...                    # OpenAI API endpoint
spring.ai.openai.api.model=gpt-3.5-turbo        # Model to use
spring.ai.openai.api.key=...                    # API key (KEEP THIS SECRET!)
```

## Building & Deployment

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/emailwriter-0.0.1-SNAPSHOT.jar
```

### Build Docker Image (Optional)
```bash
docker build -t emailwriter:latest .
docker run -p 8080:8080 emailwriter:latest
```

## Logging

The application logs important events to the console. Check the logs for:
- Email generation requests
- API responses
- Error details

## Error Handling

Common errors and solutions:

| Error | Solution |
|-------|----------|
| `Could not resolve placeholder 'OPENAI_API_KEY'` | Add API key to application.properties |
| `Error calling OpenAI API` | Check API key validity and internet connection |
| `Error processing OpenAI response` | Check API response format |

## Testing

Run unit tests:
```bash
mvn test
```

## Development Tips

1. **Modify System Prompt**: Edit the `buildPrompt()` method in `EmailGeneratedService.java` to customize email generation behavior
2. **Add New Tones**: Update the frontend dropdown in `index.html` and handle in the service
3. **Change Model**: Update `spring.ai.openai.api.model` in `application.properties`
4. **Custom Error Messages**: Modify error handling in `EmailGenController.java`

## Future Enhancements

- [ ] Add email storage/history functionality
- [ ] Implement user authentication and authorization
- [ ] Add email templates support
- [ ] Implement batch email generation
- [ ] Add language support for non-English emails
- [ ] Create mobile app
- [ ] Add email provider integrations (Gmail, Outlook)
- [ ] Implement cost tracking for API usage

## Security Considerations

⚠️ **Important**:
- Never commit your API keys to version control
- Use environment variables for sensitive data in production
- Validate and sanitize user inputs
- Implement rate limiting for production deployment
- Use HTTPS in production

## Troubleshooting

### Application won't start
- Check Java version: `java -version` (should be 21+)
- Verify Maven is installed: `mvn -version`
- Check if port 8080 is available

### API calls fail
- Verify API key is correct in `application.properties`
- Check internet connection
- Ensure OpenRouter service is accessible
- Check logs for detailed error messages

### Frontend not loading
- Clear browser cache (Ctrl+Shift+Delete)
- Check browser console for JavaScript errors (F12)
- Verify the application is running on `http://localhost:8080`

## Contributing

Feel free to fork this project and submit pull requests with improvements!

## License

This project is open source and available under the MIT License.

## Support

For issues, questions, or suggestions, please create an issue in the repository.

## Changelog

### Version 0.0.1 (Current)
- Initial release
- Email generation API
- Web UI for testing
- Support for multiple tones
- Error handling and logging

---

**Last Updated**: April 30, 2026

