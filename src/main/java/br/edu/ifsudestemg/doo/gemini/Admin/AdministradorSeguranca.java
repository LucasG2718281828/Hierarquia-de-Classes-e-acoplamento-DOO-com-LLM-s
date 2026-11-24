package br.edu.ifsudestemg.doo.gemini.Admin;

public class AdministradorSeguranca extends UsuarioSistema {

    public AdministradorSeguranca(String id, String nome,
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

    // Operação exclusiva do administrador: definir política de acesso
    public void definirPoliticaAcesso(String regra) {
        politicaAcesso.adicionarRegra(regra);
        auditar("Definiu política de acesso: " + regra);
    }

    // Operação exclusiva do administrador: gerar relatório de auditoria
    public void gerarRelatorioAuditoria() {
        loggerAuditoria.gerarRelatorio();
        auditar("Gerou relatório de auditoria");
    }
}