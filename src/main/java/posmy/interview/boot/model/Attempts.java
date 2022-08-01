/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package posmy.interview.boot.model;

/**
 *
 * @author afdzal
 */
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id; 
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity 
@Table(name = "attempts")
public class Attempts { 
   @Id 
   @GeneratedValue(strategy = GenerationType.IDENTITY) 
   private int id;
   private String username; 
   private int attempts;
   
   
}