<div align="center">

# 🍥 Naruto Anime Mod — 26.3

**Port do Naruto Anime Mod (1.7.10, do Mathioks) para o Minecraft 26.3 / Forge 66**

Sistema de ativação de jutsus por **combinação de teclas C / V / B** — sem itens de talismã.

![Minecraft](https://img.shields.io/badge/Minecraft-26.3-brightgreen)
![Forge](https://img.shields.io/badge/Forge-66.0.12-orange)
![Java](https://img.shields.io/badge/Java-25-blue)
![Status](https://img.shields.io/badge/status-alfa-yellow)

</div>

---

## 📖 Sobre

Este projeto **porta o Naruto Anime Mod 0.9.17 (Minecraft 1.7.10)** para a versão mais recente do Minecraft, com duas mudanças centrais pedidas pelo projeto:

1. **Todos os itens de ativação de jutsu foram removidos** — nada de talismãs, pergaminhos ou papéis de natureza. Kunais, katanas, armaduras, headbands, ryo e pílulas **continuam** no mod.
2. **A ativação agora é por combos de teclas**, no estilo do *Naruto Anime Mod do Sekwah41*: você tecla **C**, **V** e **B** na ordem do jutsu, e a **conquista correspondente mostra a combinação** necessária.

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
3. Copie `dist/narutoanimemod-26.3-v0.1.0.jar` para a pasta `mods/`
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

| Projeto | Autor |
|---|---|
| Naruto Anime Mod 0.9.17 (1.7.10) | **Mathioks** |
| NarutoMod 1.20 — referência das teclas (MIT) | **Sekwah41** |
| Naruto / Naruto Shippuden | **Masashi Kishimoto** |

Projeto de fã, sem fins comerciais. Ver [LICENSE.md](LICENSE.md).
