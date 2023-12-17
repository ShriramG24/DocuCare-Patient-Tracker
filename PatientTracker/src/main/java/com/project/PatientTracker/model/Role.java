package com.project.PatientTracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @Getter @Setter
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  @Getter @Setter
  private ERole name;

  public Role() {

  }

  public Role(ERole name) {
    this.name = name;
  }
}
