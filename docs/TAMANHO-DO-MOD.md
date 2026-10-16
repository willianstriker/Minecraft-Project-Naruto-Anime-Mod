# Por que o mod tem ~50 KB? (e por que isso NÃO quer dizer que ele está incompleto)

Dúvida comum ao olhar o jar do port e comparar com o mod original. Resumo: **mod é código; textura e som é que pesam.**

## Comparação real (medida nos arquivos)

| Arquivo | Tamanho | O que tem dentro |
|---|---|---|
| `Naruto-Anime-Mod_V0.9.17_Beta_1.7.10-1.0.jar` (original do Mathioks, 1.7.10) | **20.396.504 bytes (20,4 MB)** | `assets/` = 21,3 MB (texturas, modelos, sons) · `alcoholmod/` = 10,7 MB (código) · resto: Susanoo, pacotes, `mcmod.info` |
| `NarutoMod-1.20-1.8.1-universal.jar` (Sekwah41's, 1.20) | **4.324.825 bytes (4,3 MB)** | `assets/` = 4,2 MB · `com/` = 549 KB · `narutologo.png` = 196 KB |
| `SekCLib-1.20-1.0.2-universal.jar` (biblioteca do Sekwah41's) | **48.945 bytes (48,9 KB)** | só código (`com/` = 89 KB) — **tamanho parecido com o nosso port** |
| `narutoanimemod-26.3-*.jar` (este port) | **~50 KB** | 12 classes + 34 conquistas + textos PT/EN |

Ou seja: um jar de biblioteca/código puro, oficialmente publicado e funcionando, tem o mesmo tamanho do nosso.

## O que tem dentro do nosso jar (é um `.zip` — dá para abrir e conferir)

```
META-INF/mods.toml                  <- descrição do mod (é o que o Forge lê para carregar)
com/mathioks/narutomod/**           <- classes do mod (combos, jutsus, rede, chakra)
data/am/advancement/*.json          <- 34 conquistas com a combinação de teclas na descrição
assets/am/lang/pt_br.json, en_us.json
pack.mcmeta
```

## O que falta — e quando entra

O que **não** foi portado ainda são os **recursos visuais/sonoros** do mod original (21 MB de `assets/`).
Eles entram nas próximas fases, junto com:

- texturas dos itens/blocos e sons dos jutsus;
- efeitos visuais (partículas) dos jutsus;
- HUD do combo na tela;
- tela de jutsus (tecla J);
- os 477 jutsus restantes do catálogo.

Conforme isso entra, o jar **cresce sozinho** — como aconteceu no mod original.

## O que realmente impede um mod de carregar (não é o tamanho)

1. **Jar na pasta errada** — versão Forge normal lê `.minecraft/mods`; **modpack do TLauncher lê
   `.minecraft/versions/<nome do pack>/mods`** (a pasta global é ignorada pelo pack);
2. **Versão diferente** — este port exige **Minecraft 26.3 + Forge 66.x**;
3. **Jogo aberto sem Forge** — sem Forge nenhum mod carrega (o rodapé do menu precisa mostrar
   `Forge Beta 66.x`);
4. **Jar antigo (1.7.10 / 1.20)** — o Forge moderno usa `META-INF/mods.toml`; os antigos usam `mcmod.info`
   e **não são lidos de jeito nenhum** no 26.3. É o caso dos arquivos de `referencia/` (4 MB / 20 MB / 40 MB).

## Como saber, em 3 segundos, se o mod carregou (v0.1.2+)

Ao abrir o jogo, no **menu principal**, aparece um aviso no canto: **“Naruto Anime Mod v0.1.2 - CARREGADO”**.
Se aparecer, o mod está lido e funcionando. Se não aparecer, o jar não está na pasta que o Forge lê —
rode o `INSTALAR-MOD.bat` ([guia](guias/INSTALAR-E-DIAGNOSTICAR.html)) e envie o `RELATORIO`, que ele
mostra cada pasta `mods`, o tamanho dos jars e o veredito a partir do log.
