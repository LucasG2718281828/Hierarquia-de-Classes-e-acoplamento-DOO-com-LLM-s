**Java – Hierarquia de Usuário do Sistema com dependências de segurança**

```java
// ---------------------------------------------------------------------------
// 1.  Dependências (stubs – apenas a estrutura necessária para compilar)
// ---------------------------------------------------------------------------

package com.example.segseg;

public class AutenticadorMfa {
    public boolean autenticar(String usuario, String senha) {
        // Lógica real de MFA seria aqui
        return true;
    }
}

public class RepositorioLdap {
    public void sincronizar() {
        // Lógica de sincronização LDAP
    }
}

public class PoliticaAcesso {
    public boolean validarAcesso(String usuario, String recurso) {
        // Lógica de verificação de política
        return true;
    }
}

public class LoggerAuditoria {
    public void log(String mensagem) {
        // Persistência de logs SIEM
    }
}

public class GerenciadorSessao {
    public void criarSessao(String usuario) {
        // Criação de sessão
    }
}

public class TokenJwt {
    public String gerarToken(String usuario) {
        // Geração de token JWT
        return "jwt-token";
    }
}

public class NotificadorAlerta {
    public void enviarAlerta(String mensagem) {
        // Envio de alerta (e‑mail, SMS, etc.)
    }
}

// ---------------------------------------------------------------------------
// 2.  Classe Pai – UsuarioSistema
// ---------------------------------------------------------------------------

package com.example.segseg;

public class UsuarioSistema {

    /* -----------------------------------------------------------------------
     *  Atributos de dependência (injetados via construtor)
     * ----------------------------------------------------------------------- */
    protected final AutenticadorMfa autenticadorMfa;
    protected final RepositorioLdap repositorioLdap;
    protected final PoliticaAcesso politicaAcesso;
    protected final LoggerAuditoria loggerAuditoria;
    protected final GerenciadorSessao gerenciadorSessao;
    protected final TokenJwt tokenJwt;
    protected final NotificadorAlerta notificadorAlerta;

    /* -----------------------------------------------------------------------
     *  Construtor
     * ----------------------------------------------------------------------- */
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

    /* -----------------------------------------------------------------------
     *  Métodos de negócio
     * ----------------------------------------------------------------------- */

    /**
     * Autentica o usuário utilizando MFA.
     *
     * @param usuario Nome do usuário
     * @param senha   Senha do usuário
     * @return true se a autenticação for bem‑sucedida, false caso contrário
     */
    public boolean autenticar(String usuario, String senha) {
        boolean autenticado = autenticadorMfa.autenticar(usuario, senha);
        if (autenticado) {
            gerenciadorSessao.criarSessao(usuario);
            String token = tokenJwt.gerarToken(usuario);
            loggerAuditoria.log("Usuário " + usuario + " autenticado. Token: " + token);
        } else {
            loggerAuditoria.log("Falha na autenticação do usuário " + usuario);
            notificadorAlerta.enviarAlerta("Falha de login para usuário: " + usuario);
        }
        return autenticado;
    }

    /**
     * Audita um evento no SIEM.
     *
     * @param evento Mensagem de evento a ser registrado
     */
    public void auditarLogsSIEM(String evento) {
        loggerAuditoria.log("SIEM: " + evento);
    }

    /**
     * Sincroniza o diretório LDAP com o repositório interno.
     */
    public void sincronizarDiretorioLDAP() {
        repositorioLdap.sincronizar();
        loggerAuditoria.log("Sincronização LDAP concluída.");
    }

    /* -----------------------------------------------------------------------
     *  Métodos auxiliares (poderiam ser protegidos ou privados)
     * ----------------------------------------------------------------------- */

    protected boolean validarAcesso(String usuario, String recurso) {
        return politicaAcesso.validarAcesso(usuario, recurso);
    }
}

// ---------------------------------------------------------------------------
// 3.  Classe Filha – AdministradorSeguranca
// ---------------------------------------------------------------------------

package com.example.segseg;

public class AdministradorSeguranca extends UsuarioSistema {

    public AdministradorSeguranca(AutenticadorMfa autenticadorMfa,
                                  RepositorioLdap repositorioLdap,
                                  PoliticaAcesso politicaAcesso,
                                  LoggerAuditoria loggerAuditoria,
                                  GerenciadorSessao gerenciadorSessao,
                                  TokenJwt tokenJwt,
                                  NotificadorAlerta notificadorAlerta) {
        super(autenticadorMfa, repositorioLdap, politicaAcesso,
              loggerAuditoria, gerenciadorSessao, tokenJwt, notificadorAlerta);
    }

    /**
     * Gerencia políticas de acesso (ex.: criar, atualizar, excluir).
     *
     * @param usuario  Usuário que está gerenciando
     * @param recurso  Recurso alvo da política
     * @param acao     Ação a ser realizada (CREATE, UPDATE, DELETE)
     */
    public void gerenciarPoliticaAcesso(String usuario, String recurso, String acao) {
        // Lógica real de gerenciamento de política
        loggerAuditoria.log("Administrador " + usuario + " realizou ação '" + acao
                + "' sobre recurso '" + recurso + "'.");
    }

    /**
     * Override opcional: o administrador pode ter um fluxo de autenticação mais
     * restrito ou auditoria adicional.
     */
    @Override
    public boolean autenticar(String usuario, String senha) {
        boolean autenticado = super.autenticar(usuario, senha);
        if (autenticado) {
            // Auditoria extra para administradores
            auditarLogsSIEM("Administrador " + usuario + " autenticado.");
        }
        return autenticado;
    }
}

// ---------------------------------------------------------------------------
// 4.  Classe Filha – OperadorSuporte
// ---------------------------------------------------------------------------

package com.example.segseg;

public class OperadorSuporte extends UsuarioSistema {

    public OperadorSuporte(AutenticadorMfa autenticadorMfa,
                           RepositorioLdap repositorioLdap,
                           PoliticaAcesso politicaAcesso,
                           LoggerAuditoria loggerAuditoria,
                           GerenciadorSessao gerenciadorSessao,
                           TokenJwt tokenJwt,
                           NotificadorAlerta notificadorAlerta) {
        super(autenticadorMfa, repositorioLdap, politicaAcesso,
              loggerAuditoria, gerenciadorSessao, tokenJwt, notificadorAlerta);
    }

    /**
     * Resolve um incidente de