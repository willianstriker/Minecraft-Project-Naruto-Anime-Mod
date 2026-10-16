# Naruto Anime Mod — versão 26.3 (Forge)

Port do **Naruto Anime Mod 0.9.17 (1.7.10, do Mathioks)** para **Minecraft 26.3 / Forge 66.0.12**, com o sistema de ativação de jutsus refeito no estilo **Sekwah41's (Sekwah41's Naruto Mod 1.20)**.

---

## ✅ O que já está pronto e testado

| Item | Status |
|---|---|
| Ambiente Forge 26.3 (Java 25, Gradle 9.7.1, ForgeGradle 7) | ✅ funcionando |
| **mod compilado** (`narutoanimemod-26.3-v0.1.0.jar`) | ✅ build OK |
| **Teste em servidor 26.3 real** | ✅ "Done (0.674s)!" com o mod carregado |
| **33 jutsus registrados** no sistema de combos | ✅ (log do servidor confirma) |
| **34 conquistas** carregadas (root + 33 jutsus) | ✅ (1900 = 1866 vanilla + 34) |
| Motor de combos C/V/B | ✅ **15/15 testes passando** |
| Catálogo dos 830 itens do mod original | ✅ 510 p/ deletar · 320 p/ manter |
| Página de conquistas (HTML) | ✅ `pagina_conquistas.html` |

---

## 🎮 O sistema de teclas (igual ao Sekwah41's)

- **C** = 1 · **V** = 2 · **B** = 3
- Digite a sequência e o jutsu dispara ~0,2 s depois (configurável em `config/am-common.toml`)
- Jutsus **carregados** (Rasengan, Chidori, Raikiri, Kirin, Rasenshuriken): **segure a última tecla** para carregar → soltar libera no máximo. Toque rápido = versão fraca.
- Combo errado → mensagem vermelha no chat

### Jutsus assinatura (combos curtos)

| Combo | Jutsu | Rank | Chakra |
|---|---|---|---|
| C | Kawarimi no Jutsu | E | 20 |
| V | Henge no Jutsu | E | 20 |
| B | Shunshin no Jutsu | D | 40 |
| C-C | Kage Bunshin | B | 120 |
| C-B | Taju Kage Bunshin | A | 300 |
| V-C | Katon: Goukakyuu | C | 90 |
| V-V | Katon: Housenka | C | 80 |
| V-B | Amaterasu | S | 500 |
| B-C | Suiton: Suidan | C | 70 |
| B-V | Suiton: Suirou | B | 90 |
| B-B | Suiton: Bakusui Shouha | A | 220 |
| C-V-C | Fuuton: Daitoppa | C | 70 |
| C-V-V ⏱ | **Rasengan** (carrega) | A | 250 |
| C-V-B ⏱ | Rasenshuriken (carrega) | S | 600 |
| C-B-C | Doton: Doryuusou | C | 70 |
| C-B-V | Doton: Doryuu Jouheki | C | 60 |
| V-C-C ⏱ | **Chidori** (carrega) | A | 250 |
| V-C-V ⏱ | Raikiri (carrega) | A | 300 |
| V-C-B ⏱ | Kirin (carrega) | S | 600 |
| V-V-C | Mokuton: Jukai Koutan | A | 300 |
| V-V-B | Hyoton: Makyou Hyoushou | A | 280 |
| V-B-C | Shinra Tensei | S | 450 |
| V-B-V | Bansho Ten'in | S | 400 |
| V-B-B | Kamui | S | 500 |
| B-C-C | Sennin Mode | S | 400 |
| B-C-V | Bijuu Chakra Mode | S | 500 |
| B-C-B | Marca Amaldiçoada Nv.2 | A | 250 |
| B-V-C | Byakugan | A | 180 |
| B-V-V | Sharingan | B | 150 |
| B-V-B | Hachimon Tonkou | S | 450 |
| B-B-C | Iryo: Shosen | C | 100 |
| B-B-V | Souzou Saisei | S | 550 |
| B-B-B | Edo Tensei | S | 800 |

*(⏱ = jutsu de carga — segure a última tecla)*

Os outros ~477 jutsus do catálogo original usam o **esquema por escola** (prefixo de 2 teclas + técnica) — tabela completa em `catalogo_jutsus.csv` e `pagina_conquistas.html`.

---

## 📥 Como instalar (TLauncher)

1. Instale **Forge 26.3 (66.0.x)** na sua instância do TLauncher
   - Selecione a versão 26.3 → "Install Forge" (ou baixe em files.minecraftforge.net)
2. Garanta que o jogo está usando **Java 25** (o 26.3 exige)
   - TLauncher → Configurações → Java → apontar para o JDK 25
3. Copie `narutoanimemod-26.3-v0.1.0.jar` para a pasta `mods/` da instância
4. Abra o jogo → **Opções → Controles** → procure a categoria **Naruto Anime Mod** e confira as teclas C, V, B (e J = lista futura)
5. Menu de **Conquistas** → aba **Naruto Anime Mod** → cada jutsu mostra a combinação na descrição

> Teste rápido: bata `C` e veja o Kawarimi, `C-V-V` **segurando o V** para carregar o Rasengan.

---

## 📂 Estrutura do projeto

```
NarutoAnimeMod-26.3/
├── build.gradle, settings.gradle, gradlew, gradle.properties
├── src/main/java/com/mathioks/narutomod/
│   ├── NarutoAnimeMod.java          # classe principal (@Mod("am"))
│   ├── Config.java                  # delay do combo, chakra
│   ├── chakra/ChakraData.java       # chakra do jogador
│   ├── jutsu/ComboEngine.java       # motor de combos C/V/B (testado)
│   ├── jutsu/JutsuTable.java        # 33 jutsus + códigos de combo
│   ├── jutsu/JutsuCaster.java       # efeitos no servidor
│   ├── network/ModNetwork.java      # canal SimpleChannel
│   ├── network/CastJutsuPacket.java # cliente → servidor
│   ├── client/NarutoKeys.java       # teclas C/V/B (67/86/66)
│   └── advancement/                 # trigger "am:cast_jutsu"
├── src/main/resources/
│   ├── META-INF/mods.toml
│   ├── assets/am/lang/{en_us,pt_br}.json
│   └── data/am/advancement/*.json   # 34 conquistas
└── src/test/java/.../ComboEngineTest.java
```

### Comandos úteis

```bash
./gradlew build          # gera o jar em build/libs/
./gradlew runServer      # sobe um servidor de teste
./gradlew runClient      # abre o cliente (precisa de GPU)
./gradlew comboTest      # roda os 15 testes do motor de combos
```

---

## 🔜 Próximos passos (fases seguintes)

1. **HUD do combo** — mostrar C-V-… na tela enquanto digita (RegisterGuiLayersEvent)
2. **Tela de jutsus (tecla J)** — lista visual com todos os combos dentro do jogo
3. **Portar o resto do catálogo**: os ~477 jutsus restantes, clãs, Kekkei Genkai
4. **Entidades/mobs** do mod original (bijuus, invocações, NPCs)
5. **Armas e armaduras** (os 320 itens mantidos) — kunai, katanas, robes, headbands
6. **Sons e modelos** (os assets do jar original são reaproveitáveis — 3.956 arquivos)
7. **Modo ninja / nível de Ninja** e o sistema de pergaminhos de habilidade
