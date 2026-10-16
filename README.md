<div align="center">

# 🍥 Naruto Anime Mod — 26.3

**Port do Naruto Anime Mod (1.7.10, do Mathioks) para o Minecraft 26.3 / Forge 66**

Sistema de ativação de jutsus por **combinação de teclas C / V / B** — sem itens de talismã.

![Minecraft](https://img.shields.io/badge/Minecraft-26.3-brightgreen)
![Forge](https://img.shields.io/badge/Forge-66.0.12-orange)
![Java](https://img.shields.io/badge/Java-25-blue)
![Status](https://img.shields.io/badge/status-alfa-yellow)
![License](https://img.shields.io/badge/license-MIT-green)

### 📥 [**BAIXAR A ÚLTIMA VERSÃO**](https://github.com/willianstriker/Minecraft-Project-Naruto-Anime-Mod/releases/latest)

[![Release](https://img.shields.io/github/v/release/willianstriker/Minecraft-Project-Naruto-Anime-Mod?label=vers%C3%A3o&color=blue)](https://github.com/willianstriker/Minecraft-Project-Naruto-Anime-Mod/releases/latest)

</div>

---

## 🆕 NOVA ATUALIZAÇÃO — v0.1.1 · 16/10/2026

> ### ⚠️ Se você baixou a v0.1.0, atualize!
> A v0.1.0 tinha **2 bugs** que faziam o mod não responder às teclas C/V/B. Os dois foram encontrados
> rodando o mod num Minecraft 26.3 de verdade e **corrigidos na v0.1.1**.

### 🐞 Bugs que existiam na versão antiga (v0.1.0)

| # | Bug | O que acontecia no jogo | Correção na v0.1.1 |
|---|-----|-------------------------|--------------------|
| 1 | **Teclas registradas tarde demais** — no Minecraft 26.3 o cliente termina de iniciar **antes** do `FMLCommonSetupEvent`, e o mod só registrava as teclas nesse setup | As teclas **C / V / B não existiam** no jogo: não apareciam em Opções → Controles e não faziam nada ao apertar | Os listeners do cliente agora são registrados **na construção do mod** (`NarutoKeys.registrarListeners()`), cedo o suficiente para o `RegisterKeyMappingsEvent` |
| 2 | **Códigos de tecla no formato antigo** — o port usava os números do 1.7.10 (67 / 86 / 66, do GLFW) | Mesmo quando registradas, as teclas viravam **F10 / "-" / F9** em vez de C / V / B | Trocado para **scancodes** do 26.3: `InputConstants.KEY_C`, `KEY_V`, `KEY_B` e `KEY_J` |

### ✨ O que mudou nesta atualização

- **Mensagem de boas-vindas no chat** ao entrar num mundo — prova visual na hora de que o mod carregou:
  `[Naruto Anime Mod] 33 jutsus ativos - combos C/V/B. A aba de conquistas (tecla L) mostra as combinações.`
- **`INSTALAR-MOD.bat`** — instalador automático para Windows: acha o jar, copia para a pasta certa
  (versão Forge normal **e/ou** modpack do TLauncher), cria a pasta `mods` se não existir e gera a pasta
  `RELATORIO/` com logs e crash-reports para diagnóstico. **Nada é apagado.**
- **Mensagens de log novas** para diagnóstico: `listeners do cliente registrados...` e `4 teclas registradas: C, V, B (jutsus) e J (menu)`.
- **Guia novo** [`docs/guias/INSTALAR-E-DIAGNOSTICAR.html`](docs/guias/INSTALAR-E-DIAGNOSTICAR.html) —
  explica também por que o mod tem ~50 KB (e por que os jars antigos de 4 MB / 20 MB / 40 MB **não** funcionam no 26.3).
- **Aviso em `referencia/`** — os jars antigos (1.7.10 / 1.20) são só fonte de consulta, não servem para jogar.
- **Provas de teste em [`docs/evidencias/`](docs/evidencias/)** — prints do mod rodando no 26.3.

### ✅ Como confirmar que está funcionando (3 sinais)

1. Tela **Mods** do menu principal: `Naruto Anime Mod 0.1.1` na lista;
2. **Ao entrar num mundo**: mensagem dourada no chat (acima);
3. **Aperte B-B-B**: aparece `» Shunshin no Jutsu` na tela (e o `Load 1900 advancements` aparece no log).

---

## 📥 Download

| Arquivo | Para que serve |
|---|---|
| [**narutoanimemod-26.3-v0.1.1.jar**](https://github.com/willianstriker/Minecraft-Project-Naruto-Anime-Mod/releases/latest) | ⭐ **É este que vai na pasta `mods/`** |
| [**NarutoAnimeMod-26.3-v0.1.1-COM-INSTALADOR.zip**](https://github.com/willianstriker/Minecraft-Project-Naruto-Anime-Mod/releases/latest) | Pacote completo: **jar + instalador `.bat` + guias** (extraia e rode o `INSTALAR-MOD.bat`) |

> Página de todas as versões: [**Releases**](https://github.com/willianstriker/Minecraft-Project-Naruto-Anime-Mod/releases)
>
> ⚠️ O `.zip` **não** vai na pasta `mods/` — o Minecraft só lê `.jar`. Extraia o zip ou baixe o jar direto.

---


## 📖 Sobre

Este projeto **porta o Naruto Anime Mod 0.9.17 (Minecraft 1.7.10)** para a versão mais recente do Minecraft, com duas mudanças centrais pedidas pelo projeto:

1. **Todos os itens de ativação de jutsu foram removidos** — nada de talismãs, pergaminhos ou papéis de natureza. Kunais, katanas, armaduras, headbands, ryo e pílulas **continuam** no mod.
2. **A ativação agora é por combos de teclas**, no estilo do *Sekwah41's Naruto Mod*: você tecla **C**, **V** e **B** na ordem do jutsu, e a **conquista correspondente mostra a combinação** necessária.

## 🎮 Como funciona

| Tecla | Valor |
|---|---|
| **C** | 1 |
| **V** | 2 |
| **B** | 3 |

- Digite a sequência e o jutsu dispara ~0,2 s depois da última tecla.
- **Jutsus de carga** (Rasengan, Chidori...): **segure a última tecla** para carregar. Soltar libera com potência proporcional. Toque rápido = versão fraca.
- Combo errado → mensagem vermelha no chat.
- O tempo de espera é configurável em `config/am-common.toml`.

### Jutsus assinatura (combos curtos)

| Combo | Jutsu | Rank | Chakra |
|---|---|:---:|---:|
| `C` | Kawarimi no Jutsu | E | 20 |
| `V` | Henge no Jutsu | E | 20 |
| `B` | Shunshin no Jutsu | D | 40 |
| `C-C` | Kage Bunshin | B | 120 |
| `C-B` | Taju Kage Bunshin | A | 300 |
| `V-C` | Katon: Goukakyuu | C | 90 |
| `V-V` | Katon: Housenka | C | 80 |
| `V-B` | Amaterasu | S | 500 |
| `B-C` | Suiton: Suidan | C | 70 |
| `B-V` | Suiton: Suirou | B | 90 |
| `B-B` | Suiton: Bakusui Shouha | A | 220 |
| `C-V-C` | Fuuton: Daitoppa | C | 70 |
| `C-V-V` ⏱ | **Rasengan** | A | 250 |
| `C-V-B` ⏱ | Rasenshuriken | S | 600 |
| `C-B-C` | Doton: Doryuusou | C | 70 |
| `C-B-V` | Doton: Doryuu Jouheki | C | 60 |
| `V-C-C` ⏱ | **Chidori** | A | 250 |
| `V-C-V` ⏱ | Raikiri | A | 300 |
| `V-C-B` ⏱ | Kirin | S | 600 |
| `V-V-C` | Mokuton: Jukai Koutan | A | 300 |
| `V-V-B` | Hyoton: Makyou Hyoushou | A | 280 |
| `V-B-C` | Shinra Tensei | S | 450 |
| `V-B-V` | Bansho Ten'in | S | 400 |
| `V-B-B` | Kamui | S | 500 |
| `B-C-C` | Sennin Mode | S | 400 |
| `B-C-V` | Bijuu Chakra Mode | S | 500 |
| `B-C-B` | Marca Amaldiçoada Nv.2 | A | 250 |
| `B-V-C` | Byakugan | A | 180 |
| `B-V-V` | Sharingan | B | 150 |
| `B-V-B` | Hachimon Tonkou | S | 450 |
| `B-B-C` | Iryo: Shosen | C | 100 |
| `B-B-V` | Souzou Saisei | S | 550 |
| `B-B-B` | Edo Tensei | S | 800 |

⏱ = jutsu de carga (segure a última tecla)

📄 **Catálogo completo** (incluindo o esquema por escola para os ~477 jutsus restantes): [`docs/pagina_conquistas.html`](docs/pagina_conquistas.html) · [`docs/catalogo_jutsus.csv`](docs/catalogo_jutsus.csv)

## 📥 Instalação

1. Instale o **Forge 26.3 (66.0.x)** na sua instância
2. Garanta que o jogo usa **Java 25** (obrigatório no 26.3)
3. Copie `dist/narutoanimemod-26.3-v0.1.1.jar` para a pasta `mods/`
4. Abra o jogo → **Opções → Controles** → categoria **Naruto Anime Mod**

> ⚠️ O arquivo `.zip` do repositório **não** vai na pasta `mods/` — só o `.jar` de dentro.

**Teste rápido:** `C` (Kawarimi) · `C-V-V` **segurando o V** (Rasengan carregado)

## 🔨 Compilando

Requer **JDK 25** e internet na primeira compilação.

```bash
./gradlew build        # gera o jar em build/libs/
./gradlew comboTest    # 15 testes do motor de combos
./gradlew runServer    # servidor de teste local
./gradlew runClient    # cliente (precisa de GPU)
```

> A **primeira** compilação leva ~30-40 min: o Forge baixa e descompila o Minecraft inteiro. Depois disso, ~10 s por build.

## 📂 Estrutura

```
src/main/java/com/mathioks/narutomod/
├── NarutoAnimeMod.java          # classe principal (@Mod("am"))
├── Config.java                  # delay do combo, chakra
├── chakra/ChakraData.java       # chakra do jogador (NBT persistente)
├── jutsu/
│   ├── ComboEngine.java         # motor de combos C/V/B (independente do MC, testável)
│   ├── JutsuTable.java          # registro dos jutsus e códigos
│   └── JutsuCaster.java         # efeitos no servidor
├── network/                     # SimpleChannel + pacotes
├── client/NarutoKeys.java       # teclas C(67) / V(86) / B(66) / J(74)
└── advancement/                 # trigger "am:cast_jutsu"

src/main/resources/
├── META-INF/mods.toml
├── assets/am/lang/{pt_br,en_us}.json
└── data/am/advancement/*.json   # 34 conquistas (combo na descrição)
```

| Pasta | Conteúdo |
|---|---|
| `docs/` | Página de conquistas, catálogo e guias |
| `tools/` | Script que gera o catálogo de itens/combos |
| `dist/` | Builds prontos para jogar |

## 🗺️ Roadmap

- [x] Ambiente Forge 26.3 + build funcionando
- [x] Motor de combos C/V/B (15/15 testes)
- [x] 33 jutsus com efeitos
- [x] Conquistas com as combinações
- [ ] HUD mostrando o combo na tela
- [ ] Tela de jutsus (tecla **J**)
- [ ] Portar os ~477 jutsus restantes (clãs, Kekkei Genkai, modos)
- [ ] Mobs, invocações e bijuus
- [ ] Armas e armaduras (os 320 itens mantidos)
- [ ] Sons e modelos do jar original

## 📜 Créditos

**Este port só existe graças ao trabalho destas pessoas — créditos obrigatórios em qualquer cópia ou redistribuição:**

| Pessoa | Contribuição |
|---|---|
| **Mathioks** | Criador do **Naruto Anime Mod** original (MC 1.7.10) — conteúdo, jutsus, itens, armas, armaduras, mobs e assets |
| **ninjaespio1000** | Criador do **Naruto Anime Mod Plus+** — modificação/expansão do mod do Mathioks |
| **Sekwah41's** | Criador do **Sekwah41's Naruto Mod** (1.20) e do **SekCLib** — referência do sistema de teclas C/V/B (licença MIT) |
| **Masashi Kishimoto** | Criador de **Naruto / Naruto Shippuden** |
| **willianstriker** | Port para Minecraft 26.3 / Forge 66, motor de combos, conquistas e interface |

## ⚖️ Licença — código aberto (MIT)

O código deste repositório é **livre**: qualquer pessoa pode **usar, copiar, modificar e redistribuir**, desde que mantenha os créditos acima. Ver [LICENSE.md](LICENSE.md).

> Projeto de fã, sem fins comerciais. Os assets originais (texturas, modelos, sons) pertencem ao Mathioks e ao Sekwah41's — procure os autores antes de reutilizá-los comercialmente.
