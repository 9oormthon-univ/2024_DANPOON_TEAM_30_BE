package soon.ready_action.domain.chatbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import soon.ready_action.domain.chatbot.dto.request.ChatbotRequest;
import soon.ready_action.domain.chatbot.dto.response.ChatbotResponse;
import soon.ready_action.domain.chatbot.entity.Chatbot;
import soon.ready_action.domain.chatbot.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chatbot")
@Tag(name = "Chatbot Controller", description = "Chatbot API")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @Operation(summary = "챗봇 문답 전송", description = "질문유형과 depth에 따른 챗봇 문답 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ChatbotResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<ChatbotResponse>> getChatbotResponses(
            @RequestParam int questionType,
            @RequestParam int depth) {

        // ChatbotRequest 객체 생성
        ChatbotRequest requestDto = new ChatbotRequest(questionType, depth);

        List<Chatbot> responses = chatbotService.getChatbotResponses(requestDto);
        List<ChatbotResponse> responseDtos = responses.stream()
                .map(ChatbotResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }
}