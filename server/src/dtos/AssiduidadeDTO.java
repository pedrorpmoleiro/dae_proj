package dtos;

public class AssiduidadeDTO {
    private String username;
    private String assistude;

    public AssiduidadeDTO(String username, Boolean assistude) {
        this.username = username;
        if(assistude){
            this.assistude="COMPARECEU";
        }else{
            this.assistude="FALTOU";
        }
    }
    public AssiduidadeDTO(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAssistude() {
        return assistude;
    }

    public void setAssistude(String assistude) {
        this.assistude = assistude;
    }
}
