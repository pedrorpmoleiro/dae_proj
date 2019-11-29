package entities;

import java.util.LinkedList;
import java.util.List;
@Entity
public class Modalidade {
    private List<Socio> socios;
    private List<Treinador> treinadores;
    private String nome;
    private List<Escalao> escaloes;

    public Modalidade( String nome) {
        this.socios = new LinkedList<Socio>();
        this.treinadores = new LinkedList<Treinador>();
        this.nome = nome;
        this.escaloes = new LinkedList<Escalao>();
    }
    public Modalidade() {
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
    }

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<Treinador> treinadores) {
        this.treinadores = treinadores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Escalao> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(List<Escalao> escaloes) {
        this.escaloes = escaloes;
    }
//----------------------------------------ESCALAO---------------------------------
public void addEscalao(Escalao esc){
    if(!escaloes.contains(esc)){
        escaloes.add(esc);
    }
}
    public void removeEscalao(Escalao esc){
        if(escaloes.contains(esc)){
            escaloes.remove(esc);
        }
    }
}
//--------------------------------------SOCIO-----------------------------
public void addSocio(Socio socio){
    if(!escaloes.contains(socio)){
        escaloes.add(socio);
    }
}
    public void removeSocio(Socio socio){
        if(socios.contains(socio)){
            socios.remove(socio);
        }
    }
}