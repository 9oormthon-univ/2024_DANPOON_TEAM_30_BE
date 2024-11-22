package soon.ready_action.domain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.ready_action.domain.main.service.MainService;
import soon.ready_action.domain.main.dto.response.MainResponse;
import soon.ready_action.global.oauth2.service.TokenService;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/api/v1/main")
    public MainResponse getMainPage() {
        Long memberId = TokenService.getLoginMemberId();

        return mainService.getMainPage(memberId);
    }
}
