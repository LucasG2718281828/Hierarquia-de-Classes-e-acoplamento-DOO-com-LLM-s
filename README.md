# Geração de Código Orientado a Objetos por LLM's: uma Análise da Qualidade de Herança sob Diferentes Hierarquias de classes e Contexto de Acoplamento

Esse repositório pretence ao projeto final da disciplina de desenvolvimento orientado a objetos do IF Sudeste MG - Campus Manhuaçu.

Esta análise é dedicada a entender e avaliar por perspectivas qualitativas de códigos gerados por agentes IA sobre o contexto de orientação a objetos utilizando java sobre o pretexto do quanto devemos confiar nas IA's como ferramentas diretas de desenvolvimento de códigos

Obs: Devido a questões orçamentárias foram utilizados agentes de IA's gratuitos e funcionais na composição do código e foram dispostos 45 códigos sendo, 15 para cada agente selecionado. 

Agentes utilizados: 

1. Gemini (gemini-3.8-flash) - Modelo fechado gerado por API
2. Groq (openai/gpt-oss-20b) - Modelo Aberto para simulação do gemini em caso de falhas
3. Groq Mistral (mistral-small-2603) - Modelo aberto para simualçao do mistral

**Estrutura**

`/codigos-gerados`: Contém arquivos .java gerados por LLM's sendo 45 códigos dividos entre os agentes, avaliados e não foi feita nenhuma modificação no código após a IA gerar.

`/prompts`: Contém os prompts que foram gerados de cada IA.

`/src/test/java`: 

`/planilha`: Contém os dados de Análise de qualidade e complexidade em 'Avaliação.csv'.

`/gerar_codigos.py`: Um script em python utilizado para automatizar a geração de prompts entre os agentes de IA.

`/pom.xml`: Configuração do Maven.

**Como executar**

1. Clone o repositório:

git clone https://github.com/seu-usuario/seu-repositorio.git

cd seu-repositorio

2. Instalar requisições:

pip install -r requirements.txt

3. Configurar chaves de API

Renomeie o arquivo .env.example para .env.

Preencha o arquivo .env com as suas chaves/Key reais, sendo keys de API's disponibilizadas pelas plataformas de seu devido agente de IA.

4. Rode o script

python gerar_codigos.py

**Tools**

A qualidade do código foi analisada pelo SonarQube e pelo JUnit 5, sendo o Sonar para análise de complexidade ciclomática, acoplamento e code smells e o Junit 5 para teste de correção.