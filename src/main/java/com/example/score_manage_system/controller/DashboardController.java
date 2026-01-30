package com.example.score_manage_system.controller;

import com.example.score_manage_system.entity.Etudiant;
import com.example.score_manage_system.services.EtudiantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    private final EtudiantService etudiantService;

    public DashboardController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        // Statistiques
        model.addAttribute("moyenneClasse", etudiantService.moyenneClasse());
        model.addAttribute("totalEtudiants", etudiantService.getAllEtudiants().size());
        model.addAttribute("totalAdmis", etudiantService.getEtudiantsAdmis().size());

        Etudiant premier = etudiantService.getPremier();
        Etudiant dernier = etudiantService.getDernier();

        model.addAttribute("premier", premier);
        model.addAttribute("dernier", dernier);

        if (premier != null) {
            model.addAttribute("moyennePremier", etudiantService.calculerMoyenneEtudiant(premier));
        }

        if (dernier != null) {
            model.addAttribute("moyenneDernier", etudiantService.calculerMoyenneEtudiant(dernier));
        }

        // Classement complet avec moyennes
        List<Etudiant> classement = etudiantService.trierEtudiantsParMerite();
        Map<String, Double> moyennes = new HashMap<>();  // ← Changé de Map<Long, Double>

        for (Etudiant e : classement) {
            moyennes.put(e.getMatricule(), etudiantService.calculerMoyenneEtudiant(e));  // ← Utilise matricule
        }

        model.addAttribute("classement", classement);
        model.addAttribute("moyennes", moyennes);

        return "dashboard";
    }

    @GetMapping("/admis")
    public String afficherAdmis(Model model) {
        List<Etudiant> admis = etudiantService.getEtudiantsAdmis();
        Map<String, Double> moyennes = new HashMap<>();  // ← Changé de Map<Long, Double>

        for (Etudiant e : admis) {
            moyennes.put(e.getMatricule(), etudiantService.calculerMoyenneEtudiant(e));  // ← Utilise matricule
        }

        model.addAttribute("admis", admis);
        model.addAttribute("moyennes", moyennes);

        return "admis";
    }

    @GetMapping("/superieurs-moyenne")
    public String afficherSuperieursMoyenne(Model model) {
        double moyClasse = etudiantService.moyenneClasse();
        List<Etudiant> superieurs = etudiantService.getEtudiantsSuperieursMoyenneClasse();
        Map<String, Double> moyennes = new HashMap<>();  // ← Changé de Map<Long, Double>

        for (Etudiant e : superieurs) {
            moyennes.put(e.getMatricule(), etudiantService.calculerMoyenneEtudiant(e));  // ← Utilise matricule
        }

        model.addAttribute("moyenneClasse", moyClasse);
        model.addAttribute("superieurs", superieurs);
        model.addAttribute("moyennes", moyennes);

        return "superieurs-moyenne";
    }
}
