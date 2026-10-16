# Guias de instalação e solução de problemas

| Guia | Quando usar |
|---|---|
| [`INSTALAR-E-DIAGNOSTICAR.html`](INSTALAR-E-DIAGNOSTICAR.html) | **COMECE AQUI** — instalador automático `.bat` + relatório de diagnóstico + explicação do tamanho do jar |
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

> ⚠️ Use **somente** o `narutoanimemod-26.3-*.jar` (~50 KB) da página **Releases**. Os jars de `referencia/` (1.7.10 / 1.20) são só fontes de consulta e **não** funcionam no 26.3.

## Instalador automático

`INSTALAR-MOD.bat` (na raiz do repositório e dentro do ZIP do Release): copia o jar para a pasta certa (versão normal **e/ou** modpack), cria a pasta `mods` se não existir e gera a pasta `RELATORIO/` com logs e crash-reports para enviar no diagnóstico. Nada é apagado.

## Novidades da v0.1.1

- **Corrigido:** as teclas C/V/B não eram registradas no cliente 26.3 (o mod registrava tarde demais) e usavam códigos de tecla antigos (viravam F10 / "-" / F9). Veja o [CHANGELOG](../../CHANGELOG.md).
- **Novo:** mensagem de boas-vindas no chat ao entrar no mundo (prova de que o mod carregou).
- **Novo:** `INSTALAR-MOD.bat` + este guia.
- **Novo:** prints de teste em [`docs/evidencias/`](../evidencias/).
