# Guias de instalação e solução de problemas

| Guia | Quando usar |
|---|---|
| [`TLAUNCHER-QUAL-VERSAO-ABRIR.html`](TLAUNCHER-QUAL-VERSAO-ABRIR.html) | Dúvida sobre qual versão abrir (normal x Forge x Modpack) |
| [`CORRECAO-MODPACK-TLAUNCHER.html`](CORRECAO-MODPACK-TLAUNCHER.html) | **Mod não aparece** e você usa um Modpack criado no TLauncher |
| [`DIAGNOSTICO-MOD-NAO-FUNCIONA.html`](DIAGNOSTICO-MOD-NAO-FUNCIONA.html) | Mod não aparece (guia geral, com os 4 testes) |
| [`GUIA-PASSO-A-PASSO.html`](GUIA-PASSO-A-PASSO.html) | Publicar o projeto no GitHub pelo navegador |
| [`PUBLICAR-NO-GITHUB.md`](PUBLICAR-NO-GITHUB.md) | Publicar com git (avançado) |

## Resumo rápido: onde colocar o mod

| Como você abre o jogo | Onde vai o `.jar` |
|---|---|
| Versão **Forge** direta (sem modpack) | `.minecraft/mods/` |
| **Modpack** criado no TLauncher | `.minecraft/versions/<nome do modpack>/mods/` |

> ⚠️ A pasta `.minecraft/mods` **não** é lida pelos modpacks do TLauncher — cada modpack tem a própria pasta em `versions/<nome>/`.
