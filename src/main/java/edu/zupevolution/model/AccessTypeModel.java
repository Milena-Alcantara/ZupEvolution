package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Access_Type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTypeModel {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;
}