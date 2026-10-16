# ⚠️ ATENÇÃO: os jars desta pasta NÃO são para jogar

Os arquivos de `referencia/` são **fontes de consulta** usadas para fazer o port do mod:

| Arquivo | O que é | Funciona no 26.3? |
|---|---|---|
| `Naruto-Anime-Mod_V0.9.17_Beta_1.7.10-1.0.jar` (20,4 MB) | Mod **original** do Mathioks, feito para **Minecraft 1.7.10** | ❌ nunca |
| `NarutoMod-1.20-1.8.1-universal.jar` (4,3 MB) | Mod do **Sekwah41's**, feito para **Minecraft 1.20** | ❌ nunca |
| `SekCLib-1.20-1.0.2-universal.jar` (48 KB) | Biblioteca do Sekwah41's (1.20) | ❌ nunca |

O Forge do Minecraft 26.3 **não reconhece o formato antigo de mod** (esses jars usam `mcmod.info`, e o Forge moderno exige `META-INF/mods.toml`). Colocar qualquer um deles na pasta `mods` **não faz o mod funcionar** — só confunde o diagnóstico.

## O único arquivo para jogar é o do Release

```
narutoanimemod-26.3-v0.1.0.jar   (50.246 bytes, Release v0.1.0)
narutoanimemod-26.3-v0.1.1.jar   (Release v0.1.1, com mensagem de boas-vindas no chat)
```

Como identificar o certo:
- Está na página **Releases** do repositório (não nesta pasta `referencia/`);
- Tem **cerca de  cincuenta KB** (não 4 MB, não 20 MB, não 40 MB);
- Dentro dele: `META-INF/mods.toml`, `data/am/advancement/*.json` e `com/mathioks/narutomod/`.

> Se você colocou um jar grande na pasta `mods`, **tire** e use o do Release.
