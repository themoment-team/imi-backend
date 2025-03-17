package team.themoment.imi.global.mapper;

import org.springframework.stereotype.Component;
import team.themoment.imi.domain.profile.dto.ProfileListResDto;
import team.themoment.imi.domain.profile.dto.ProfileResDto;
import team.themoment.imi.domain.profile.entity.Profile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {
    public ProfileResDto toProfileResDto(Profile profile){
        return ProfileResDto.builder()
                .userId(profile.getUser().getId())
                .wanted(profile.getWanted())
                .major(profile.getMajor())
                .content(profile.getContent())
                .build();
    }
    public ProfileListResDto toProfileListResDto(List<Profile> profiles){
        return ProfileListResDto.builder()
                .profileList(profiles.stream().map(this::toProfileResDto).collect(Collectors.toList()))
                .build();
    }
}
