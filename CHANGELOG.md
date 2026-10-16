# Changelog

Todas as mudanças relevantes deste projeto são documentadas aqui.
O formato segue [Keep a Changelog](https://keepachangelog.com/pt-BR/1.1.0/).

## [Não lançado]

### Alterado
- **Licença trocada para MIT** — o código agora é aberto e qualquer pessoa pode
  copiar, modificar e redistribuir, mantendo os créditos
- **Créditos completos** adicionados ao `LICENSE.md` e ao `README.md`:
  **Mathioks** (mod original), **ninjaespio1000** (Naruto Anime Mod Plus+),
  **Sekwah41** (NarutoMod/SekCLib) e **Masashi Kishimoto** (obra Naruto)

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
