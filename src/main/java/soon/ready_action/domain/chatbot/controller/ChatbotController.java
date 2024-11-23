package soon.ready_action.domain.chatbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import soon.ready_action.domain.chatbot.dto.request.ChatbotRequest;
import soon.ready_action.domain.chatbot.dto.response.ChatbotResponse;
import soon.ready_action.domain.chatbot.entity.Chatbot;
import soon.ready_action.domain.chatbot.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chatbot")
@Tag(name = "Chatbot Controller", description = "Chatbot API")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @Operation(summary = "챗봇 depth 전송", description = "QuestionType에 따른 depth 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/depth-list")
    public ResponseEntity<?> getChatbotResponses(@RequestParam int questionType) {
        // QuestionType에 따른 응답 데이터 정의
        Map<Integer, List<String>> questionTypeData = new HashMap<>();
        questionTypeData.put(0, List.of("건강보험", "심리 상담 치료", "의료비 감면 문제", "비급여 의료비 지원"));
        questionTypeData.put(1, List.of("자립정착금 추가 수령", "워킹홀리데이 자립수당", "금융 관리 팁", "금융 법률", "금융 상담 서비스"));
        questionTypeData.put(2, List.of("취업 지원", "구직자 지원", "이력서 작성 팁"));
        questionTypeData.put(3, List.of("자립생활관", "자립정착금", "LH전세임대주택", "주거 보수"));
        questionTypeData.put(4, List.of("온라인 교육 플랫폼", "교육 신청 서류", "기술 교육"));

        // 요청받은 questionType이 유효하지 않은 경우 예외 처리
        if (!questionTypeData.containsKey(questionType)) {
            return ResponseEntity.badRequest().body("Invalid questionType value");
        }

        // 해당 questionType의 데이터 반환
        List<String> response = questionTypeData.get(questionType);
        return ResponseEntity.ok(response);
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