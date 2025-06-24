package team.themoment.imi.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.club.data.response.ClubListResDto;
import team.themoment.imi.domain.club.entity.Club;
import team.themoment.imi.domain.club.repository.ClubJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubJpaRepository clubJpaRepository;

    @Cacheable(key = "'clubs'", value = "clubs")
    public ClubListResDto getAllClubs() {
        List<Club> clubs = clubJpaRepository.findAll();
        return new ClubListResDto(clubs.size(), clubs);
    }
}