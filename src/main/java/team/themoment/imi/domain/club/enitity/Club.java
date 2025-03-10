package team.themoment.imi.domain.club.enitity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Club {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String leader;
    private String mainContent;
    private String iconUrl;
}
