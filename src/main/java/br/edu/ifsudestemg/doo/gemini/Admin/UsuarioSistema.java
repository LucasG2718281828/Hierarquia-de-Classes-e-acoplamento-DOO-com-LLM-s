package br.edu.ifsudestemg.doo.gemini.Admin;

public abstract class UsuarioSistema {
    protected String id;
    protected String nome;
    protected AutenticadorMfa autenticadorMfa;
    protected RepositorioLdap repositorioLdap;
    protected PoliticaAcesso politicaAcesso;
    protected LoggerAuditoria loggerAuditoria;
    protected GerenciadorSessao gerenciadorSessao;
    protected TokenJwt tokenJwt;
    protected NotificadorAlerta notificadorAlerta;

    public UsuarioSistema(String id, String nome,
                          AutenticadorMfa autenticadorMfa,
                          RepositorioLdap repositorioLdap,
                          PoliticaAcesso politicaAcesso,
                          LoggerAuditoria loggerAuditoria,
                          GerenciadorSessao gerenciadorSessao,
                          TokenJwt tokenJwt,
                          NotificadorAlerta notificadorAlerta) {
        this.id = id;
        this.nome = nome;
        this.autenticadorMfa = autenticadorMfa;
        this.repositorioLdap = repositorioLdap;
        this.politicaAcesso = politicaAcesso;
        this.loggerAuditoria = loggerAuditoria;
        this.gerenciadorSessao = gerenciadorSessao;
        this.tokenJwt = tokenJwt;
        this.notificadorAlerta = notificadorAlerta;
    }

    // Autenticação com MFA
    public boolean autenticar(String usuario, String senha) {
        boolean credenciaisValidas = politicaAcesso.validarCredenciais(usuario, senha);
        if (!credenciaisValidas) {
            loggerAuditoria.registrarFalhaAutenticacao(usuario);
            return false;
        }
        boolean mfaOk = autenticadorMfa.executarMfa(usuario);
        if (mfaOk) {
            String token = tokenJwt.gerarToken(usuario);
            gerenciadorSessao.iniciarSessao(usuario, token);
            loggerAuditoria.registrarSucessoAutenticacao(usuario);
            return true;
        } else {
            loggerAuditoria.registrarFalhaMfa(usuario);
            notificadorAlerta.enviarAlertaFalhaMfa(usuario);
            return false;
        }
    }

    // Auditoria de ações no SIEM
    public void auditar(String acao) {
        loggerAuditoria.registrarAcao(this.id, acao);
    }

    // Sincronização com diretório LDAP
    public void sincronizarDiretorio() {
        repositorioLdap.sincronizarUsuario(this.id, this.nome);
    }

    // Getters e Setters básicos
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}