import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    private static final String INVALID_OPTION_MSG = "Opção inválida. Tente novamente!";
    public static String[] menuFilmes = {
            "1 - Cadastrar Filmes",
            "2 - Cadastrar Atores",
            "3 - Cadastrar Diretores",
            "4 - Relacionar Ator ao Filme",
            "5 - Relacionar Diretor ao Filme",
            "6 - Listar Catálogo de Filmes",
            "7 - Listar Catálogo de Atores",
            "8 - Listar Catálogo de Diretores",
            "9 - Pesquisar filme pelo nome/número",
            "0 - Excluir Filme do Catálogo",
            "X - SAIR"
    };

    public static void menu() {
        while (true) {
            montarTela(ConsoleColors.BLACK_BACKGROUND_BRIGHT + "MENU PRINCIPAL " + " ".repeat(65) + ConsoleColors.RESET);
            mostrarOpcoes(menuFilmes);
            String opcao = getOptionMenu(menuFilmes);
            if ("SAIR".contains(opcao)) break;
            executar(opcao);
        }
        System.out.println("-".repeat(78));
        System.out.println("Até a próxima!");
    }

    private static void montarTela(String mensagem) {
        System.out.println(ConsoleColors.YELLOW_BOLD + "┌" + "─".repeat(78) + "┐" + ConsoleColors.RESET );
        System.out.println(ConsoleColors.YELLOW_BOLD + "            ╔═╗╔═╗╔╦╗╔═╗╦  ╔═╗╔═╗╔═╗  ╔╦╗╔═╗  ╔═╗╦ ╦  ╔╦╗╔═╗╔═╗" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD + "            ║  ╠═╣ ║ ╠═╣║  ║ ║║ ╦║ ║   ║║║╣   ╠╣ ║ ║  ║║║║╣ ╚═╗" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD + "            ╚═╝╩ ╩ ╩ ╩ ╩╩═╝╚═╝╚═╝╚═╝  ═╩╝╚═╝  ╚  ╩ ╩═╝╩ ╩╚═╝╚═╝" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD + "└" + "─".repeat(78) + "┘" + ConsoleColors.RESET);
        System.out.printf("%s\n", mensagem);
    }

    public static void mostrarOpcoes(String[] opcoes) {
        for (String s : opcoes) {
            System.out.println(s);
        }
    }

    private static String getOptionMenu(String[] opcoes) {
        do {
            String opcao = getUserInput(ConsoleColors.BLUE_BOLD_BRIGHT + ">> Entre com uma opção" + ConsoleColors.RESET);
            String selecao = selecionado(opcao, opcoes);
            if (!selecao.isEmpty()) return selecao;
            System.out.printf(ConsoleColors.RED_BOLD + "[%s] " + ConsoleColors.RESET, INVALID_OPTION_MSG);
        } while (true);
    }

    public static String getUserInput(String question) {
        System.out.printf(question.concat(" ☻ : "));
        return new Scanner(System.in).nextLine().trim().toUpperCase();
    }

    private static String selecionado(String opcao, String[] opcoes) {
        return Arrays.stream(opcoes)
                .filter(s -> s.startsWith(opcao))
                .findFirst()
                .map(s -> s.replace(opcao.concat(" - "), "").trim())
                .orElse("");
    }

    private static void executar(String opcao) {
        switch (opcao) {
            case "Cadastrar Filmes" -> {
                cadastrarFilmes();
            }
            case "Cadastrar Atores" -> {
                cadastrarAtores();  // ## TO-DO: refatorar!
            }
            case "Cadastrar Diretores" -> {
                cadastrarDiretores();
            }
            case "Relacionar Ator ao Filme" -> {
                relacionaPessoa(CatalogoService.pesquisarAtor(getUserInput("Digite o nome ou número do ATOR: ")));
            }
            case "Relacionar Diretor ao Filme" -> {
                relacionaPessoa(CatalogoService.pesquisarDiretor(getUserInput("Digite o nome ou número do DIRETOR: ")));
            }
            case "Listar Catálogo de Filmes" -> {
                System.out.println(CatalogoService.listaCatalogoFilmes());
            }
            case "Listar Catálogo de Atores" -> {
                System.out.println(CatalogoService.listaCatalogoAtores());
            }
            case "Listar Catálogo de Diretores" -> {
                System.out.println(CatalogoService.listaCatalogoDiretores());
            }
            case "Pesquisar filme pelo nome/número" -> {
                listarFilme(pesquisarFilme());
            }
            case "Excluir Filme do Catálogo" -> {
                removerFilme();
            }
        }
        getUserInput(ConsoleColors.YELLOW + "Aperte qualquer tecla para continuar...." + ConsoleColors.RESET);
    }

    private static void listarFilme(Filme filme) {
        System.out.println( (filme == null) ?
            "Filme  não localizado!" :
            CatalogoService.listaCatalogoFilmes(filme) );
    }

    private static  <T extends Pessoa> void relacionaPessoa(T pessoa) {
        if (pessoa == null) return;
        System.out.printf("\t=> %s\n", pessoa.getNome());

        Filme filme = pesquisarFilme();
        if (filme == null) return;

        if (pessoa instanceof Diretor) {
            filme.getDiretor().add((Diretor) pessoa);
        } else if (pessoa instanceof Ator) {
            filme.getElenco().add((Ator) pessoa);
        }
    }

    // ## TO-DO: Há filmes que o nome é um número-> 1945
    private static Filme pesquisarFilme() {
        String resposta = getUserInput("Digite o nome ou número do filme: ");
        if (resposta.isEmpty()) return null;
        return (CatalogoService.isNumeric(resposta)) ?
                CatalogoService.pesquisarFilmePeloNumero(Integer.parseInt(resposta)) :
                CatalogoService.pesquisarFilmePeloNome(resposta);
    }

    private static void removerFilme() {
        if (CatalogoService.removeCatalogoFilme(pesquisarFilme())){
            System.out.println("### FILME REMOVIDO!!!");
        }
    }

    private static void cadastrarFilmes() {
        String nomeFilme = getUserInput("Digite o nome do filme: ");
        if (nomeFilme.isEmpty()) return;
        String informacao = getUserInput("Digite um breve resumo do filme: ");
        String genero = getUserInput("Informe o genero (separar por ; ex: Ação; Aventura; Drama): ");
        String orcamento = getUserInput("Digite o orçamento do filme (exp: US$ 10 milhões): ");
        String dataLancamento = getUserInput("Qual o data de lançamento do filme? ");

        Filme filme = new Filme(
                        nomeFilme,
                        informacao,
                        genero,
                        orcamento,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        CatalogoService.formatarData(dataLancamento)
                );

        CatalogoService.adicionaCatalogoFilme(filme);

        String resposta = getUserInput("Deseja adicionar o ELENCO do filme:  " + nomeFilme + "? ");
        if (resposta.equals("SIM") || resposta.equals("S")) {
            cadastrarAtores(filme);
        }

        resposta = getUserInput("Deseja adicionar o DIREÇÃO do filme:  " + nomeFilme + "? ");
        if (resposta.equals("SIM") || resposta.equals("S")) {
            cadastrarDiretores(filme);
        }

        System.out.println(CatalogoService.listaCatalogoFilmes(filme));

    }

    public static void cadastrarAtores() {
        cadastrarAtores(null);
    }

    public static void cadastrarAtores(Filme filme) {
        while (true) {
            String dadosAtor = getUserInput("Informe Nome/Nacionalidade/Sexo/Nascimento do ATOR (separar por ; ex: Baby Sauro; EUA; M; 08/06/1963), " +
                    '\n' + "ou [ENTER] para sair");
            if (dadosAtor.isEmpty()) return;

            try {
                String[] aAtor = dadosAtor.split("\\s*;\\s*");
                Ator ator = new Ator(aAtor[0], aAtor[1], aAtor[2], CatalogoService.formatarData(aAtor[3]));
                CatalogoService.adicionaCatalogoAtor(ator);
                if (filme != null) {
                    filme.addElenco(ator);
                }
            } catch (Exception e) {
                System.out.println(" ◘‼◘ Dados inválidos para o ATOR. Detalhes: " + e.getMessage());
            }

        }
    }

    public static void cadastrarDiretores() {
        cadastrarDiretores(null);
    }

    public static void cadastrarDiretores(Filme filme) {
        while (true) {
            String dadosDiretor = getUserInput("Informe Nome/Nacionalidade/Sexo/Nascimento do DIRETOR (separar por ; ex: Baby Sauro; EUA; M; 08/06/1963), " +
                    '\n' + "ou [ENTER] para sair");
            if (dadosDiretor.isEmpty()) return;

            try {
                String[] aDiretor = dadosDiretor.split("\\s*;\\s*");
                Diretor diretor = new Diretor(aDiretor[0], aDiretor[1], aDiretor[2], CatalogoService.formatarData(aDiretor[3]));
                CatalogoService.adicionaCatalogoDiretor(diretor);
                if (filme != null){
                    filme.addDiretor(diretor);
                }
            } catch (Exception e) {
                System.out.println(" ◘‼◘ Dados inválidos para o DIRETOR. Detalhes: " + e.getMessage());
            }
        }
    }


}
