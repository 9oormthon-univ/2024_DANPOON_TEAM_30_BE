package soon.ready_action.domain.house.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.ready_action.domain.house.dto.request.HouseFilterRequest;
import soon.ready_action.domain.house.dto.response.HouseResponse;
import soon.ready_action.domain.house.service.HouseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/houses")
public class HouseController extends HouseDocsController{

    private final HouseService houseService;

    @GetMapping
    public ResponseEntity<List<HouseResponse>> handleHouse(@RequestBody HouseFilterRequest request) {
        List<HouseResponse> house = houseService.getHouseByCityAndCounty(request);

        return ResponseEntity.ok(house);
    }
}
