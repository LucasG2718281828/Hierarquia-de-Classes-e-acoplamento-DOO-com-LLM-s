import os
import time
import re
from dotenv import load_dotenv
from openai import OpenAI
from gerar_codigos import Atvs, salvar_estrutura

load_dotenv()
client_groq = OpenAI(api_key=os.getenv("GROQ_API_KEY"), base_url="https://api.groq.com/openai/v1")

def gerar_com_groq(prompt, modelo):
    try:
        resp = client_groq.chat.completions.create(
            model=modelo,
            messages=[{"role": "user", "content": prompt}],
            temperature=0
        )
        return resp.choices[0].message.content
    except Exception as e:
        print(f"   ❌ Erro no modelo {modelo}: {e}")
        return None

def limpar_nome_arquivo(nome):
    # Remove caracteres inválidos para o Windows: \ / : * ? " < > |
    return re.sub(r'[\\/:*?"<>|]', '_', nome)

def main():
    print("🚀 Preenchendo lacunas com modelos do Groq...\n")
    
    for Atv in Atvs:
        t_id = Atv["id"]
        dominio = Atv["dominio"].strip()
        prompt = Atv["prompt"]
        
        # --- Preencher Gemini (usando GPT-OSS 120B) ---
        caminho_gemini = f"src/main/java/br/edu/ifsudestemg/doo/gemini/{dominio}"
        if not os.path.exists(caminho_gemini) or len(os.listdir(caminho_gemini)) == 0:
            print(f"⏳ Gerando {t_id} ({dominio}) para Gemini (GPT-OSS 120B)...")
            codigo = gerar_com_groq(prompt, "openai/gpt-oss-120b")
            if codigo and "ERRO" not in codigo:
                # Limpa o texto antes de salvar para evitar erros de nome de arquivo
                codigo = codigo.replace("```java", "").replace("```", "")
                salvar_estrutura(codigo, "gemini", dominio)
            time.sleep(3)
        
        # --- Preencher Mistral (usando Qwen) ---
        caminho_mistral = f"src/main/java/br/edu/ifsudestemg/doo/mistral/{dominio}"
        if not os.path.exists(caminho_mistral) or len(os.listdir(caminho_mistral)) < 2:
            print(f"⏳ Gerando {t_id} ({dominio}) para Mistral (Qwen)...")
            codigo = gerar_com_groq(prompt, "qwen/qwen3.8-27b")
            if codigo and "ERRO" not in codigo:
                codigo = codigo.replace("```java", "").replace("```", "")
                salvar_estrutura(codigo, "mistral", dominio)
            time.sleep(3)

    print("🎉 Processo concluído!")

if __name__ == "__main__":
    main()