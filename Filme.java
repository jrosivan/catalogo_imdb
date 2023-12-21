import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Filme {
    private String nomeFilme;
    private String informacoes;
    private String genero;
    private String orcamento;
    private List<Diretor> direcao;
    private List<Ator> elenco;
    private LocalDate dataLancamento;

    public String getNomeFilme() {
        return nomeFilme;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public String getGenero() {
        return genero;
    }

    public String getOrcamento() { return orcamento; }

    public List<Diretor> getDiretor() {
        return direcao;
    }

    public List<Ator> getElenco() {
        return elenco;
    }

    public String getDataLancamento() {
        return dataLancamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Filme(String nomeFilme,
                 String informacoes,
                 String genero,
                 String orcamento,
                 List<Diretor> direcao,
                 List<Ator> elenco,
                 LocalDate dataLancamento) {
        this.nomeFilme = nomeFilme;
        this.informacoes = informacoes;
        this.genero = genero;
        this.orcamento = orcamento;
        this.direcao = direcao;
        this.elenco = elenco;
        this.dataLancamento = dataLancamento;
    }

    public void addElenco(Ator ator) {
        if (elenco == null) {
            elenco = new ArrayList<>();
        }
        elenco.add(ator);
    }

    public void addDiretor(Diretor diretor) {
        if (direcao == null) {
            direcao = new ArrayList<>();
        }
        direcao.add(diretor);
    }

}
