package br.edu.ifsudestemg.doo.groq.Admin;

public class UsuarioSistema {
    protected AutenticadorMfa autenticadorMfa;
    protected RepositorioLdap repositorioLdap;
    protected PoliticaAcesso politicaAcesso;
    protected LoggerAuditoria loggerAuditoria;
    protected GerenciadorSessao gerenciadorSessao;
    protected TokenJwt tokenJwt;
    protected NotificadorAlerta notificadorAlerta;

    public UsuarioSistema(AutenticadorMfa autenticadorMfa,
                          RepositorioLdap repositorioLdap,
                          PoliticaAcesso politicaAcesso,
                          LoggerAuditoria loggerAuditoria,
                          GerenciadorSessao gerenciadorSessao,
                          TokenJwt tokenJwt,
                          NotificadorAlerta notificadorAlerta) {
        this.autenticadorMfa = autenticadorMfa;
        this.repositorioLdap = repositorioLdap;
        this.politicaAcesso = politicaAcesso;
        this.loggerAuditoria = loggerAuditoria;
        this.gerenciadorSessao = gerenciadorSessao;
        this.tokenJwt = tokenJwt;
        this.notificadorAlerta = notificadorAlerta;
    }

    public boolean autenticarComMfa(String usuario, String senha, String codigoMfa) {
        boolean autenticado = autenticadorMfa.autenticar(usuario, senha, codigoMfa);
        if (autenticado) {
            String token = tokenJwt.gerarToken(usuario);
            gerenciadorSessao.iniciarSessao(usuario, token);
            loggerAuditoria.registrarEvento(usuario, "Autenticação MFA bem-sucedida");
        } else {
            loggerAuditoria.registrarEvento(usuario, "Falha na autenticação MFA");
            notificadorAlerta.enviarAlerta(usuario, "Falha de autenticação MFA");
        }
        return autenticado;
    }

    public void auditarLogsSiem(String evento) {
        loggerAuditoria.registrarEvento(evento);
    }

    public void sincronizarDiretorioLdap() {
        repositorioLdap.sincLdap();
        loggerAuditoria.registrarEvento("Sincronização LDAP concluída");
    }
}