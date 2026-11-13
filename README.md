# Shinra Tensei - Narutoto Addon

Addon independente para **Minecraft 1.20.1 + Forge 47.x + Narutoto 0.4.1**.

## O que esta versão implementa

- Habilidade Shinra Tensei ativada pela tecla **R** (tecla configurável em Controles).
- Verificação no servidor: exige que o jogador tenha concluído o avanço `narutoto:learn_rinnegan`.
- Onda de repulsão em área, partículas, som, dano e empurrão das entidades próximas.
- Recarga de 8 segundos, validada no servidor para evitar spam de pacotes.
- Projeto Gradle e workflow `.github/workflows/build.yml` para compilar no GitHub Actions.

## Instalação

1. Instale Minecraft 1.20.1, Forge 47.x e Narutoto 0.4.1.
2. Compile este projeto ou baixe o JAR em **Actions → Build Shinra Tensei Narutoto Addon → Artifacts**.
3. Coloque o JAR gerado na pasta `mods` junto do Narutoto.
4. Entre no mundo, desbloqueie o avanço do Rinnegan e pressione **R**.

## Compilar localmente

Requer Java 17 e Gradle 8.1+:

```bash
gradle --no-daemon build
```

O resultado fica em `build/libs/`.

## Integração e compatibilidade

O JAR Shinra Tensei fornecido é um mod **Fabric para Minecraft 1.21.11** e declara licença `All-Rights-Reserved`; por isso seus binários/código/recursos não são redistribuídos nem podem ser carregados diretamente no Forge 1.20.1. Este projeto implementa uma habilidade compatível de repulsão em Forge, sem incorporar arquivos proprietários do JAR Fabric.

O Narutoto 0.4.1 já possui conteúdo próprio relacionado a Shinra Tensei. Este addon é separado e não substitui nem modifica os arquivos do Narutoto. A checagem de Rinnegan usa o avanço `narutoto:learn_rinnegan` do mod.

## Ajustes

Os valores de alcance, força, dano e recarga estão no arquivo `ShinraTenseiPacket.java` (`RADIUS`, `KNOCKBACK`, `COOLDOWN_TICKS` e `5.0F`).
