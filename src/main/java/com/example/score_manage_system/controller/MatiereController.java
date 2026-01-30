package com.example.score_manage_system.controller;

import com.example.score_manage_system.entity.Matiere;
import com.example.score_manage_system.services.MatiereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matieres")
public class MatiereController {

    private final MatiereService matiereService;

    public MatiereController(MatiereService matiereService) {
        this.matiereService = matiereService;
    }

    // READ - Liste des matières
    @GetMapping
    public String listMatieres(Model model) {
        model.addAttribute("matieres", matiereService.getAllMatieres());
        return "matieres";
    }

    // CREATE - Afficher le formulaire
    @GetMapping("/add")
    public String addMatiere(Model model) {
        model.addAttribute("matiere", new Matiere());
        return "add-matiere";
    }

    // CREATE - Enregistrer
    @PostMapping("/add")
    public String saveMatiere(@ModelAttribute Matiere matiere) {
        matiereService.createMatiere(matiere);
        return "redirect:/matieres";
    }

    // UPDATE - Afficher le formulaire
    @GetMapping("/edit/{code}")  // ← Changé de {id}
    public String editMatiere(@PathVariable String code, Model model) {  // ← Changé de Long id
        Matiere matiere = matiereService.getMatiereByCode(code);
        if (matiere == null) {
            return "redirect:/matieres";
        }
        model.addAttribute("matiere", matiere);
        return "edit-matiere";
    }

    // UPDATE - Enregistrer les modifications
    @PostMapping("/edit/{code}")  // ← Changé de {id}
    public String updateMatiere(@PathVariable String code, @ModelAttribute Matiere matiere) {
        matiereService.updateMatiere(code, matiere);
        return "redirect:/matieres";
    }

    // DELETE
    @GetMapping("/delete/{code}")  // ← Changé de {id}
    public String deleteMatiere(@PathVariable String code) {
        matiereService.deleteMatiere(code);
        return "redirect:/matieres";
    }
}
