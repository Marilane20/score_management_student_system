package com.example.score_manage_system.controller;

import com.example.score_manage_system.entity.Etudiant;
import com.example.score_manage_system.services.EtudiantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    // READ - Liste des étudiants
    @GetMapping
    public String listEtudiants(Model model) {
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        Map<String, Double> moyennes = new HashMap<>();  // ← Changé de Map<Long, Double>

        for (Etudiant e : etudiants) {
            moyennes.put(e.getMatricule(), etudiantService.calculerMoyenneEtudiant(e));  // ← Utilise matricule
        }

        model.addAttribute("etudiants", etudiants);
        model.addAttribute("moyennes", moyennes);
        return "etudiants";
    }

    // CREATE - Afficher le formulaire
    @GetMapping("/add")
    public String addEtudiant(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        return "add-etudiant";
    }

    // CREATE - Enregistrer
    @PostMapping("/add")
    public String saveEtudiant(@ModelAttribute Etudiant etudiant) {
        etudiantService.createEtudiant(etudiant);
        return "redirect:/etudiants";
    }

    // UPDATE - Afficher le formulaire
    @GetMapping("/edit/{matricule}")  // ← Changé de {id}
    public String editEtudiant(@PathVariable String matricule, Model model) {  // ← Changé de Long id
        Etudiant etudiant = etudiantService.getEtudiantByMatricule(matricule);
        if (etudiant == null) {
            return "redirect:/etudiants";
        }
        model.addAttribute("etudiant", etudiant);
        return "edit-etudiant";
    }

    // UPDATE - Enregistrer les modifications
    @PostMapping("/edit/{matricule}")  // ← Changé de {id}
    public String updateEtudiant(@PathVariable String matricule, @ModelAttribute Etudiant etudiant) {
        etudiantService.updateEtudiant(matricule, etudiant);
        return "redirect:/etudiants";
    }

    // DELETE
    @GetMapping("/delete/{matricule}")  // ← Changé de {id}
    public String deleteEtudiant(@PathVariable String matricule) {
        etudiantService.deleteEtudiant(matricule);
        return "redirect:/etudiants";
    }
}
