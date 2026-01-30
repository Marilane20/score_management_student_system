package com.example.score_manage_system.services;


import com.example.score_manage_system.entity.Etudiant;
import com.example.score_manage_system.entity.Note;
import com.example.score_manage_system.repository.EtudiantRepository;
import com.example.score_manage_system.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final NoteRepository noteRepository;

    public EtudiantService( EtudiantRepository etudiantRepository ,NoteRepository noteRepository){
        this.etudiantRepository =etudiantRepository;
        this.noteRepository = noteRepository;
    }

    // CRUD de base
    public Etudiant createEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Etudiant getEtudiantByMatricule(String matricule) {  // ← Changé de getEtudiantById
        return etudiantRepository.findById(matricule).orElse(null);
    }

    public void deleteEtudiant(String matricule) {  // ← Changé de Long id
        etudiantRepository.deleteById(matricule);
    }

    public Etudiant updateEtudiant(String matricule, Etudiant updatedEtudiant) {  // ← Changé de Long id
        return etudiantRepository.findById(matricule).map(etudiant -> {
            etudiant.setNom(updatedEtudiant.getNom());
            etudiant.setPrenom(updatedEtudiant.getPrenom());
            etudiant.setAge(updatedEtudiant.getAge());
            return etudiantRepository.save(etudiant);
        }).orElse(null);
    }

    // Calcul de la moyenne d'un étudiant
    public double calculerMoyenneEtudiant(Etudiant etudiant) {
        List<Note> notes = noteRepository.findByEtudiant(etudiant);

        if (notes.isEmpty()) {
            return 0.0;
        }

        double somme = 0;
        int totalCoef = 0;

        for (Note note : notes) {
            somme += note.getValeur() * note.getMatiere().getCoef();
            totalCoef += note.getMatiere().getCoef();
        }

        return totalCoef > 0 ? somme / totalCoef : 0.0;
    }

    // Moyenne de la classe
    public double moyenneClasse() {
        List<Etudiant> etudiants = getAllEtudiants();

        if (etudiants.isEmpty()) {
            return 0.0;
        }

        double somme = 0;
        for (Etudiant e : etudiants) {
            somme += calculerMoyenneEtudiant(e);
        }

        return somme / etudiants.size();
    }

    // Trier par mérite (ordre décroissant de moyenne)
    public List<Etudiant> trierEtudiantsParMerite() {
        return getAllEtudiants().stream()
                .sorted((e1, e2) -> Double.compare(
                        calculerMoyenneEtudiant(e2),
                        calculerMoyenneEtudiant(e1)
                ))
                .collect(Collectors.toList());
    }

    // Premier de la classe
    public Etudiant getPremier() {
        List<Etudiant> tries = trierEtudiantsParMerite();
        return tries.isEmpty() ? null : tries.get(0);
    }

    // Dernier de la classe
    public Etudiant getDernier() {
        List<Etudiant> tries = trierEtudiantsParMerite();
        return tries.isEmpty() ? null : tries.get(tries.size() - 1);
    }

    // Étudiants admis (moyenne >= 10)
    public List<Etudiant> getEtudiantsAdmis() {
        return getAllEtudiants().stream()
                .filter(e -> calculerMoyenneEtudiant(e) >= 10)
                .collect(Collectors.toList());
    }

    // Étudiants supérieurs à la moyenne de la classe
    public List<Etudiant> getEtudiantsSuperieursMoyenneClasse() {
        double moyClasse = moyenneClasse();
        return getAllEtudiants().stream()
                .filter(e -> calculerMoyenneEtudiant(e) >= moyClasse)
                .collect(Collectors.toList());
    }

}
