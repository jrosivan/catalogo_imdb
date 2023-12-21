import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Pessoa {
    String nome;
    String nacionalidade;
    String sexo;
    LocalDate dataNascimento;

    public Pessoa(String nome, String nacionalidade, String sexo, LocalDate dataNascimento) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }
    public String getNome() {
        return nome;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }
    public String getSexo() {
        return sexo;
    }
    public String getDataNascimento() {
        return dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
