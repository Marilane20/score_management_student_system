package com.example.score_manage_system.repository;

import com.example.score_manage_system.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {
}
