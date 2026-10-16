#!/usr/bin/env bash
# =============================================================================
#  publicar.sh — publica o Naruto Anime Mod no GitHub (push + Release)
#  Projeto: https://github.com/willianstriker/Minecraft-Project-Naruto-Anime-Mod
#
#  USO:
#     ./ferramentas/publicar.sh <pasta-do-clone>                 -> só commit + push
#     ./ferramentas/publicar.sh <pasta-do-clone> v0.1.2 "Título"  -> push + Release
#
#  O token é lido de  /home/user/.gh_token  (arquivo local, nunca vai para o repo).
#  Se a tag for informada, o script envia TODOS os arquivos de <pasta>/dist/ como
#  assets do Release.
#
#  IMPORTANTE (regra do projeto): o script NUNCA apaga arquivos do repositório.
#  Ele só adiciona/atualiza. Exclusão é sempre feita pelo dono do repositório.
# =============================================================================
set -euo pipefail

REPO_DIR="${1:-}"
TAG="${2:-}"
TITULO="${3:-}"
TOKEN_FILE="${HOME}/.gh_token"
REMOTE="github.com/willianstriker/Minecraft-Project-Naruto-Anime-Mod.git"

if [[ -z "${REPO_DIR}" ]]; then
  echo "uso: $0 <pasta-do-clone> [tag] [titulo]" >&2
  exit 2
fi
if [[ ! -d "${REPO_DIR}/.git" ]]; then
  echo "ERRO: '${REPO_DIR}' não é um clone git. Clone antes:" >&2
  echo "  git clone https://<TOKEN>@${REMOTE} ${REPO_DIR}" >&2
  exit 2
fi
if [[ ! -f "${TOKEN_FILE}" ]]; then
  echo "ERRO: token não encontrado em ${TOKEN_FILE}" >&2
  echo "     salve o PAT (fine-grained, com Contents+Releases: read/write) em ${TOKEN_FILE}" >&2
  exit 2
fi

TOKEN="$(cat "${TOKEN_FILE}")"
cd "${REPO_DIR}"

# Se o 2o argumento nao for uma tag (v1.2.3), trata como mensagem de commit.
if [[ -n "${TAG}" && ! "${TAG}" =~ ^v[0-9]+\.[0-9]+ ]]; then
  TITULO="${TAG}"
  TAG=""
fi

# --- commit + push ----------------------------------------------------------
git config user.name  "willianstriker" >/dev/null
git config user.email "willianstriker@users.noreply.github.com" >/dev/null

if [[ -n "$(git status --porcelain)" ]]; then
  git add -A
  MSG="${TITULO:-chore: atualizacao automatica}"
  git commit -q -m "${MSG}"
  echo "[ok] commit: $(git log --oneline -1)"
else
  echo "[i] nada novo para commitar"
fi

git push -q "https://${TOKEN}@${REMOTE}" main
echo "[ok] push concluído ($(git log --oneline -1))"

# --- Release (opcional) -----------------------------------------------------
if [[ -z "${TAG}" ]]; then
  echo "[i] sem tag informada — só push. Para publicar Release: $0 ${REPO_DIR} v0.1.2 \"Título\""
  exit 0
fi

api="https://api.github.com/repos/willianstriker/Minecraft-Project-Naruto-Anime-Mod"

# descrição da release: usa o arquivo dist/NOTAS-DA-VERSAO.md se existir
NOTES_FILE="dist/NOTAS-DA-VERSAO.md"
python3 - "$TAG" "$TITULO" "$NOTES_FILE" "$TOKEN" <<'PY'
import json, pathlib, subprocess, sys
tag, titulo, notes_file, token = sys.argv[1:5]
body = pathlib.Path(notes_file).read_text(encoding="utf-8") if pathlib.Path(notes_file).exists() else f"Versão {tag}"
payload = {"tag_name": tag, "target_commitish": "main",
           "name": titulo or tag, "body": body, "draft": False, "prerelease": False}
p = pathlib.Path("/tmp/_rel_payload.json"); p.write_text(json.dumps(payload), encoding="utf-8")
out = subprocess.run(["curl","-sS","-X","POST",
    "-H", f"Authorization: Bearer {token}",
    "-H", "Accept: application/vnd.github+json",
    "--data", f"@{p}", f"https://api.github.com/repos/willianstriker/Minecraft-Project-Naruto-Anime-Mod/releases"],
    capture_output=True, text=True).stdout
d = json.loads(out)
if "id" not in d:
    print("[erro] ao criar release:", json.dumps(d)[:400]); sys.exit(1)
print("[ok] release criada:", d["html_url"])
pathlib.Path("/tmp/_rel_id").write_text(str(d["id"]))
PY

REL_ID="$(cat /tmp/_rel_id)"
shopt -s nullglob
for f in dist/*; do
  [[ -f "$f" ]] || continue
  nome="$(basename "$f")"
  echo "[i] enviando asset: ${nome}"
  curl -sS -X POST \
    -H "Authorization: Bearer ${TOKEN}" \
    -H "Accept: application/vnd.github+json" \
    -H "Content-Type: application/octet-stream" \
    --data-binary "@${f}" \
    "https://uploads.github.com/repos/willianstriker/Minecraft-Project-Naruto-Anime-Mod/releases/${REL_ID}/assets?name=${nome}" \
    | python3 -c "import json,sys; d=json.load(sys.stdin); print('    ->', d.get('state'), d.get('browser_download_url'))"
done
rm -f /tmp/_rel_payload.json /tmp/_rel_id
echo "[ok] Release ${TAG} publicada."
