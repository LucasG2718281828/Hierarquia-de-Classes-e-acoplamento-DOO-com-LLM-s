**Java – Hierarquia de Usuário do Sistema com dependências de segurança**

```java
// ---------------------------------------------------------------------------
// 1.  Dependências (stubs – apenas a estrutura necessária para compilar)
// ---------------------------------------------------------------------------

package com.example.segseg.deps;

/**
 * Autenticação Multi‑Fator (MFA)
 */
public class AutenticadorMfa {

    public boolean autenticar(String usuario, String senha) {
        // Lógica de MFA (placeholder)
        return true;
    }
}

/**
 * Repositório LDAP
 */
public class RepositorioLdap {

    public void sincronizar() {
        // Sincronização LDAP (placeholder)
    }
}

/**
 * Política de Acesso
 */
public class PoliticaAcesso {

    public boolean validarAcesso(String usuario, String recurso) {
        // Validação de política (placeholder)
        return true;
    }
}

/**
 * Logger de Auditoria (SIEM)
 */
public class LoggerAuditoria {

    public void log(String mensagem) {
        // Envio de log para SIEM (placeholder)
    }
}

/**
 * Gerenciador de Sessões
 */
public class GerenciadorSessao {

    public void criarSessao(String usuario) {
        // Criação de sessão (placeholder)
    }
}

/**
 * Token JWT
 */
public class TokenJwt {

    public String gerarToken(String usuario) {
        // Geração de token (placeholder)
        return "jwt-token";
    }
}

/**
 * Notificador de Alertas
 */
public class NotificadorAlerta {

    public void enviarAlerta(String mensagem) {
        // Envio de alerta (placeholder)
    }
}
```

```java
// ---------------------------------------------------------------------------
// 2.  Classe Pai – UsuarioSistema
// ---------------------------------------------------------------------------

package com.example.segseg;

import com.example.segseg.deps.*;

public class UsuarioSistema {

    /* --------------------------------------------------------------------
     *  Atributos de dependência (injetados via construtor)
     * -------------------------------------------------------------------- */
    protected final AutenticadorMfa autenticadorMfa;
    protected final RepositorioLdap repositorioLdap;
    protected final PoliticaAcesso politicaAcesso;
    protected final LoggerAuditoria loggerAuditoria;
    protected final GerenciadorSessao gerenciadorSessao;
    protected final TokenJwt tokenJwt;
    protected final NotificadorAlerta notificadorAlerta;

    /* --------------------------------------------------------------------
     *  Construtor
     * -------------------------------------------------------------------- */
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

    /* --------------------------------------------------------------------
     *  Métodos de negócio
     * -------------------------------------------------------------------- */

    /**
     * Autentica o usuário utilizando MFA.
     *
     * @param usuario Nome do usuário
     * @param senha   Senha do usuário
     * @return true se a autenticação for bem‑sucedida
     */
    public boolean autenticar(String usuario, String senha) {
        boolean autenticado = autenticadorMfa.autenticar(usuario, senha);
        if (autenticado) {
            gerenciadorSessao.criarSessao(usuario);
            String token = tokenJwt.gerarToken(usuario);
            loggerAuditoria.log("Usuário " + usuario + " autenticado. Token: " + token);
        } else {
            loggerAuditoria.log("Falha na autenticação do usuário " + usuario);
            notificadorAlerta.enviarAlerta("Falha de autenticação para " + usuario);
        }
        return autenticado;
    }

    /**
     * Audita um evento no SIEM.
     *
     * @param evento Mensagem de evento
     */
    public void auditarLogsSIEM(String evento) {
        loggerAuditoria.log("SIEM: " + evento);
    }

    /**
     * Sincroniza o diretório LDAP com o repositório interno.
     */
    public void sincronizarDiretorioLDAP() {
        repositorioLdap.sincronizar();
        loggerAuditoria.log("Diretório LDAP sincronizado.");
    }

    /* --------------------------------------------------------------------
     *  Métodos auxiliares (poderiam ser protegidos ou privados)
     * -------------------------------------------------------------------- */

    protected boolean validarAcesso(String usuario, String recurso) {
        return politicaAcesso.validarAcesso(usuario, recurso);
    }
}
```

```java
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
     * Gerencia políticas de acesso (exemplo de método específico).
     */
    public void gerenciarPoliticaAcesso(String usuario, String recurso, boolean permitir) {
        // Lógica fictícia de alteração de política
        boolean resultado = validarAcesso(usuario, recurso);
        loggerAuditoria.log("Administrador alterou política para " + usuario + " em " + recurso + ": " + (permitir ? "permitido" : "negado"));
        if (!permitir) {
            notificadorAlerta.enviarAlerta("Política de acesso negada para " + usuario + " em " + recurso);
        }
    }

    /**
     * Override da autenticação para adicionar auditoria extra.
     */
    @Override
    public boolean autenticar(String usuario, String senha) {
        boolean autenticado = super.autenticar(usuario, senha);
        if (autenticado) {
            auditarLogsSIEM("Administrador " + usuario + " autenticado com sucesso.");
        } else {
            auditarLogsSIEM("Falha de autenticação para Administrador " + usuario);
        }
        return autenticado;
    }
}
```

```java
// ---------------------------------------------------------------------------
// 4.  Classe Filha – OperadorSuporte
// ---------------------------------------------------------------------------

package com.example.segseg;

public class OperadorSuporte extends UsuarioSistema {

    public OperadorSuporte(AutenticadorMfa autenticadorMfa,
                           RepositorioLdap repositorioLdap,
                           Politica