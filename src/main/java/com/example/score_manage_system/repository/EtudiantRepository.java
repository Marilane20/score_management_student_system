package com.example.score_manage_system.repository;

import com.example.score_manage_system.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, String>{
}
