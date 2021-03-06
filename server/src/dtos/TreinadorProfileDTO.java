package dtos;

import entities.Escalao;
import entities.Modalidade;
import entities.Treinador;

import javax.persistence.Id;
import java.util.LinkedHashSet;
import java.util.Set;

public class TreinadorProfileDTO {

    private long idSocio;
    private String name;
    private String email;
    @Id
    private String username;
    private Set<String> escaloes;
    private Set<String> modalidades;
    private Set<String> atletas;


    public TreinadorProfileDTO() {
        this.atletas = new LinkedHashSet<String>();
        this.escaloes = new LinkedHashSet<String>();
        this.modalidades = new LinkedHashSet<String>();
    }

    public TreinadorProfileDTO(long idSocio, String username, String name, String email) {
        this.idSocio = idSocio;
        this.username = username;
        this.name = name;
        this.email = email;
        this.atletas = new LinkedHashSet<String>();
        this.escaloes = new LinkedHashSet<String>();
        this.modalidades = new LinkedHashSet<String>();
    }
    public TreinadorProfileDTO(long idSocio, String username, String name, String email,LinkedHashSet<String> escaloes,LinkedHashSet<String> modalidades,LinkedHashSet<String> atletas) {
        this.idSocio = idSocio;
        this.username = username;
        this.name = name;
        this.email = email;
        this.escaloes = escaloes;
        this.modalidades= modalidades;
        this.atletas = atletas;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(long idSocio) {
        this.idSocio = idSocio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(Set<String> escaloes) {
        this.escaloes = escaloes;
    }

    public Set<String> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<String> modalidades) {
        this.modalidades = modalidades;
    }

    public Set<String> getAtletas() {
        return atletas;
    }

    public void setAtletas(Set<String> atletas) {
        this.atletas = atletas;
    }

    public void addEscalao(String escalao){
        if(!this.escaloes.contains(escalao)){
            escaloes.add(escalao);
        }
    }
    public void removeEscalao(String escalao){
        if(this.escaloes.contains(escalao)){
            escaloes.remove(escalao);
        }
    }
    public void addModalidade(String modalidade){
        if(!this.modalidades.contains(modalidade)){
            modalidades.add(modalidade);
        }
    }
    public void removeModalidade(Modalidade modalidade){
        if(this.modalidades.contains(modalidade)){
            modalidades.remove(modalidade);
        }
    }
    public void addAtletas(String atleta){
        if(!this.atletas.contains(atleta)){
            atletas.add(atleta);
        }
    }
    public void removeAtletas(String atleta){
        if(this.atletas.contains(atleta)){
            atletas.remove(atleta);
        }
    }
}