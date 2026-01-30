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
@Table(name ="Etudiants")

public class Etudiant {
    @Id
    @Column(name = "matricule" ,nullable = false)
    private String matricule;

    @Column(name = "nom" ,nullable = false)
    private String nom;


    private String prenom;

    private int age;


}
