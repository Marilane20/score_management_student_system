package com.example.score_manage_system.services;

import com.example.score_manage_system.entity.Note;
import com.example.score_manage_system.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public Note updateNote(Long id, Note updatedNote) {
        return noteRepository.findById(id).map(note -> {
            note.setEtudiant(updatedNote.getEtudiant());
            note.setMatiere(updatedNote.getMatiere());
            note.setValeur(updatedNote.getValeur());
            return noteRepository.save(note);
        }).orElse(null);
    }


}
