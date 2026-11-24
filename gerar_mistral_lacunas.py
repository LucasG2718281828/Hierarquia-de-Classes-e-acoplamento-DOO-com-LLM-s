import os
import time
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

def main():
    print("🚀 Preenchendo lacunas APENAS da coluna 'Mistral'...\n")
    
    for Atv in Atvs:
        t_id = Atv["id"]
        dominio = Atv["dominio"].strip()
        prompt = Atv["prompt"]
        
        caminho_mistral = f"src/main/java/br/edu/ifsudestemg/doo/mistral/{dominio}"
        
        # Se a pasta não existe ou tem menos de 2 arquivos
        if not os.path.exists(caminho_mistral) or len(os.listdir(caminho_mistral)) < 2:
            print(f"⏳ Gerando {t_id} ({dominio})...")
            
            # Usando o GPT-OSS 20B, que já provou funcionar
            codigo = gerar_com_groq(prompt, "openai/gpt-oss-20b")
            
            if codigo and "ERRO" not in codigo:
                # Limpa o texto para garantir que não tenha formatação Markdown
                codigo = codigo.replace("```java", "").replace("```", "")
                salvar_estrutura(codigo, "mistral", dominio)
            else:
                print(f"   ⚠️ Falha ao gerar {t_id}")
            
            time.sleep(3) # Pausa para não estourar o limite do Groq
        else:
            print(f"⏭️ {t_id} ({dominio}) já está preenchido. Pulando.")

    print("🎉 Processo do Mistral concluído!")

if __name__ == "__main__":
    main()