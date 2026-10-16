# Como publicar uma versão nova

Guia para publicar no GitHub usando o script [`ferramentas/publicar.sh`](../ferramentas/publicar.sh),
que faz **commit + push + Release** (enviando os arquivos de `dist/` como assets) de uma vez só.

## 1. Guarde o token em um arquivo local

Crie um **Personal Access Token** no GitHub (Settings → Developer settings → Personal access tokens →
**Fine-grained**), dê acesso a este repositório e marque as permissões:

- **Contents:** Read and write
- **Releases:** Read and write

Depois salve o token em um arquivo **fora do repositório**:

```bash
printf '%s' 'SEU_TOKEN_AQUI' > ~/.gh_token
chmod 600 ~/.gh_token
```

> ⚠️ **Nunca** coloque o token dentro da pasta do repositório. O `.gitignore` já bloqueia os nomes
> mais comuns (`.gh_token`, `*.token`, `.env`), mas o cuidado principal é não criar o arquivo ali.
> Se um token vazar, **revogue** em Settings → Developer settings e gere outro.

## 2. Gere os artefatos

```bash
export JAVA_HOME=/usr/lib/jvm/java-25-openjdk-amd64    # JDK 25
./gradlew build comboTest
cp build/libs/narutoanimemod-*.jar dist/
```

## 3. Escreva as notas da versão (aparecem publicamente na Release)

Crie `dist/NOTAS-DA-VERSAO.md` com **o que mudou**, **o que foi corrigido** e os **bugs da versão
anterior** — é esse texto que aparece na página da Release, no estilo da página de notícias do TLauncher.

## 4. Publique

```bash
# só commit + push:
./ferramentas/publicar.sh ~/meu-clone

# commit + push + Release v0.1.2 (envia tudo de dist/ como assets):
./ferramentas/publicar.sh ~/meu-clone v0.1.2 "v0.1.2 — descrição curta"
```

> 🔒 O script **nunca apaga nada** do repositório — só adiciona/atualiza (regra deste projeto).
