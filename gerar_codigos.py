import os
import time
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
        "prompt": "Implemente em Java a classe 'Cachorro' e a classe 'Gato' que herda de 'Animal'. A classe Animal possui o método 'emitirSom()' e 'movimentar()'. deve-se ter classes acopladas/dependencias como 'Som' e 'Alimento'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv02",
        "prompt": "Implemente em Java as classes 'Carro' e 'Moto' que herda de 'Veiculo'. A classe Veiculo possui distribuicoes de informacoes sobre Calcular consumo de combustível e aceleração herdando atributos de chassi, deve-se ter acopladas/dependencias de 'Motor', 'Combustivel', 'Placa'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv03",
        "prompt": "Implemente em Java as classes 'Retangulo' e 'Circulo' que herda de 'FormaGeometrica'. A classe FormaGeometrica possui os metodos abstratos 'calcularArea()' e 'desenhar()'. deve-se ter acopladas/dependencias 'Ponto2D' e 'Cor'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv04",
        "prompt": "Implemente em Java as classes 'ContaCorrente' e 'ContaPoupanca' que herda de 'ContaBancaria'. A classe ContaBancaria possui metodos sobre Aplicar tarifas bancárias específicas sobrescrevendo método sacar(). deve-se ter acopladas/dependencias 'Titular', 'Extrato' e 'Moeda'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv05",
        "prompt": "Implemente em Java as classes 'Smartphone' e 'Tablet' que herda de 'DispositivoEletronico'. A classe DispositivoEletronico possui metodos sobre Gerenciar ciclo de bateria e especificações de hardware portátil. deve-se ter acopladas/dependencias 'Bateria' e 'Fabricante'. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv06",
        "prompt": "Implemente em Java as classes 'Gerente' e 'Diretor' que herda de 'Funcionario'. A classe Funcionario possui metodos sobre Calcular bônus salarial e validar equipe subordinada do gestor. deve-se ter acopladas/dependencias Departamento, FolhaPagamento, BonusPolicy, Equipe, Auditoria. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv07",
        "prompt": "Implemente em Java as classes 'Guerreiro' e 'Mago' que herda de 'PersonagemJogo'. A classe PersonagemJogo possui metodos sobre Processar sistema de combate e cálculo de dano com equipamentos. deve-se ter acopladas/dependencias Arma, Armadura, Inventario, EfeitoStatus. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv08",
        "prompt": "Implemente em Java as classes CartaoCredito, Pix que herda de 'MeioDePagamento'. A classe MeioDePagamento possui metodos sobre Efetuar pagamento com verificação antifraude e emissão de recibo. deve-se ter acopladas/dependencias GatewayPagamento, ValidadorFraude, Recibo, Notificacao, Moeda. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv09",
        "prompt": "Implemente em Java as classes NotaFiscalEletronica, CupomFiscal que herda de 'DocumentoFiscal'. A classe DocumentoFiscal possui metodos sobre Validar, assinar digitalmente e calcular alíquotas de impostos fiscais. deve-se ter acopladas/dependencias Emitente, Destinatario, ItemTributado, CertificadoDigital, RepositorioXml, ImpostoCalculador. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv10",
        "prompt": "Implemente em Java as classes SensorTemperatura, SensorPressao que herda de 'SensorIoT'. A classe SensorIoT possui metodos sobre Calibrar e transmitir leituras ambientais via protocolo MQTT. deve-se ter acopladas/dependencias ConexaoMqtt, Calibrador, LogManager, PayloadTelemetria. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv11",
        "prompt": "Implemente em Java as classes EntregaExpressa, EntregaInternacional que herda de 'ServicoEntrega'. A classe ServicoEntrega possui metodos sobre Orquestrar despacho expresso com roteirização GPS e seguro de carga. deve-se ter acopladas/dependencias RoteirizadorGps, Rastreador, SeguroCarga, Transportadora, TabelaFrete, NotificadorSms, Aduana, CalculadorPrazo. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv12",
        "prompt": "Implemente em Java as classes PedidoECommerce, PedidoB2B que herda de 'ProcessadorPedido'. A classe ProcessadorPedido possui metodos sobre Executar fluxo de checkout com reserva de estoque, antifraude e CRM. deve-se ter acopladas/dependencias CatalogoService, EstoqueService, PagamentoGateway, AntiFraudeService, CrmService, FreteCalculator, EmailDispatcher, NotaFiscalService, AnaliseCredito. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv13",
        "prompt": "Implemente em Java as classes AdministradorSeguranca, OperadorSuporte que herda de 'UsuarioSistema'. A classe UsuarioSistema possui metodos sobre Autenticar com MFA, auditar logs SIEM e sincronizar diretório LDAP. deve-se ter acopladas/dependencias AutenticadorMfa, RepositorioLdap, PoliticaAcesso, LoggerAuditoria, GerenciadorSessao, TokenJwt, NotificadorAlerta. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv14",
        "prompt": "Implemente em Java as classes SimuladorInvestimentoAvancado, SimuladorPrevidencia que herda de 'SimuladorFinanceiro'. A classe SimuladorFinanceiro possui metodos sobre Simular projeção estocástica com curva de juros, inflação e câmbio. deve-se ter acopladas/dependencias IndiceBacen, CurvaJuros, TabelaRegressivaIr, BenchmarkIbov, GeradorGrafico, ModeloRiscoVar, CarteiraAtivos, CambioWs. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    },
    {
        "id": "Atv15",
        "prompt": "Implemente em Java as classes RelatorioVendasMultiTenant, RelatorioPerformanceMarketing que herda de 'RelatorioAnalitico'. A classe RelatorioAnalitico possui metodos sobre Consolidar métricas multitenant com cache Redis e exportação multi-formato. deve-se ter acopladas/dependencias DataWarehouseClient, CacheRedis, ExportadorPdf, ExportadorCsv, PermissionChecker, MotorTemplate, EnvioEmailService, AgendadorJob, LogAudit. Gere o código completo da hierarquia principal e stubs para as dependências. Importante: Gere o código completo (com atributos e métodos) apenas para a Classe Pai e as Classes Filhas. Para as classes de dependência (ex: Motor, Placa, GatewayPagamento), gere apenas a estrutura básica da classe (esqueleto) com métodos vazios, o suficiente para que o código compile."
    }
]
# ==============================================================================

# 3. Funções de geração para cada LLM (Temperatura 0 para determinismo)
def gerar_mistral(prompt):
    try:
        resp = client_mistral.chat.completions.create(
            model="mistral-small-2603", # Ou "codestral-latest" se preferir foco em código
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

# 4. Cria as pastas de saída se não existirem
pastas = ["codigos-gerados/gemini", "codigos-gerados/groq", "codigos-gerados/mistral", "prompts"]

for pasta in pastas:
    os.makedirs(pasta, exist_ok=True)

# 5. Loop principal de geração
def main():
    print("🚀 Iniciando a geração dos códigos...\n")
    
    # Atualize as pastas para os novos nomes
    pastas = ["codigos-gerados/gemini", "codigos-gerados/groq", "codigos-gerados/mistral", "prompts"]
    
    for pasta in pastas:
        os.makedirs(pasta, exist_ok=True)

    for Atv in Atvs:
        t_id = Atv["id"]
        prompt = Atv["prompt"]
        
        print(f"⏳ Gerando {t_id}...")
        
        with open(f"prompts/{t_id}_prompt.txt", "w", encoding="utf-8") as f:
            f.write(prompt)
            
        # --- Gemini ---
        print("   -> Gemini...")
        codigo_gemini = gerar_gemini(prompt)
        with open(f"codigos-gerados/gemini/{t_id}_gemini.java", "w", encoding="utf-8") as f:
            f.write(codigo_gemini)
            
        # --- Groq ---
        print("   -> Groq (Llama 3)...")
        codigo_groq = gerar_groq(prompt)
        with open(f"codigos-gerados/groq/{t_id}_groq.java", "w", encoding="utf-8") as f:
            f.write(codigo_groq)
            
        # --- Mistral ---
        print("   -> Mistral...")
        codigo_mistral = gerar_mistral(prompt)
        with open(f"codigos-gerados/mistral/{t_id}_mistral.java", "w", encoding="utf-8") as f:
            f.write(codigo_mistral)
            
        print(f"✅ {t_id} concluída.\n")
        time.sleep(5)

    print("🎉 Todos os códigos foram gerados e salvos!")

if __name__ == "__main__":
    main()