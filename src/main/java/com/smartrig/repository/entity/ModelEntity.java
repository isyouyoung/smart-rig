package com.smartrig.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MODEL")
public class ModelEntity {

    @Id
    private Long modelId;
}
