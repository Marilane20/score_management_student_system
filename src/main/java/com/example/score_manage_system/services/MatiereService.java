package com.example.score_manage_system.services;


import com.example.score_manage_system.entity.Matiere;
import com.example.score_manage_system.repository.MatiereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatiereService {

    private final MatiereRepository matiereRepository;

    public MatiereService(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    public Matiere createMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    public Matiere getMatiereByCode(String code) {  // ← Changé de getMatiereById
        return matiereRepository.findById(code).orElse(null);
    }

    public void deleteMatiere(String code) {  // ← Changé de Long id
        matiereRepository.deleteById(code);
    }

    public Matiere updateMatiere(String code, Matiere updatedMatiere) {  // ← Changé de Long id
        return matiereRepository.findById(code).map(matiere -> {
            matiere.setNom(updatedMatiere.getNom());
            matiere.setCoef(updatedMatiere.getCoef());
            // Note: Le code ne change pas car c'est la clé primaire
            return matiereRepository.save(matiere);
        }).orElse(null);
    }
}
