# 🆕 v0.1.2 — "prova na tela" · 16/10/2026

> Atualização de **diagnóstico e clareza**. Duas novidades pensadas para você confirmar
> num piscar de olhos se o mod está carregado — e para o relatório dizer sozinho onde está o problema.

---

## ✨ Novidades desta atualização

### 1. Prova na tela ao abrir o jogo — sem entrar em mundo, sem tecla, sem log

Duas coisas aparecem **no menu principal**:

- **Texto fixo no canto superior esquerdo** (desenhado por cima da tela, não some):
  `Naruto Anime Mod v0.1.2 CARREGADO - 33 jutsus ativos (combos C/V/B)`
- **Aviso (toast) no canto superior direito**:
  `Naruto Anime Mod v0.1.2 - CARREGADO · 33 jutsus ativos - combos C/V/B (tecla L: conquistas)`

Na **tela Mods** o mesmo aviso aparece de novo (é lá que dá para conferir a lista).

📸 Provas: [`docs/evidencias/04-v012-banner-no-menu.png`](../../docs/evidencias/04-v012-banner-no-menu.png) e
[`05-v012-toast-e-tela-mods.png`](../../docs/evidencias/05-v012-toast-e-tela-mods.png).

**Se nada disso aparecer**, o Forge não está lendo o jar — e aí o relatório (abaixo) mostra o porquê.

### 2. Instalador v2 com **veredito automático**
O `INSTALAR-MOD.bat` agora, além de instalar, monta um **RELATORIO** que lê os logs do seu jogo e diz na letra:

- `[ok] 1900 advancements = mod presente` (ou `[X] 1866 advancements = este log NÃO tem o mod`);
- `[ok] o log tem modlauncher (abriu com Forge)`;
- lista **todos** os arquivos de cada pasta `mods` **com o tamanho** (para pegar jar antigo de 20/40 MB);
- copia o `pack-*.json` de cada versão/modpack (é ali que se vê **qual Minecraft** cada um usa e se tem Forge);
- copia os `LOG-*.txt` e possíveis `CRASH-*`.

### 3. Nome mais claro na tela Mods
Agora aparece como **`Naruto Anime Mod (port 26.3)`** — para não confundir com o modpack do TLauncher.

### 4. `INSTALAR-MOD.bat` também no ZIP desta versão
Sempre que gerar o relatório, rode o script de novo e escolha **[4]** — assim o log é o da sessão nova.

---

## ℹ️ "Por que o mod tem só 50 KB? Isso não está incompleto?"

Está completo, sim — **mod é código, e o peso do mod original era textura e som**. Comparação real:

| Arquivo | Tamanho | O que é |
|---|---|---|
| `Naruto-Anime-Mod_V0.9.17_Beta_1.7.10-1.0.jar` (original, 1.7.10) | **20,4 MB** | 21,0 MB são **texturas/modelos/sons** + 10,7 MB de código |
| `SekCLib-1.20-1.0.2-universal.jar` (biblioteca do Sekwah41's) | **48,9 KB** | só código — praticamente do mesmo tamanho que o nosso |
| `narutoanimemod-26.3-v0.1.2.jar` (este port) | **~50 KB** | 12 classes + 34 conquistas + textos PT/EN |

O que tem dentro do jar (é um zip — dá para abrir e conferir):
`META-INF/mods.toml`, `com/mathioks/narutomod/**` (classes), `data/am/advancement/**` (34 conquistas), `assets/am/lang/**`.

⚠️ **O tamanho não é o problema.** O que impede um mod de carregar é: (a) jar na pasta errada, (b) versão do
Minecraft/Forge diferente, (c) o jogo aberto **sem Forge**. Os jars de 4 MB / 20 MB / 40 MB que estão em
`referencia/` são os mods **antigos** (1.7.10 / 1.20) — o Forge do 26.3 **não lê o formato deles** (usam
`mcmod.info`, o moderno exige `META-INF/mods.toml`).

> As texturas, sons e efeitos do mod original **vão** entrar no port — é a próxima fase (junto com o HUD do
> combo, a tela de jutsus com a tecla J e os 477 jutsus restantes). Aí o jar vai crescer naturalmente.

---

## 🗒️ Mudanças técnicas

- `NarutoKeys`: toast de carregamento no menu (`SystemToast`) + logs de diagnóstico.
- `mods.toml`: `version="0.1.2"`, `displayName="Naruto Anime Mod (port 26.3)"`.
- `INSTALAR-MOD.bat` v2: veredito automático, inspeção de jars por tamanho, cópia dos `pack-*.json`.
- `build.gradle`: versão `0.1.2`.

---

## 🙏 Créditos

**Mathioks** (Naruto Anime Mod original, 1.7.10) · **ninjaespio1000** (Naruto Anime Mod Plus+) ·
**Sekwah41's** (NarutoMod/SekCLib — sistema de teclas C/V/B, MIT) · **Masashi Kishimoto** (Naruto) ·
port 26.3 por **willianstriker**. Código aberto sob licença **MIT**.
