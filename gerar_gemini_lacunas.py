import os
import time
from dotenv import load_dotenv
from openai import OpenAI
# Importa a lista de atividades e a função de salvar do script principal
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
        return f"ERRO: {e}"

def main():
    print("🚀 Preenchendo lacunas da coluna 'Gemini' usando o Groq...\n")
    
    for Atv in Atvs:
        t_id = Atv["id"]
        dominio = Atv["dominio"].strip()
        prompt = Atv["prompt"]
        
        caminho_gemini = f"src/main/java/br/edu/ifsudestemg/doo/gemini/{dominio}"
        
        # Se a pasta não existe ou está vazia, gera o código
        if not os.path.exists(caminho_gemini) or len(os.listdir(caminho_gemini)) == 0:
            print(f"⏳ Gerando {t_id} ({dominio}) para a coluna Gemini...")
            # Usa um modelo diferente do Groq para manter a diversidade
            codigo = gerar_com_groq(prompt, "llama-3.3-70b-versatile")
            salvar_estrutura(codigo, "gemini", dominio)
            time.sleep(3)
        else:
            print(f"⏭️ {t_id} ({dominio}) já está preenchido. Pulando.")

    print("🎉 Lacunas preenchidas!")

if __name__ == "__main__":
    main()