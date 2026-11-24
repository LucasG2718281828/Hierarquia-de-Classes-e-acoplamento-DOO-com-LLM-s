import os
import time
from dotenv import load_dotenv
from openai import OpenAI

load_dotenv()
GROQ_KEY = os.getenv("GROQ_API_KEY")

# Cliente Groq
client_groq = OpenAI(api_key=GROQ_KEY, base_url="https://api.groq.com/openai/v1")

# Cole aqui apenas a lista de Atvs que você já tem (as 15)
from gerar_codigos import Atvs

# Função para gerar com um modelo específico do Groq
def gerar_groq_modelo(prompt, modelo):
    try:
        resp = client_groq.chat.completions.create(
            model=modelo,
            messages=[{"role": "user", "content": prompt}],
            temperature=0
        )
        return resp.choices[0].message.content
    except Exception as e:
        return f"ERRO NA API GROQ ({modelo}): {e}"

# Pastas onde faltam códigos (gemini e mistral)
os.makedirs("codigos-gerados/gemini", exist_ok=True)
os.makedirs("codigos-gerados/mistral", exist_ok=True)

print("🚀 Gerando códigos faltantes usando modelos do Groq...\n")

for Atv in Atvs:
    t_id = Atv["id"]
    prompt = Atv["prompt"]
    print(f"⏳ Gerando {t_id}...")
    
    # Usa um modelo do Groq para "substituir" o Gemini
    # print("   -> Groq (como Gemini)...")
    #codigo = gerar_groq_modelo(prompt, "openai/gpt-oss-20b")
    #with open(f"codigos-gerados/gemini/{t_id}_groq_gemini.java", "w", encoding="utf-8") as f:
     #   f.write(codigo)
    
    # Usa outro modelo do Groq para "substituir" o Mistral
    print("   -> Groq (como Mistral)...")
    codigo = gerar_groq_modelo(prompt, "openai/gpt-oss-20b")
    with open(f"codigos-gerados/mistral/{t_id}_groq_mistral.java", "w", encoding="utf-8") as f:
        f.write(codigo)
    
    print(f"✅ {t_id} concluída.\n")
    time.sleep(3) # Pausa para não estourar o limite do Groq

print("🎉 Todos os códigos faltantes foram gerados!")