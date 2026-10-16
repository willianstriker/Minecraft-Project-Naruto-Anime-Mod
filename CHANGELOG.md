# Changelog

Todas as mudanças relevantes deste projeto são documentadas aqui.
O formato segue [Keep a Changelog](https://keepachangelog.com/pt-BR/1.1.0/).

## [0.1.1] — 2026-10-16

### 🐞 Corrigido
- **BUG CRÍTICO: teclas C/V/B nunca eram registradas no cliente 26.3.**
  O mod registrava os listeners do cliente dentro do `FMLCommonSetupEvent`, mas no Minecraft 26.3 o
  cliente termina de iniciar **antes** desse evento — logo o `RegisterKeyMappingsEvent` já havia passado
  e as teclas não existiam no jogo (não apareciam em Opções → Controles e não faziam nada).
  Agora `NarutoKeys.registrarListeners()` é chamado na **construção do mod**.
- **BUG: códigos de tecla do formato antigo (GLFW) em vez de scancodes do 26.3.**
  O port usava `67 / 86 / 66` (C/V/B no 1.7.10), que no 26.3 correspondem a **F10 / "-" / F9**.
  Trocado por `InputConstants.KEY_C`, `KEY_V`, `KEY_B` e `KEY_J`.
- **BUG: `KeyMapping.Category` era criada dentro do setup**, podendo registrar a categoria depois que o
  jogo já havia montado a tela de controles. Agora é criada sob demanda com segurança.

### ✨ Adicionado
- **Mensagem de boas-vindas no chat** ao entrar em um mundo (`33 jutsus ativos - combos C/V/B...`) —
  serve como prova visual de que o mod carregou.
- **`INSTALAR-MOD.bat`** — instalador para Windows: localiza o jar, copia para `mods` da versão normal
  e/ou do modpack do TLauncher (criando a pasta se preciso) e gera `RELATORIO/` com logs e crash-reports.
  Não apaga nada.
- **Logs de diagnóstico**: `listeners do cliente registrados na construcao do mod` e
  `4 teclas registradas: C, V, B (jutsus) e J (menu)`.
- **`docs/guias/INSTALAR-E-DIAGNOSTICAR.html`** — instalador + diagnóstico + explicação do tamanho do jar.
- **`referencia/AVISO-JARS-ANTIGOS.md`** — alerta de que os jars de 1.7.10/1.20 são inúteis para jogar no 26.3.
- **`docs/evidencias/`** — prints de teste do mod rodando em Minecraft 26.3 (mensagem no chat, combo B-B-B
  disparando *Shunshin no Jutsu*, aba de conquistas do mod).
- **`src/test/java/.../ComboEngineTest.java`** adicionado ao repositório (15 testes do motor de combos,
  rodados com `./gradlew comboTest`).

### 🔧 Alterado
- `src/test/java/CastJutsuPacket`... não alterado; versão do mod (`mods.toml` e `build.gradle`) subiu para **0.1.1**.
- Documentação e README com a seção **"Nova atualização"** e os bugs da versão anterior descritos.

## [Não lançado]

### Alterado
- **Licença trocada para MIT** — o código agora é aberto e qualquer pessoa pode
  copiar, modificar e redistribuir, mantendo os créditos
- **Créditos completos** adicionados ao `LICENSE.md` e ao `README.md`:
  **Mathioks** (mod original), **ninjaespio1000** (Naruto Anime Mod Plus+),
  **Sekwah41's** (NarutoMod/SekCLib) e **Masashi Kishimoto** (obra Naruto)

### Adicionado
- `referencia/decompilado/` — código decompilado dos mods originais para consulta técnica
- `docs/guias/` — guias de publicação e passo a passo
- `dist/NarutoAnimeMod-26.3-v0.1.0.zip` — pacote pronto para o jogador (mod + documentação)

## [0.1.0] — 2026-10-15

### Adicionado
- **Port para Minecraft 26.3 / Forge 66.0.12** (Java 25, ForgeGradle 7, Gradle 9.7.1)
- **Motor de combos C/V/B** (`ComboEngine`) — commit por atraso + jutsus de carga por hold,
  independente da API do Minecraft e coberto por **15 testes automatizados**
- **33 jutsus assinatura** com efeitos reais: dano, empurrão, buffs, teleporte, invocação de
  projétil (Rasengan, Chidori, Kirin, Amaterasu, Shinra Tensei, Kamui, modos Sábio/Bijuu, etc.)
- **Sistema de chakra** do jogador (NBT persistente, custo por jutsu, regeneração configurável)
- **34 conquistas** (`data/am/advancement/`) com a **combinação de teclas na descrição**
- **Canal de rede** (`SimpleChannel`) cliente → servidor para conjurar jutsus
- **Trigger de conquista** customizado `am:cast_jutsu`
- **Traduções** pt-BR e en-US
- **Config** (`am-common.toml`): delay do combo, chakra máximo, taxa de regeneração
- **Catálogo** dos 830 itens do mod 1.7.10 classificado: 510 de ativação (removidos) e
  320 de equipamento/consumível (mantidos)
- **Página de conquistas** em HTML com todos os combos

### Removido
- Itens de ativação de jutsu (talismãs, pergaminhos, papéis de natureza, amostras de DNA)
- Dependência de itens para aprender/conjurar jutsus

### Notas técnicas
- Validado em servidor 26.3 real: `Done (0.674s)!` com o mod carregado
- Log de boot: `33 jutsus ativos no sistema de combos C/V/B`
- 1900 conquistas carregadas (1866 vanilla + 34 do mod)
