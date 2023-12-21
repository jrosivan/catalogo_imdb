import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CatalogoService {

    public static ArrayList<Filme> catalogoFilmes = new ArrayList<>();
    public static ArrayList<Ator> catalogoAtores = new ArrayList<>();
    public static ArrayList<Diretor> catalogoDiretores = new ArrayList<>();

    public static Ator adicionaCatalogoAtor(Ator ator) {
        if (ator != null && catalogoAtores.indexOf(ator) == -1) {
            catalogoAtores.add(ator);
            return ator;
        }
        return null;
    }

    public static Diretor adicionaCatalogoDiretor(Diretor diretor) {
        if (diretor != null && catalogoDiretores.indexOf(diretor) == -1) {
            catalogoDiretores.add(diretor);
            return diretor;
        }
        return null;
    }

    public static boolean adicionaCatalogoFilme(Filme filme) {
        if (filme != null && catalogoFilmes.indexOf(filme) == -1) {
            return catalogoFilmes.add(filme);
        }
        return false;
    }

    public static boolean removeCatalogoFilme(Filme filme) {
        if (filme != null && catalogoFilmes.indexOf(filme) != -1) {
            return catalogoFilmes.remove(filme);
        }
        return false;
    }

    public static Filme pesquisarFilmePeloNome(String nomeFilme) {
        return catalogoFilmes.stream()
                .filter(u -> u.getNomeFilme().equalsIgnoreCase(nomeFilme))
                .findFirst()
                .orElse(null);
    }

    public static Filme pesquisarFilmePeloNumero(int indexFilme) {
        if (indexFilme > catalogoFilmes.size() || indexFilme < 1)
            return null;
        return catalogoFilmes.get(indexFilme-1);
    }

    public static Ator pesquisarAtor(String pesquisa) {
        if (pesquisa.isEmpty()) return null;
        return (CatalogoService.isNumeric(pesquisa)) ?
                CatalogoService.pesquisarPessoaPeloNumero(catalogoAtores, Integer.parseInt(pesquisa)) :
                CatalogoService.pesquisarPessoaPeloNome(catalogoAtores, pesquisa) ;
    }

    public static Diretor pesquisarDiretor(String pesquisa) {
        if (pesquisa.isEmpty()) return null;
        return (CatalogoService.isNumeric(pesquisa)) ?
                CatalogoService.pesquisarPessoaPeloNumero(catalogoDiretores, Integer.parseInt(pesquisa)) :
                CatalogoService.pesquisarPessoaPeloNome(catalogoDiretores, pesquisa) ;
    }

    private static <T extends Pessoa> T pesquisarPessoaPeloNumero(ArrayList<T> pessoas, int indexPessoa) {
        if (indexPessoa > pessoas.size() || indexPessoa < 1) return null;
        return pessoas.get(indexPessoa-1);
    }
    public static <T extends Pessoa> T pesquisarPessoaPeloNome(ArrayList<T> pessoas, String nomePessoa) {
        return pessoas.stream()
                .filter(u -> u.getNome().equalsIgnoreCase(nomePessoa))
                .findFirst()
                .orElse(null);
    }


    /* ------ */
    public static String listaCatalogoFilmes(Filme filme) {
        String s = "";
        s += "\n■ Nº " + (catalogoFilmes.indexOf(filme)+1) + ": ";
        s += "Filme: " + filme.getNomeFilme() + "  *  [" + filme.getGenero() + "]";
        s += "\n\t· Informações: " + filme.getInformacoes();
        s += "\n\t· Data de Lançamento: " + filme.getDataLancamento() + "  *  Orçamento: " + filme.getOrcamento();
        s += "\n\t· Diretor: ";
        for (Diretor diretor : filme.getDiretor()) {
            s += listaCatalogo(diretor);
        }
        s += "\n\t· Elenco: ";
        for (Ator ator : filme.getElenco()) {
            s += listaCatalogo(ator);
        }
        return s;
    }
    public static String listaCatalogoFilmes() {
        String s = "\n ►►►► CATÁLOGO DE FILMES ◄◄◄◄\n";
        s += "-".repeat(80);
        for (Filme filme : catalogoFilmes) {
            s += listaCatalogoFilmes(filme);
        }
        return s;
    }

    public static String listaCatalogoAtores() {
        return "\n ►►►► CATÁLOGO DE ATORES ◄◄◄◄\n".concat(listaPessoas(catalogoAtores));
    }

    public static String listaCatalogoDiretores() {
        return "\n ►►►► CATÁLOGO DE DIRETORES ◄◄◄◄\n".concat(listaPessoas(catalogoDiretores));
    }

    public static String listaPessoas(ArrayList<?> pessoas){
        String s = "-".repeat(80);
        for (Object pessoa : pessoas) {
            s += "\nNº " + pessoas.indexOf(pessoa) + ": " + listaCatalogo((Pessoa)pessoa);
        }
        return s;
    }

    public static String listaCatalogo(Pessoa pessoa) {
        return pessoa.getNome() + " | " +
                pessoa.getNacionalidade() + " | " +
                pessoa.getDataNascimento() + " | Sexo: " +
                pessoa.getSexo() + "; ";
    }

    public static LocalDate formatarData(String data) {
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static boolean isNumeric(String str){
        return str != null && str.matches("[0-9]+");
     }


    static void preencherCatalogo() {
        //-------------------Filme 01
        Filme filme = new Filme(
                "Oppenheimer",
                "A história do cientista americano J. Robert Oppenheimer e o seu papel no desenvolvimento da bomba atômica.",
                "Biografia; Drama; História",
                "US$ 100 milhões",
                null,
                null,
                LocalDate.of(2023, 7, 17)
        );
        CatalogoService.adicionaCatalogoFilme(filme);

        filme.addDiretor(CatalogoService.adicionaCatalogoDiretor(new Diretor("Christopher Nolan", "Inglaterra", "M", LocalDate.of(1970, 7, 30))));

        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Matt Damon", "EUA", "M", LocalDate.of(1970, 10, 8))));
        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Emily Blunt", "Reino Unido", "F", LocalDate.of(1983, 2, 23))));
        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Cillian Murphy", "Irlanda", "M", LocalDate.of(1976, 5, 25))));

        //-------------------Filme 02
        filme = new Filme(
                "John Wick 4: Baba Yaga",
                "John Wick descobre um modo para derrotar a Alta Cúpula. Mas, antes que possa conquistar a sua liberdade, Wick deve enfrentar um novo inimigo com alianças poderosas no mundo todo e forças que transformam velhos amigos em inimigos.",
                "Ação; Policial; Suspensa",
                "US$ 100 milhões",
                null,
                null,
                LocalDate.of(2023, 3, 13)
        );
        CatalogoService.adicionaCatalogoFilme(filme);

        filme.addDiretor(CatalogoService.adicionaCatalogoDiretor(new Diretor("Chad Stahelski", "EUA", "M", LocalDate.of(1968, 9, 20))));

        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Keanu Reeves", "Libanes", "M", LocalDate.of(1964, 9, 2))));
        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Laurence Fishburne", "EUA", "M", LocalDate.of(1961, 7, 30))));
        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("George Georgiou", "Reino Unido", "M", LocalDate.of(1972, 8, 19))));

        //-------------------Filme 03
        filme = new Filme(
                "Som da Liberdade",
                "Um ex-agente especial do Governo Americano embarca em uma missão arriscada para resgatar crianças vítimas de tráfico infantil na Colômbia.",
                "Biografia; Policial; Drama",
                "US$ 14,5 milhões",
                null,
                null,
                LocalDate.of(2023, 6, 22)
        );
        CatalogoService.adicionaCatalogoFilme(filme);

        filme.addDiretor(CatalogoService.adicionaCatalogoDiretor(new Diretor("Alejandro Monteverde", "México", "M", LocalDate.of(1977, 7, 13))));

        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Jim Caviezel", "EUA", "M", LocalDate.of(1968, 9, 26))));
        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Mira Sorvino", "EUA", "F", LocalDate.of(1967, 9, 28))));
        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Bill Camp", "Reino Unido", "M", LocalDate.of(1964, 10, 2))));

        //-------------------Filme 04
        filme = new Filme(
                "Assassinos da Lua das Flores",
                "Na virada do século XX, o petróleo tornou a nação Osage a mais rica do mundo do dia para a noite. Tanta riqueza atraiu intrusos brancos, que manipularam, extorquiram e roubaram o dinheiro do povo Osage antes de assassinar a população.",
                "Policial; Drama; História",
                "US$ 200 milhões",
                null,
                null,
                LocalDate.of(2023, 5, 20)
        );
        CatalogoService.adicionaCatalogoFilme(filme);

        filme.addDiretor(CatalogoService.adicionaCatalogoDiretor(new Diretor("Martin Scorsese", "EUA", "M", LocalDate.of(1942, 11, 17))));

        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Leonardo DiCaprio", "EUA", "M", LocalDate.of(1974, 11, 11))));
        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Robert De Niro", "EUA", "M", LocalDate.of(1943, 8, 17))));
        filme.addElenco(CatalogoService.adicionaCatalogoAtor(new Ator("Lily Gladstone", "EUA", "F", LocalDate.of(1986, 8, 2))));

        //-------------------Filme 05
        filme = new Filme(
                "Duna: Parte Dois",
                "Diante da difícil escolha entre o amor de sua vida e o destino do universo conhecido, Paul Atreides, agora ao lado de Chani e dos Fremen, dará tudo de si para evitar o futuro terrível que só ele pode prever.",
                "Ação; Aventura; Drama",
                "US$ 165 milhões",
                null,
                null,
                LocalDate.of(2024, 2, 28)
        );
        CatalogoService.adicionaCatalogoFilme(filme);

        filme.addDiretor(CatalogoService.adicionaCatalogoDiretor(new Diretor("Denis Villeneuve", "Canadá", "M", LocalDate.of(1967, 10, 3))));

        filme.addElenco(new Ator("Florence Pugh", "Reino Unido", "F", LocalDate.of(1996, 1, 3)));
        filme.addElenco(new Ator("Rebecca Ferguson", "Suécia", "F", LocalDate.of(1983, 10, 19)));
        filme.addElenco(new Ator("Timothée Chalamet", "EUA", "M", LocalDate.of(1995, 12, 27)));

    }


}
