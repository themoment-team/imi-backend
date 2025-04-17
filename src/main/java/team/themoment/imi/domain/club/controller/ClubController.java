package team.themoment.imi.domain.club.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.imi.domain.club.data.response.ClubListResDto;
import team.themoment.imi.domain.club.service.ClubService;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService getClubService;

    @GetMapping
    public ClubListResDto getClub() {
        return getClubService.getAllClubs();
    }
}