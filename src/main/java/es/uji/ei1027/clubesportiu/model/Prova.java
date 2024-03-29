package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Prova {
    private String nom;
    private String descripcio;
    private String tipus;
    //private LocalDate data;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
  // @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
   //private LocalDate data;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
