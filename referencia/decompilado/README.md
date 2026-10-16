# Código decompilado (referência técnica)

Fontes obtidas decompilando os jars originais com [Vineflower](https://vineflower.org/).
Guardados aqui para consulta enquanto portamos mais sistemas para o 26.3.

| Arquivo | Origem | Por que é útil |
|---|---|---|
| `NarutoAnimeMod-1.7.10-KeyHandler.java` | Naruto Anime Mod 0.9.17 (Mathioks) | Mostra como cada jutsu era acionado por item (o que estamos substituindo por combos) |
| `NarutoMod-1.20-NarutoKeyHandler.java` | NarutoMod 1.20 (Sekwah41) | **Algoritmo de referência** do sistema de combos C/V/B (commit por atraso + carga) |
| `NarutoMod-1.20-KeyBindingTickHeld.java` | NarutoMod 1.20 (Sekwah41) | Classe auxiliar que mede quanto tempo uma tecla foi segurada |
| `NarutoMod-1.20-JutsuHandler.java` | NarutoMod 1.20 (Sekwah41) | Ponte entre o cliente e a conjuração no servidor |

> ⚠️ Estes arquivos são **material de estudo**: pertencem aos seus autores originais
> (Mathioks e Sekwah41) e não fazem parte do código compilado deste mod.

## Como decompilar outros arquivos

```bash
java -jar vineflower.jar --dgs=1 caminho/Classe.class pasta-de-saida/
```
