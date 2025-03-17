package team.themoment.imi.domain.club.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.imi.domain.club.data.response.ClubListResDto;
import team.themoment.imi.domain.club.service.GetClubService;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final GetClubService getClubService;

    @GetMapping
    public ClubListResDto getClub() {
        return getClubService.execute();
    }
}