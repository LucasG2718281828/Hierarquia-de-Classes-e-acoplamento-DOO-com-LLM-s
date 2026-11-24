import os
import time
import re
from dotenv import load_dotenv
from openai import OpenAI
from google import genai

# 1. Carrega as variáveis de ambiente do arquivo .env
load_dotenv()

# Verifica se as chaves estão configuradas
GEMINI_KEY = os.getenv("GEMINI_API_KEY")
GROQ_KEY = os.getenv("GROQ_API_KEY")
MISTRAL_KEY = os.getenv("MISTRAL_API_KEY")

# Cliente Groq (compatível com OpenAI)
client_groq = OpenAI(
    api_key=GROQ_KEY,
    base_url="https://api.groq.com/openai/v1"
)

# Cliente Mistral (compatível com OpenAI)
client_mistral = OpenAI(
    api_key=MISTRAL_KEY,
    base_url="https://api.mistral.ai/v1"
)

# Cliente Gemini (mantém como estava)
client_gemini = genai.Client(api_key=GEMINI_KEY)

# ==============================================================================
# ⚠️ IMPORTANTE: COLE AQUI AS SUAS 15 AtvS DE HERANÇA
# Substitua os exemplos abaixo pelos seus prompts reais.
# ==============================================================================
Atvs = [
    {
        "id": "Atv01",
        "dominio": "animal", # <--- ADICIONE ESTA LINHA
        "prompt": "Implemente em Java a classe 'Cachorro' e a classe 'Gato' que herda de 'Animal'. A classe Animal possui o método 'emitirSom()' e 'movimentar()'. deve-se ter classes acopladas/dependencias como 'Som' e 'Alimento'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv02",
        "dominio": "veiculo", # <--- ADICIONE ESTA LINHA
        "prompt": "Implemente em Java as classes 'Carro' e 'Moto' que herda de 'Veiculo'. A classe Veiculo possui distribuicoes de informacoes sobre Calcular consumo de combustível e aceleração herdando atributos de chassi, deve-se ter acopladas/dependencias de 'Motor', 'Combustivel', 'Placa'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv03",
        "dominio": "FormaGeometrica", # <--- ADICIONE ESTA LINHA
        "prompt": "Implemente em Java as classes 'Retangulo' e 'Circulo' que herda de 'FormaGeometrica'. A classe FormaGeometrica possui os metodos abstratos 'calcularArea()' e 'desenhar()'. deve-se ter acopladas/dependencias 'Ponto2D' e 'Cor'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv04",
        "dominio": "contabancaria",
        "prompt": "Implemente em Java as classes 'ContaCorrente' e 'ContaPoupanca' que herda de 'ContaBancaria'. A classe ContaBancaria possui metodos sobre Aplicar tarifas bancárias específicas sobrescrevendo método sacar(). deve-se ter acopladas/dependencias 'Titular', 'Extrato' e 'Moeda'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv05",
        "dominio": "dispostitivosEletronicos",
        "prompt": "Implemente em Java as classes 'Smartphone' e 'Tablet' que herda de 'DispositivoEletronico'. A classe DispositivoEletronico possui metodos sobre Gerenciar ciclo de bateria e especificações de hardware portátil. deve-se ter acopladas/dependencias 'Bateria' e 'Fabricante'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv06",
        "dominio": "Funcionario",
        "prompt": "Implemente em Java as classes 'Gerente' e 'Diretor' que herda de 'Funcionario'. A classe Funcionario possui metodos sobre Calcular bônus salarial e validar equipe subordinada do gestor. deve-se ter acopladas/dependencias Departamento, FolhaPagamento, BonusPolicy, Equipe, Auditoria. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv07",
        "dominio": "Jogo",
        "prompt": "Implemente em Java as classes 'Guerreiro' e 'Mago' que herda de 'PersonagemJogo'. A classe PersonagemJogo possui metodos sobre Processar sistema de combate e cálculo de dano com equipamentos. deve-se ter acopladas/dependencias Arma, Armadura, Inventario, EfeitoStatus. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv08",
        "dominio": "Pagamento",
        "prompt": "Implemente em Java as classes CartaoCredito, Pix que herda de 'MeioDePagamento'. A classe MeioDePagamento possui metodos sobre Efetuar pagamento com verificação antifraude e emissão de recibo. deve-se ter acopladas/dependencias GatewayPagamento, ValidadorFraude, Recibo, Notificacao, Moeda. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv09",
        "dominio": "Fiscal",
        "prompt": "Implemente em Java as classes NotaFiscalEletronica, CupomFiscal que herda de 'DocumentoFiscal'. A classe DocumentoFiscal possui metodos sobre Validar, assinar digitalmente e calcular alíquotas de impostos fiscais. deve-se ter acopladas/dependencias Emitente, Destinatario, ItemTributado, CertificadoDigital, RepositorioXml, ImpostoCalculador. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv10",
        "dominio": "Iot",
        "prompt": "Implemente em Java as classes SensorTemperatura, SensorPressao que herda de 'SensorIoT'. A classe SensorIoT possui metodos sobre Calibrar e transmitir leituras ambientais via protocolo MQTT. deve-se ter acopladas/dependencias ConexaoMqtt, Calibrador, LogManager, PayloadTelemetria. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv11",
        "dominio": "Entrega",
        "prompt": "Implemente em Java as classes EntregaExpressa, EntregaInternacional que herda de 'ServicoEntrega'. A classe ServicoEntrega possui metodos sobre Orquestrar despacho expresso com roteirização GPS e seguro de carga. deve-se ter acopladas/dependencias RoteirizadorGps, Rastreador, SeguroCarga, Transportadora, TabelaFrete, NotificadorSms, Aduana, CalculadorPrazo. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv12",
        "dominio": "pedido",
        "prompt": "Implemente em Java as classes PedidoECommerce, PedidoB2B que herda de 'ProcessadorPedido'. A classe ProcessadorPedido possui metodos sobre Executar fluxo de checkout com reserva de estoque, antifraude e CRM. deve-se ter acopladas/dependencias CatalogoService, EstoqueService, PagamentoGateway, AntiFraudeService, CrmService, FreteCalculator, EmailDispatcher, NotaFiscalService, AnaliseCredito. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv13",
        "dominio": "Admin",
        "prompt": "Implemente em Java as classes AdministradorSeguranca, OperadorSuporte que herda de 'UsuarioSistema'. A classe UsuarioSistema possui metodos sobre Autenticar com MFA, auditar logs SIEM e sincronizar diretório LDAP. deve-se ter acopladas/dependencias AutenticadorMfa, RepositorioLdap, PoliticaAcesso, LoggerAuditoria, GerenciadorSessao, TokenJwt, NotificadorAlerta. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv14",
        "dominio": "Investimento",
        "prompt": "Implemente em Java as classes SimuladorInvestimentoAvancado, SimuladorPrevidencia que herda de 'SimuladorFinanceiro'. A classe SimuladorFinanceiro possui metodos sobre Simular projeção estocástica com curva de juros, inflação e câmbio. deve-se ter acopladas/dependencias IndiceBacen, CurvaJuros, TabelaRegressivaIr, BenchmarkIbov, GeradorGrafico, ModeloRiscoVar, CarteiraAtivos, CambioWs. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    },
    {
        "id": "Atv15",
        "dominio": "Marketing",
        "prompt": "Implemente em Java as classes RelatorioVendasMultiTenant, RelatorioPerformanceMarketing que herda de 'RelatorioAnalitico'. A classe RelatorioAnalitico possui metodos sobre Consolidar métricas multitenant com cache Redis e exportação multi-formato. deve-se ter acopladas/dependencias DataWarehouseClient, CacheRedis, ExportadorPdf, ExportadorCsv, PermissionChecker, MotorTemplate, EnvioEmailService, AgendadorJob, LogAudit. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile. Não use formatação Markdown (não use ```). Na primeira linha da sua resposta, escreva exatamente: // --- DOMINIO: nome_do_dominio --- (ex: animal, funcionario, veiculo, pagamento)., Para cada classe Java, comece com o marcador exato: // --- ARQUIVO: NomeDaClasse.java ---, O pacote (package) de todas as classes deve ser: package br.edu.ifsudestemg.doo.{nome_do_dominio}; Crie o código completo para as classes da hierarquia principal e classes 'stub' (vazias) para as dependências."
    }
]
# ==============================================================================

def salvar_estrutura(texto_ia, llm_nome, dominio_correto):
    """Lê a resposta da IA e cria os arquivos .java nas pastas corretas, forçando o domínio."""
    
    caminho_base = f"src/main/java/br/edu/ifsudestemg/doo/{llm_nome}/{dominio_correto}"
    os.makedirs(caminho_base, exist_ok=True)

    padrao = r"//\s*---\s*ARQUIVO:\s*(\w+\.java)\s*---\s*(.*?)(?=//\s*---\s*ARQUIVO:|$)"
    matches = re.findall(padrao, texto_ia, re.DOTALL)
    
    if not matches:
        print(f"   ⚠️ Aviso: A IA não usou o marcador para {llm_nome} - {dominio_correto}.")
        return

    for nome_arquivo, conteudo in matches:
        nome_arquivo = re.sub(r'[^a-zA-Z0-9_\.-]', '', nome_arquivo).strip()

        if not nome_arquivo.endswith(".java"):
            nome_arquivo += ".java"

        conteudo_limpo = conteudo.replace("```java", "").replace("```", "").strip()
        
        conteudo_limpo = re.sub(r"package\s+br\.edu\.ifsudestemg\.doo\.[a-zA-Z0-9_.]+;", f"package br.edu.ifsudestemg.doo.{dominio_correto};", conteudo_limpo)
        
        caminho_completo = os.path.join(caminho_base, nome_arquivo)
        
        # Só salva se o arquivo não estiver vazio
        if len(conteudo_limpo) > 20:
            with open(caminho_completo, "w", encoding="utf-8") as f:
                f.write(conteudo_limpo)
            print(f"      ✅ Criado: {nome_arquivo}")
        else:
            print(f"      ⚠️ Ignorado (vazio): {nome_arquivo}")

# 3. Funções de geração para cada LLM (Temperatura 0 para determinismo)
def gerar_mistral(prompt):
    try:
        resp = client_mistral.chat.completions.create(
            model="qwen/qwen3.8-27b", # Ou "codestral-latest" se preferir foco em código
            messages=[{"role": "user", "content": prompt}],
            temperature=0
        )
        return resp.choices[0].message.content
    except Exception as e:
        return f"ERRO NA API MISTRAL: {e}"

def gerar_groq(prompt):
    try:
        resp = client_groq.chat.completions.create(
            model="openai/gpt-oss-20b", # Modelo aberto e gratuito no Groq
            messages=[{"role": "user", "content": prompt}],
            temperature=0
        )
        return resp.choices[0].message.content
    except Exception as e:
        return f"ERRO NA API GROQ: {e}"

def gerar_gemini(prompt):
    try:
        resp = client_gemini.models.generate_content(
            model="gemini-3.8-flash", # Ajuste para o modelo que você escolheu
            contents=prompt,
            config={"temperature": 0}
        )
        return resp.text
    except Exception as e:
        return f"ERRO NA API GEMINI: {e}"

def main():
    print("🚀 Iniciando a geração dos códigos...\n")
    
    # Cria apenas a pasta de prompts (histórico)
    os.makedirs("prompts", exist_ok=True)

    for Atv in Atvs:
        t_id = Atv["id"]
        dominio = Atv["dominio"].strip() # <--- PEGA O DOMÍNIO DA LISTA
        prompt = Atv["prompt"]

        caminho_verificacao = f"src/main/java/br/edu/ifsudestemg/doo/groq/{dominio}"
        if os.path.exists(caminho_verificacao) and len(os.listdir(caminho_verificacao)) > 0:
            print(f"⏭️ Pulando {t_id} ({dominio}) - já gerado pelo Groq.")
            continue
        
        print(f"⏳ Gerando {t_id}...")
        
        # Salva o prompt usado
        with open(f"prompts/{t_id}_prompt.txt", "w", encoding="utf-8") as f:
            f.write(prompt)
            
        # --- Gemini ---
        print("   -> Gemini...")
        codigo_gemini = gerar_gemini(prompt)
        time.sleep(10) # Pausa de 10s para não estourar o limite do Google

        salvar_estrutura(codigo_gemini, "gemini", dominio)  # <--- A MÁGICA ACONTECE AQUI
            
        # --- Groq ---
        print("   -> Groq...")
        codigo_groq = gerar_groq(prompt)
        salvar_estrutura(codigo_groq, "groq", dominio)
        time.sleep(2) # Pausa de 10s para não estourar o limite do Google
      # <--- E AQUI
            
        # --- Mistral ---
        print("   -> Mistral...")
        codigo_mistral = gerar_mistral(prompt)
        salvar_estrutura(codigo_mistral, "mistral", dominio)
        time.sleep(2) # Pausa de 10s para não estourar o limite do Google
 # <--- E AQUI
            
        print(f"✅ {t_id} concluída.\n")
        time.sleep(5)

    print("🎉 Todos os códigos foram gerados e organizados!")

if __name__ == "__main__":
    main()