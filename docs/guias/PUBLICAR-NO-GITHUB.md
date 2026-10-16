# 📤 Como publicar este repositório no GitHub

O repositório já está **pronto e organizado** (10 commits temáticos + tag `v0.1.0`).
Você só precisa enviá-lo para o GitHub. Escolha **uma** das opções abaixo.

---

## ✅ Opção 1 — Enviar pelo terminal (recomendado, 3 comandos)

### 1.1 Crie o repositório vazio no GitHub
1. Acesse <https://github.com/new>
2. **Repository name:** `NarutoAnimeMod-26.3`
3. **Não** marque "Add a README file", "Add .gitignore" nem "Choose a license"
   *(o repositório precisa estar vazio, senão dá conflito)*
4. Clique em **Create repository**

### 1.2 Extraia o zip do repositório
Extraia `NarutoAnimeMod-26.3-repo.zip` em qualquer pasta (ele **já vem com o histórico Git completo**).

### 1.3 Envie
No terminal, dentro da pasta extraída:

```bash
git remote add origin https://github.com/SEU-USUARIO/NarutoAnimeMod-26.3.git
git push -u origin main
git push origin v0.1.0
```

Pronto — o repositório sobe com **todo o histórico de commits organizado**.

---

## ✅ Opção 2 — Enviar pelo GitHub Desktop (sem terminal)

1. Crie o repositório vazio no GitHub (passo 1.1 acima)
2. Abra o **GitHub Desktop** → *File* → **Add local repository**
3. Selecione a pasta extraída do zip
4. Clique em **Publish repository** / **Push origin**
5. *(Opcional)* Marque para publicar a tag `v0.1.0` em *Repository → Tags*

---

## ✅ Opção 3 — Reconstruir o repo a partir do bundle

Se preferir o arquivo único `.bundle`:

```bash
git clone narutoanimemod-repo.bundle NarutoAnimeMod-26.3
cd NarutoAnimeMod-26.3
git remote set-url origin https://github.com/SEU-USUARIO/NarutoAnimeMod-26.3.git
git push -u origin main
git push origin v0.1.0
```

---

## 🏷️ Depois de publicar (recomendações)

### Descrição do repositório
```
Port do Naruto Anime Mod (1.7.10) para Minecraft 26.3/Forge — jutsus ativados por combos de teclas C/V/B, sem itens de talismã.
```

### Topics (tags)
`minecraft` `minecraft-mod` `forge` `naruto` `minecraft-26-3` `java` `jutsu` `mod`

### Crie uma Release
O jeito certo de distribuir o `.jar` é por **Releases** (o arquivo em `dist/` serve de histórico, mas Releases é o que os jogadores baixam):

1. No GitHub: **Releases** → **Draft a new release**
2. **Tag:** `v0.1.0` (já existe)
3. **Title:** `v0.1.0 — Port para Minecraft 26.3`
4. **Descreva** as mudanças (pode copiar do `CHANGELOG.md`)
5. **Anexe** o arquivo `dist/narutoanimemod-26.3-v0.1.0.jar`

### Ativar o GitHub Pages (para a página de conquistas)
1. **Settings** → **Pages**
2. Source: *Deploy from a branch* → Branch: `main` → pasta `/docs`
3. A página de conquistas ficará em:
   `https://SEU-USUARIO.github.io/NarutoAnimeMod-26.3/pagina_conquistas.html`

---

## 🔄 Como enviar as próximas atualizações

Depois das fases seguintes (mais jutsus, HUD, tela de jutsus etc.):

```bash
git add .
git commit -m "feat: HUD do combo na tela"
git push
```

Para publicar uma nova versão:

```bash
git tag -a v0.2.0 -m "v0.2.0 — HUD e tela de jutsus"
git push origin v0.2.0
```

---

## ⚠️ Sobre o `.jar` no repositório

O arquivo `dist/*.jar` **está** versionado (são só 50 KB, útil para quem quiser baixar direto).
Se preferir manter o repositório apenas com código, remova antes de enviar:

```bash
git rm -r --cached dist/
git commit -m "chore: nao versionar builds (usar Releases)"
```

E adicione ao `.gitignore`: `dist/*.jar`
