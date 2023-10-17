package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "squads")
public class SquadModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "squad_users",
            joinColumns = @JoinColumn(name = "id_squad"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<UserSquadModel> squadMembers;

    @ElementCollection
    @CollectionTable(name = "skills_required", joinColumns = @JoinColumn(name = "squad_id"))
    private List<String> skillsRequired;
}
