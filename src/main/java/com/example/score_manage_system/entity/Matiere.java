package com.example.score_manage_system.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Matieres")

public class Matiere {
    @Id
    @Column( name ="code",nullable = false)
    private  String code;

    private int coef;
    private String nom;
}
