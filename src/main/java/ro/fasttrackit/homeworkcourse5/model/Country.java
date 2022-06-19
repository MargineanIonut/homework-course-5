package ro.fasttrackit.homeworkcourse5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@AllArgsConstructor
@Getter
public class Country {
    @Id
    private final String id;
    private final String name;
    private final String capital;
    private final Long population;
    private final String area;
    private final String continent;
    private final String neighbours;
}
