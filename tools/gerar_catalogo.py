#!/usr/bin/env python3
"""
Naruto Anime Mod 26.3 - Catalogo de jutsus e combinacoes de teclas (C / V / B)

Regra de design:
  * Jutsus ASSINATURA  -> combos curtos (2-3 teclas), memoraveis
  * DEMAIS jutsus      -> esquema por ESCOLA: prefixo (2 teclas) + tecnica (1-3 teclas)
                          3 + 9 + 27 = 39 jutsus por escola, 9 escolas = 351 slots

Saidas:
  catalogo_jutsus.csv   - planilha completa (abre no Excel)
  jutsus.json           - dados para o mod (data-driven)
  pagina_conquistas.html- pagina de conquistas / lista de combos (visual)
"""
import csv, html, json, re
from pathlib import Path

BASE = Path("/home/user")
LANG = BASE / "repo_original/x1710/assets/tm/lang/en_US.lang"
T = {"1": "C", "2": "V", "3": "B"}

def cstr(combo: str) -> str:
    return "-".join(T[c] for c in combo)

# ------------------------------------------------------------------ 1) equipamento / consumivel que CONTINUA no mod
KEEP_PAT = re.compile(
    r"kunai|shuriken|senbon|sword|blade|katana|axe|pickaxe|shovel|hoe|scythe|shield|rod|"
    r"armor|armour|helmet|chestplate|leggings|boots|robe|cloak|vest|flak|headband|bandana|"
    r"\bpill\b|beer|sake|wine|wodka|vodka|rhum|whiskey|liquor|shot|mug|pizza|ramen|ryo|"
    r"ingot|paper bomb|smoke bomb|poison bomb|food|scroll\b(?! of)|chakra scroll|chakra restore|"
    r"military pill|soldier pill|blood pill|kunai paper|explosive tag|sandals|pants|trousers|"
    r"collar|gloves|shirt|jacket\b|kimono|haori|scarf|belt|cape|sash|mask\b|kimono",
    re.I,
)

def classify(name: str) -> str:
    if re.search(r"bijuu bomb|tailed beast bomb|rasenshuriken", name, re.I):
        return "DELETAR"
    if KEEP_PAT.search(name) and not re.search(r"release|jutsu|technique|style|mode|clone|sharingan", name, re.I):
        return "MANTER"
    return "DELETAR"

# ------------------------------------------------------------------ 2) categoria
CATS = (
    ("Doujutsu",        r"sharingan|rinnegan|byakugan|tenseigan|ketsuryugan|jougan|mangekyou|"
                        r"amaterasu|tsukuyomi|kamui|susano|almighty|limbo|amenotejikara|kagutsuchi|"
                        r"shinra|bansho|chibaku|izanagi|izanami|coercion|eye"),
    ("Katon (Fogo)",    r"fire release|great fireball|phoenix|inferno|flame|scorch release|"
                        r"fire bullet|fire running|matatabi|ash stone|congealing|burning"),
    ("Suiton (Agua)",   r"water release|water bullet|water prison|water shark|hiding in mist|"
                        r"waterfall|water arm|water colliding|water dragon|water clone|mist"),
    ("Fuuton (Vento)",  r"wind release|rasengan|vacuum|breakthrough|air bullet|great slicing|"
                        r"rasen"),
    ("Doton (Terra)",   r"earth release|sand |sand\b|rock|mud|earth spear|burial|tsunami|"
                        r"quicksand|earth wall|earth dragon"),
    ("Raiton (Raio)",   r"lightning|chidori|raikiri|kirin|electric|black lightning|thunder|"
                        r"lightning armor|lariat|storm release|black hunting|laser circus"),
    ("Kekkei Genkai",   r"wood release|mokuton|lava release|ice release|hyoton|boil release|"
                        r"steel release|explosion release|magnet release|crystal release|dust release|"
                        r"swift release|kaguya|bone|shikotsumyaku|ice mirror|ice prison|ice needles|"
                        r"nativity|vine|deep forest|explosive clay|crystal|steel|jinton"),
    ("Modo / Transformacao", r"mode|sage|sennin|jinchuriki|tailed beast|bijuu|six paths|curse|"
                        r"eight gates|chakra mode|cloak|transformation|hashirama|ashura|zetsu|hidan|"
                        r"otsutsuki|rez|version|kcm|berserk|sagemode|mark"),
    ("Cla Hiden",       r"nara|yamanaka|akimichi|inuzuka|aburame|hyuga|uchiha|senju|hoshigaki|"
                        r"hiden|clan|kikaichu|shikotsu|gentle fist|eight trigrams|rotation|"
                        r"shadow imitation|mind body|passing fang|butterfly"),
    ("Iryo Ninjutsu",   r"medical|healing|iryo|wound|delicate illness|body pathway|rebirth|"
                        r"strength of a hundred|nervous system|scalpel|mythical"),
    ("Genjutsu",        r"genjutsu|illusion|bringer of darkness|mind|coercion|bind|moon|"
                        r"demonic|fool|crow"),
    ("Kuchiyose (Invocacao)", r"summon|invoc|edo tensei|puppet"),
)

def category(name: str) -> str:
    n = name.lower()
    for cat, pat in CATS:
        if re.search(pat, n):
            return cat
    return "Ninjutsu (Outros)"

# ------------------------------------------------------------------ 3) jutsus assinatura (combos curtos)
# (combo, nome no lang, nome PT-BR, rank, descricao)
ASSINATURA = [
    ("1",    "Substitution Jutsu",             "Kawarimi no Jutsu",          "E",  "Troca de lugar com um tronco. Esquiva automatica."),
    ("2",    "Transformation Jutsu",           "Henge no Jutsu",             "E",  "Copia a aparencia de um mob ou jogador."),
    ("3",    "Body Flicker Technique",         "Shunshin no Jutsu",          "D",  "Dash instantaneo na direcao da mira."),
    ("12",   "Kage Bunshin no Jutsu",          "Kage Bunshin no Jutsu",      "B",  "Cria um clone de sombra que luta por voce."),
    ("13",   "Multiple Shadow Clones",         "Taju Kage Bunshin",          "A",  "Varios clones de sombra de uma vez."),
    ("21",   "Great Fireball Jutsu",           "Katon: Goukakyuu",           "C",  "Bola de fogo em linha reta."),
    ("22",   "Phoenix Flower Jutsu",           "Katon: Housenka",            "C",  "Rajada de bolas de fogo em leque."),
    ("23",   "Amaterasu",                      "Amaterasu",                  "S",  "Chama negra que nao se apaga."),
    ("31",   "Water Bullet Jutsu",             "Suiton: Suidan",             "C",  "Projetil de agua."),
    ("32",   "Water Prison Jutsu",             "Suiton: Suirou",             "B",  "Prisao de agua no alvo."),
    ("33",   "Exploding Water Wave",           "Suiton: Bakusui Shouha",     "A",  "Onda gigante que arrasta inimigos."),
    ("121",  "Wind Style: Great Breakthrough", "Fuuton: Daitoppa",           "C",  "Rajada que arremessa o alvo longe."),
    ("122",  "Rasengan",                       "Rasengan",                   "A",  "Esfera espiral de chakra (corpo a corpo)."),
    ("123",  "Rasenshuriken",                  "Rasenshuriken",              "S",  "Shuriken de vento, dano em area."),
    ("131",  "Earth Style: Earth Spear",       "Doton: Doryuusou",           "C",  "Estacas de terra saem do chao."),
    ("132",  "Earth Style: Mud Wall",          "Doton: Doryuu Jouheki",      "C",  "Parede de terra para defesa."),
    ("211",  "Chidori",                        "Chidori",                    "A",  "Mao de raio que perfura."),
    ("212",  "Lightning Blade",                "Raikiri",                    "A",  "Corte de raio (forma aperfeicoada)."),
    ("213",  "Kirin",                          "Kirin",                      "S",  "Raio desce do ceu numa area."),
    ("221",  "Wood Style: Nativity",           "Mokuton: Jukai Koutan",      "A",  "Faz a floresta brotar."),
    ("223",  "Ice Style: Demonic Ice Mirrors", "Hyoton: Makyou Hyoushou",    "A",  "Prisao de espelhos de gelo."),
    ("231",  "Rinnegan: Almighty Push",        "Shinra Tensei",              "S",  "Empurra tudo ao redor."),
    ("232",  "Rinnegan: Almighty Pull",        "Bansho Ten'in",              "S",  "Atrai um alvo ate voce."),
    ("233",  "Kamui",                          "Kamui",                      "S",  "Teletransporte dimensional."),
    ("311",  "Sage Mode",                      "Sennin Mode",                "S",  "Modo Sabio: atributos e sensing."),
    ("312",  "Tailed Beast Chakra Mode",       "Bijuu Chakra Mode",          "S",  "Manto de chakra da bijuu."),
    ("313",  "Curse Mark Stage 2",             "Marca Amaldicoada Niv.2",    "A",  "Transformacao Juugo."),
    ("321",  "Byakugan",                       "Byakugan",                   "A",  "Visao 360 e deteccao de chakra."),
    ("322",  "Sharingan",                      "Sharingan",                  "B",  "Copia movimentos e preve ataques."),
    ("323",  "Eight Inner Gates",              "Hachimon Tonkou",            "S",  "Oito Portoes: poder bruto."),
    ("331",  "Healing Jutsu",                  "Iryo: Shosen Jutsu",         "C",  "Cura a si mesmo e aliados."),
    ("332",  "Creation Rebirth",               "Souzou Saisei",              "S",  "Regeneracao total temporaria."),
    ("333",  "Edo Tensei",                     "Edo Tensei",                 "S",  "Reanima ninjas para lutar."),
]

# ------------------------------------------------------------------ 4) escolas (prefixo) para TODOS os outros jutsus
ESCOLAS = [
    ("11", "Ninjutsu Básico",       "Clones, substituição, transformação, shunshin"),
    ("12", "Katon — Fogo",          "Elemento Fogo"),
    ("13", "Suiton — Água",         "Elemento Água"),
    ("21", "Fuuton — Vento",        "Elemento Vento"),
    ("22", "Doton — Terra",         "Elemento Terra + Areia"),
    ("23", "Raiton — Raio",         "Elemento Raio + Tempestade"),
    ("31", "Doujutsu e Kekkei Genkai", "Sharingan, Rinnegan, Byakugan, Mokuton, Hyoton..."),
    ("32", "Clãs e Hiden",          "Nara, Yamanaka, Akimichi, Inuzuka, Aburame, Hyuuga..."),
    ("33", "Modos, Médico e Genjutsu", "Modos Sábio/Bijuu, Iryo Ninjutsu, Genjutsu, Invocações"),
]

def tecnica_sufixo(i: int) -> str:
    """1-3 teclas dentro da escola: 3 + 9 + 27 = 39 slots."""
    if i < 3:                       # 1 tecla
        return str(i + 1)
    i -= 3
    if i < 9:                       # 2 teclas
        return str(i // 3 + 1) + str(i % 3 + 1)
    i -= 9
    return str(i // 9 + 1) + str((i // 3) % 3 + 1) + str(i % 3 + 1)   # 3 teclas

# ------------------------------------------------------------------ 5) match categoria -> escola
CAT2ESCOLA = {
    "Ninjutsu (Outros)": "11", "Doujutsu": "31", "Katon (Fogo)": "12", "Suiton (Agua)": "13",
    "Fuuton (Vento)": "21", "Doton (Terra)": "22", "Raiton (Raio)": "23",
    "Kekkei Genkai": "31", "Cla Hiden": "32", "Iryo Ninjutsu": "33",
    "Modo / Transformacao": "33", "Genjutsu": "33", "Kuchiyose (Invocacao)": "33",
}

def main():
    itens, seen = [], set()
    for line in LANG.read_text(encoding="utf-8", errors="ignore").splitlines():
        m = re.match(r"^item\.([\w\.\-]+)\.name=(.+)$", line.strip())
        if not m or m.group(2).strip() in seen:
            continue
        seen.add(m.group(2).strip())
        nome = m.group(2).strip()
        itens.append({"classe": m.group(1), "nome": nome,
                      "acao": classify(nome), "categoria": category(nome)})

    assin = {a[1].lower(): a for a in ASSINATURA}
    contadores = {}
    for it in itens:
        a = assin.get(it["nome"].lower())
        if a:
            it.update(combo=a[0], combo_teclas=cstr(a[0]), rank=a[3],
                      descricao=a[4], v1=True, escola="Assinatura", nome_pt=a[2])
        else:
            it.update(combo="", combo_teclas="", rank="", descricao="", v1=False,
                      escola="", nome_pt=it["nome"])

    # escola + tecnica para os demais itens de ativacao
    for it in sorted([i for i in itens if i["acao"] == "DELETAR" and not i["v1"]],
                     key=lambda x: (CAT2ESCOLA.get(x["categoria"], "11"), x["nome"])):
        esc = CAT2ESCOLA.get(it["categoria"], "11")
        n = contadores.get(esc, 0)
        if n >= 39:
            it["escola"] = "Overflow"
            continue
        contadores[esc] = n + 1
        sufixo = tecnica_sufixo(n)
        it["combo"] = esc + sufixo
        it["combo_teclas"] = cstr(it["combo"])
        it["escola"] = dict((e[0], e[1]) for e in ESCOLAS).get(esc, "?")

    itens.sort(key=lambda r: (r["acao"] != "DELETAR", r["escola"], r["combo_teclas"], r["nome"]))

    with open(BASE / "catalogo_jutsus.csv", "w", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=["nome", "nome_pt", "acao", "categoria", "escola",
                                          "combo_teclas", "rank", "v1", "classe", "descricao"],
                           extrasaction="ignore")
        w.writeheader(); w.writerows(itens)

    (BASE / "jutsus.json").write_text(json.dumps({
        "mod": "Naruto Anime Mod — 26.3",
        "teclas": T, "escolas": [{"prefixo": cstr(e[0]), "nome": e[1], "desc": e[2]} for e in ESCOLAS],
        "assinatura": [{"combo": cstr(a[0]), "nome": a[1], "nome_pt": a[2], "rank": a[3], "desc": a[4]} for a in ASSINATURA],
        "itens": itens,
    }, ensure_ascii=False, indent=1), encoding="utf-8")

    d = sum(1 for r in itens if r["acao"] == "DELETAR"); k = len(itens) - d
    print(f"Itens: {len(itens)} | DELETAR {d} | MANTER {k} | assinatura {len(ASSINATURA)}")
    print("Slots usados por escola:", {dict((e[0], e[1]) for e in ESCOLAS)[k2]: v
                                       for k2, v in sorted(contadores.items())})

    gerar_html(itens)

# ------------------------------------------------------------------ 6) pagina de conquistas
def gerar_html(itens):
    assin = [i for i in itens if i["v1"]]
    outros = [i for i in itens if i["acao"] == "DELETAR" and not i["v1"] and i["combo"]]
    rank_cor = {"E": "#9e9e9e", "D": "#4caf50", "C": "#2196f3", "B": "#9c27b0", "A": "#ff9800", "S": "#f44336"}

    def card(it, mostrar_escola=False):
        rank = it.get("rank") or ""
        badge = (f'<span style="background:{rank_cor.get(rank,"#555")};color:#fff;font-size:10px;'
                 f'padding:2px 6px;border-radius:8px;margin-left:6px">{rank}</span>') if rank else ""
        esc = (f'<div style="color:#8a8a8a;font-size:10px;margin-top:2px">{html.escape(it.get("escola",""))}</div>'
               if mostrar_escola else "")
        return f'''<div style="background:#1e1e24;border:1px solid #33333d;border-radius:10px;padding:10px 12px;margin:6px 0;display:flex;align-items:center;gap:12px">
  <div style="min-width:118px;font-family:ui-monospace,monospace;font-size:15px;font-weight:700;color:#ffb347;letter-spacing:1px">{it["combo_teclas"]}</div>
  <div style="flex:1"><div style="color:#f2f2f2;font-size:13px;font-weight:600">{html.escape(it["nome_pt"])}{badge}</div>
    <div style="color:#9a9aa5;font-size:11px;margin-top:2px">{html.escape(it["descricao"] or it["nome"])}</div>{esc}</div>
</div>'''

    escolas_html = ""
    for pref, nome, desc in ESCOLAS:
        grupo = [i for i in outros if i["escola"] == nome]
        if not grupo:
            continue
        escolas_html += (f'<h3 style="color:#e6e6e6;font-size:15px;margin:18px 0 6px">{html.escape(nome)} '
                         f'<span style="color:#ffb347;font-family:ui-monospace,monospace">{cstr(pref)} — …</span>'
                         f'<span style="color:#777;font-size:11px;font-weight:400"> ({desc})</span></h3>')
        escolas_html += "".join(card(i, False) for i in grupo[:12])
        if len(grupo) > 12:
            escolas_html += (f'<div style="color:#777;font-size:11px;padding:4px 12px">'
                             f'+ {len(grupo)-12} técnicas desta escola no catálogo completo (catalogo_jutsus.csv)</div>')

    doc = f'''<!DOCTYPE html><html lang="pt-BR"><head><meta charset="utf-8">
<title>Naruto Anime Mod 26.3 — Página de Conquistas</title></head>
<body style="margin:0;background:#121216;font-family:system-ui,-apple-system,Segoe UI,Roboto,sans-serif">
<div style="max-width:900px;margin:0 auto;padding:24px 18px 60px">
  <div style="background:linear-gradient(135deg,#3a1d0f,#7a2d10 40%,#b34700);border-radius:14px;padding:20px 22px;margin-bottom:6px">
    <div style="color:#ffd9a8;font-size:12px;letter-spacing:3px;text-transform:uppercase">Naruto Anime Mod</div>
    <div style="color:#fff;font-size:26px;font-weight:800;margin:4px 0">Página de Conquistas — Combinações de Jutsus</div>
    <div style="color:#ffe6cc;font-size:13px">Minecraft 26.3 · Forge 66.0.x · Teclas <b>C</b>, <b>V</b> e <b>B</b></div>
  </div>
  <div style="background:#191920;border:1px solid #2c2c36;border-radius:12px;padding:14px 16px;margin:14px 0">
    <div style="color:#fff;font-size:14px;font-weight:700;margin-bottom:6px">Como funciona</div>
    <div style="color:#b9b9c4;font-size:12.5px;line-height:1.65">
      • Os itens de ativação de jutsu (talismãs, pergaminhos, papéis de natureza) foram <b>removidos</b>.<br>
      • Toque as teclas na ordem mostrada — o combo é lido em até ~1,2 s entre as teclas.<br>
      • Jutsus <b>carregados</b> (ex.: Rasengan, Chidori): <b>segure a última tecla</b> para carregar.<br>
      • Todo jutsu desbloqueado aparece na aba de conquistas do jogo com o combo na descrição.
    </div>
  </div>

  <h2 style="color:#ffb347;font-size:17px;margin:22px 0 4px">★ Jutsus assinatura — combos curtos</h2>
  <div style="color:#888;font-size:11px;margin-bottom:8px">{len(assin)} técnicas mais usadas, com atalho direto</div>
  {"".join(card(i) for i in assin)}

  <h2 style="color:#ffb347;font-size:17px;margin:28px 0 4px">Esquema por escola — todos os outros jutsus</h2>
  <div style="color:#888;font-size:11px;margin-bottom:4px">A 1ª tecla dupla identifica a escola; as teclas seguintes, a técnica.
  São 39 slots por escola (3 de 1 tecla, 9 de 2 teclas, 27 de 3 teclas).</div>
  {escolas_html}

  <div style="margin-top:28px;padding:12px 14px;background:#191920;border:1px solid #3a3a45;border-radius:10px;color:#b9b9c4;font-size:12px">
    Catálogo completo: <b>{len(itens)}</b> itens analisados · <b>{sum(1 for i in itens if i["acao"]=="DELETAR")}</b> itens de ativação serão removidos ·
    <b>{sum(1 for i in itens if i["acao"]=="MANTER")}</b> itens de equipamento/consumível permanecem (kunai, shuriken, espadas, armaduras, pílulas de chakra).
  </div>
</div></body></html>'''
    (BASE / "pagina_conquistas.html").write_text(doc, encoding="utf-8")
    print("pagina_conquistas.html gerada")

if __name__ == "__main__":
    main()
