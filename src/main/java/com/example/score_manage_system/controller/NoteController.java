package com.example.score_manage_system.controller;

import com.example.score_manage_system.entity.Note;
import com.example.score_manage_system.services.EtudiantService;
import com.example.score_manage_system.services.MatiereService;
import com.example.score_manage_system.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final EtudiantService etudiantService;
    private final MatiereService matiereService;

    public NoteController(NoteService noteService, EtudiantService etudiantService, MatiereService matiereService) {
        this.noteService = noteService;
        this.etudiantService = etudiantService;
        this.matiereService = matiereService;
    }

    // READ - Liste des notes
    @GetMapping
    public String listNotes(Model model) {
        model.addAttribute("notes", noteService.getAllNotes());
        return "notes";
    }

    // CREATE - Afficher le formulaire
    @GetMapping("/add")
    public String addNote(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        return "add-note";
    }

    // CREATE - Enregistrer
    @PostMapping("/add")
    public String saveNote(@RequestParam String etudiantMatricule,
                           @RequestParam String matiereCode,  // ← Changé de Long matiereId
                           @RequestParam double valeur) {
        Note note = new Note();
        note.setEtudiant(etudiantService.getEtudiantByMatricule(etudiantMatricule));
        note.setMatiere(matiereService.getMatiereByCode(matiereCode));  // ← Changé
        note.setValeur(valeur);
        noteService.createNote(note);
        return "redirect:/notes";
    }

    // UPDATE - Afficher le formulaire
    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return "redirect:/notes";
        }
        model.addAttribute("note", note);
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        return "edit-note";
    }

    // UPDATE - Enregistrer les modifications
    @PostMapping("/edit/{id}")
    public String updateNote(@PathVariable Long id,
                             @RequestParam String etudiantMatricule,
                             @RequestParam String matiereCode,  // ← Changé de Long matiereId
                             @RequestParam double valeur) {
        Note note = new Note();
        note.setEtudiant(etudiantService.getEtudiantByMatricule(etudiantMatricule));
        note.setMatiere(matiereService.getMatiereByCode(matiereCode));  // ← Changé
        note.setValeur(valeur);
        noteService.updateNote(id, note);
        return "redirect:/notes";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return "redirect:/notes";
    }
}