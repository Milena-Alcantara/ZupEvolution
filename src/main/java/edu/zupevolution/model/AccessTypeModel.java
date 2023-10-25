package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Access_Type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTypeModel {
    @Id
    private Long id;

    @Column(name = "type" )
    private String type;
}