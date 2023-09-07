package com.iam.serviceacquisition.controller;

import com.iam.serviceacquisition.domain.dto.SearchRequestDTO;
import com.iam.serviceacquisition.domain.search.dto.SearchTeamResponse;
import com.iam.serviceacquisition.service.TalentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/talent")
@Slf4j
public class TalentController {

    private final TalentService talentService;

    @PostMapping(value = "/search", produces = "application/json")
    public ResponseEntity<Page<SearchTeamResponse>> searchTalentsByCriteria(@Valid @RequestBody SearchRequestDTO searchRequestDTO) {
        log.info("Retrieving Talents by Refactor");
        return ResponseEntity.ok(talentService.getTeamsByCriteria(searchRequestDTO));
    }

}
