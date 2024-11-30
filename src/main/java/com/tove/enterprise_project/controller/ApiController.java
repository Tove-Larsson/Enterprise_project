package com.tove.enterprise_project.controller;

import com.tove.enterprise_project.model.dto.ApiGameDTO;
import com.tove.enterprise_project.service.ApiService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;


    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/games/{title}")
    public Mono<List<ApiGameDTO>> getGames(
            @PathVariable("title") String title,
            @RequestParam(required = false) String maxDate,
            @RequestParam(required = false) String minDate) {

        return apiService.fetchGamesByTitle(title, maxDate, minDate);
    }


}
