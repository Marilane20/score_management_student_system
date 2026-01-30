package com.example.score_manage_system.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();


}
