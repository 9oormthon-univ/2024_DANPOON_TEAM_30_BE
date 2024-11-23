package soon.ready_action.domain.scrap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import soon.ready_action.domain.scrap.service.ScrapService;

@RequiredArgsConstructor
@RestController
public class ScrapController {

    private final ScrapService scrapService;

}
