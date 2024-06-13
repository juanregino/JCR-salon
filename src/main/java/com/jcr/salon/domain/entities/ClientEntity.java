package com.jcr.salon.domain.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "client")  
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(length = 100 , nullable = false)
  private String name;
  @Column(length = 100 , nullable = false)
  private String lastName;
  @Column(length = 20)
  private String phone;
  @Column(length = 100 , nullable = false) 
  private String email;
  

  //se hace como una relacion many to one , lo unico es que la llave debe ser unica
  @OneToOne(fetch = FetchType.LAZY)
  //Como client va a ser la entidad debil por eso se le coloca el join column
  @JoinColumn(name = "user_id" ,referencedColumnName = "id")
  private User user;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
             mappedBy = "client", 
             fetch = FetchType.EAGER,
             cascade = CascadeType.ALL, 
             orphanRemoval = false)
  private List<Appointment> appointments;

  
}
