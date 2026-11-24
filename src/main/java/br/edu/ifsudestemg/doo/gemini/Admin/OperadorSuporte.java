package br.edu.ifsudestemg.doo.gemini.Admin;

public class OperadorSuporte extends UsuarioSistema {

    public OperadorSuporte(String id, String nome,
                           AutenticadorMfa autenticadorMfa,
                           RepositorioLdap repositorioLdap,
                           PoliticaAcesso politicaAcesso,
                           LoggerAuditoria loggerAuditoria,
                           GerenciadorSessao gerenciadorSessao,
                           TokenJwt tokenJwt,
                           NotificadorAlerta notificadorAlerta) {
        super(id, nome, autenticadorMfa, repositorioLdap, politicaAcesso,
              loggerAuditoria, gerenciadorSessao, tokenJwt, notificadorAlerta);
    }

    // Operação exclusiva do operador: resetar senha de usuário
    public void resetarSenhaUsuario(String usuarioAlvo) {
        politicaAcesso.resetarSenha(usuarioAlvo);
        auditar("Resetou senha do usuário: " + usuarioAlvo);
        notificadorAlerta.enviarNotificacao(usuarioAlvo, "Sua senha foi resetada pelo operador de suporte.");
    }

    // Operação exclusiva do operador: consultar logs recentes
    public void consultarLogsRecentes() {
        loggerAuditoria.exibirLogsRecentes();
        auditar("Consultou logs recentes");
    }
}