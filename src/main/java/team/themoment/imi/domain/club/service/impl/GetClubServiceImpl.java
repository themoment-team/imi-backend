package team.themoment.imi.domain.club.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.club.data.response.ClubListResDto;
import team.themoment.imi.domain.club.enitity.Club;
import team.themoment.imi.domain.club.repository.ClubJpaRepository;
import team.themoment.imi.domain.club.service.GetClubService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetClubServiceImpl implements GetClubService {

    private final ClubJpaRepository clubJpaRepository;

    @Override
    public ClubListResDto execute() {
        List<Club> clubs = clubJpaRepository.findAll();
        return new ClubListResDto(clubs.size(), clubs);
    }
}