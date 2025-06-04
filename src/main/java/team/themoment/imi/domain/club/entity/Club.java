package team.themoment.imi.domain.club.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;

@Entity
@Getter
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String leader;
    private String mainContent;
    private String notionUrl;
    @Column(columnDefinition = "VARCHAR(512)")
    private String iconUrl;
}