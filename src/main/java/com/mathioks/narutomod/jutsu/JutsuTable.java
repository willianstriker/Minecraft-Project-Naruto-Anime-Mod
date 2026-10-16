package com.mathioks.narutomod.jutsu;

import java.util.*;

/**
 * Tabela de jutsus: código do combo (C=1, V=2, B=3) -> jutsu.
 * Os 33 jutsus assinatura usam combos curtos; o resto do catálogo usa
 * o esquema por escola (prefixo de 2 teclas + técnica).
 */
public final class JutsuTable {

    public record Jutsu(String id, String combo, String nome, String rank, boolean carga, int custo) {}

    private static final Map<String, Jutsu> POR_COMBO = new LinkedHashMap<>();
    private static final Map<String, Jutsu> POR_ID = new LinkedHashMap<>();

    private static void reg(String combo, String id, String nome, String rank, boolean carga, int custo) {
        Jutsu j = new Jutsu(id, combo, nome, rank, carga, custo);
        POR_COMBO.put(combo, j);
        POR_ID.put(id, j);
    }

    public static void init() {
        if (!POR_COMBO.isEmpty()) return;
        // ---- combos curtos (jutsus assinatura) ----
        reg("1",   "kawarimi",            "Kawarimi no Jutsu",        "E", false,   20);
        reg("2",   "henge",               "Henge no Jutsu",           "E", false,   20);
        reg("3",   "shunshin",            "Shunshin no Jutsu",        "D", false,   40);
        reg("12",  "kage_bunshin",        "Kage Bunshin no Jutsu",    "B", false,  120);
        reg("13",  "taju_kage_bunshin",   "Taju Kage Bunshin",        "A", false,  300);
        reg("21",  "goukakyuu",           "Katon: Goukakyuu",         "C", false,   90);
        reg("22",  "housenka",            "Katon: Housenka",          "C", false,   80);
        reg("23",  "amaterasu",           "Amaterasu",                "S", false,  500);
        reg("31",  "suidan",              "Suiton: Suidan",           "C", false,   70);
        reg("32",  "suirou",              "Suiton: Suirou",           "B", false,   90);
        reg("33",  "bakusui_shouha",      "Suiton: Bakusui Shouha",   "A", false,  220);
        reg("121", "daitoppa",            "Fuuton: Daitoppa",         "C", false,   70);
        reg("122", "rasengan",            "Rasengan",                 "A", true,   250);
        reg("123", "rasenshuriken",       "Rasenshuriken",            "S", true,   600);
        reg("131", "doryuusou",           "Doton: Doryuusou",         "C", false,   70);
        reg("132", "doryuu_jouheki",      "Doton: Doryuu Jouheki",    "C", false,   60);
        reg("211", "chidori",             "Chidori",                  "A", true,   250);
        reg("212", "raikiri",             "Raikiri",                  "A", true,   300);
        reg("213", "kirin",               "Kirin",                    "S", true,   600);
        reg("221", "jukai_koutan",        "Mokuton: Jukai Koutan",    "A", false,  300);
        reg("223", "makyou_hyoushou",     "Hyoton: Makyou Hyoushou",  "A", false,  280);
        reg("231", "shinra_tensei",       "Shinra Tensei",            "S", false,  450);
        reg("232", "bansho_tenin",        "Bansho Ten'in",            "S", false,  400);
        reg("233", "kamui",               "Kamui",                    "S", false,  500);
        reg("311", "sage_mode",           "Sennin Mode",              "S", false,  400);
        reg("312", "bijuu_chakra_mode",   "Bijuu Chakra Mode",        "S", false,  500);
        reg("313", "curse_mark_2",        "Marca Amaldicoada Niv.2",  "A", false,  250);
        reg("321", "byakugan",            "Byakugan",                 "A", false,  180);
        reg("322", "sharingan",           "Sharingan",                "B", false,  150);
        reg("323", "eight_gates",         "Hachimon Tonkou",          "S", false,  450);
        reg("331", "healing_jutsu",       "Iryo: Shosen Jutsu",       "C", false,  100);
        reg("332", "creation_rebirth",    "Souzou Saisei",            "S", false,  550);
        reg("333", "edo_tensei",          "Edo Tensei",               "S", false,  800);
    }

    public static Optional<Jutsu> porCombo(String combo) { return Optional.ofNullable(POR_COMBO.get(combo)); }
    public static Optional<Jutsu> porId(String id) { return Optional.ofNullable(POR_ID.get(id)); }
    public static Collection<Jutsu> todos() { return POR_COMBO.values(); }
    public static int total() { return POR_COMBO.size(); }

    private JutsuTable() {}
}
