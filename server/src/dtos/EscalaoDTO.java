package dtos;

public class EscalaoDTO {
    private String code;
    private String name;
    private String modalidade;

    public EscalaoDTO(String code, String name, String modalidade) {
        this.code = code;
        this.name = name;
        this.modalidade = modalidade;
    }
    public EscalaoDTO(){
        this("","","");
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }
}
