# Como compilar

## Requisitos
- **JDK 25** (obrigatório — o Minecraft 26.3 roda em Java 25)
- Internet na primeira compilação (baixa o Forge 26.3 e o Minecraft)

## Passos

```bash
# Linux / macOS
./gradlew build

# Windows
gradlew.bat build
```

O jar final sai em `build/libs/narutoanimemod-1.0.0.jar`.

> ⚠️ A **primeira** compilação demora ~30-40 min: o Forge baixa e descompila o
> Minecraft inteiro. Depois disso, cada build leva ~10 segundos.

## Testes e execução

```bash
./gradlew comboTest    # 15 testes do motor de combos C/V/B
./gradlew runServer    # servidor de teste local (aceita o mod)
./gradlew runClient    # abre o cliente (precisa de GPU)
```

## Memória

Em ambientes com pouca RAM (menos de 4 GB), ajuste `gradle.properties`:

```properties
org.gradle.jvmargs=-Xmx1200m -XX:MaxMetaspaceSize=512m
```

Se a decompilação do Forge falhar por falta de memória, crie área de swap:

```bash
sudo fallocate -l 8G /swapfile && sudo chmod 600 /swapfile
sudo mkswap /swapfile && sudo swapon /swapfile
```
