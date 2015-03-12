package br.unicesumar.ads2015.livro;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Livro implements Serializable{
    
    @Id
    private Long id;
    private String titulo;
    private Integer ano;
    private Double peso;

    public Livro() {
    }

    public Livro(Long id, String titulo, Integer ano, Double peso) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.peso = peso;
    }
    
    

    @Override
    public String toString() {
        return "Livro{" + "id=" + id + ", titulo=" + titulo + ", ano=" + ano + ", peso=" + peso + '}';
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public Double getPeso() {
        return peso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Livro other = (Livro) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
