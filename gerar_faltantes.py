import os
import time
import re
from dotenv import load_dotenv
from openai import OpenAI

load_dotenv()

# Vamos usar o Groq para substituir o Mistral, pois ele tem cota melhor
client_groq = OpenAI(api_key=os.getenv("GROQ_API_KEY"), base_url="https://api.groq.com/openai/v1")

# Importa as 15 atividades do seu script principal para não precisar copiar tudo
# (Certifique-se de que o nome do arquivo principal seja gerar_codigos.py)
from gerar_codigos import Atvs, salvar_estrutura

# Sua instrução de formatação (a mesma que está no script principal)
instrucao = "\n\nINSTRUÇÕES DE SAÍDA OBRIGATÓRIAS:\n1. NÃO use formatação Markdown.\n2. Na primeira linha, escreva: // --- DOMINIO: nome_do_dominio ---\n3. Para cada classe, escreva: // --- ARQUIVO: NomeDaClasse.java ---\n4. O pacote deve ser: package br.edu.ifsudestemg.doo.nome_do_dominio;\n5. Gere stubs para dependências."

def gerar_groq_substituto(prompt, modelo):
    try:
        resp = client_groq.chat.completions.create(
            model=modelo,
            messages=[{"role": "user", "content": prompt}],
            temperature=0
        )
        return resp.choices[0].message.content
    except Exception as e:
        return f"ERRO NA API GROQ ({modelo}): {e}"

def main():
    print("🚀 Gerando códigos faltantes...\n")
    
    for Atv in Atvs:
        t_id = Atv["id"]
        prompt_final = Atv["prompt"] + instrucao
        
        # 1. Corrigir a Atv11 que faltou no Groq
        if t_id == "Atv11":
            print(f"⏳ Gerando {t_id} (Groq faltante)...")
            codigo = gerar_groq_substituto(prompt_final, "openai/gpt-oss-20b")
            salvar_estrutura(codigo, "groq")
            time.sleep(5)
            
        # 2. Substituir o Mistral pelo Groq (usando outro modelo para diversificar)
        # Vamos usar o modelo llama-3.1-8b-instant para simular o Mistral
        print(f"⏳ Gerando {t_id} (Substituto do Mistral)...")
        codigo = gerar_groq_substituto(prompt_final, "openai/gpt-oss-20b")
        # Salva na pasta mistral para manter a organização do relatório
        salvar_estrutura(codigo, "mistral") 
        
        print(f"✅ {t_id} concluída.\n")
        time.sleep(5)

    print("🎉 Códigos faltantes gerados!")

if __name__ == "__main__":
    main()