/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package posmy.interview.boot.repository;

/**
 *
 * @author afdzal
 */
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import posmy.interview.boot.model.Attempts;


@Repository 
public interface AttemptsRepository extends JpaRepository<Attempts, Integer> { 
   Optional<Attempts> findAttemptsByUsername(String username); 
}
