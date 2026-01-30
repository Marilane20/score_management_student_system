package com.example.score_manage_system.repository;

import com.example.score_manage_system.entity.Etudiant;
import com.example.score_manage_system.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findByEtudiant(Etudiant etudiant);
}
