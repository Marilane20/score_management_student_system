package com.example.score_manage_system.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Notes")

public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_matricule", nullable = false)  // ← Changé de etudiant_id à etudiant_matricule
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "matiere_code", nullable = false)
    private Matiere matiere;

    private double valeur;
}
