package game;

import model.*;

public class BenchmarkSearch {

    private static long usedMemoryKb() {
        Runtime rt = Runtime.getRuntime();
        return (rt.totalMemory() - rt.freeMemory()) / 1024;
    }

    public static void main(String[] args) {
        PokemonTeam playerTeam = new PokemonTeam();
        playerTeam.addPokemon(PokemonData.pokemonGreninja());
        playerTeam.addPokemon(PokemonData.pokemonRayquaza());
        playerTeam.addPokemon(PokemonData.pokemonScizor());
        playerTeam.addPokemon(PokemonData.pokemonArceus());

        PokemonTeam aiTeam = new PokemonTeam();
        aiTeam.addPokemon(PokemonData.pokemonCharizard());
        aiTeam.addPokemon(PokemonData.pokemonGiratina());
        aiTeam.addPokemon(PokemonData.pokemonHaxorus());
        aiTeam.addPokemon(PokemonData.pokemonJirachi());

        Pokemon player = playerTeam.getCurrentPokemon();
        Pokemon ai = aiTeam.getCurrentPokemon();

        System.out.println("Depth,Minimax_MemKB,Minimax_TimeMs,AlphaBeta_MemKB,AlphaBeta_TimeMs");
        for (int depth = 1; depth <= 10; depth++) {
            System.gc();
            long memBefore = usedMemoryKb();
            long t0 = System.nanoTime();
            AI.evaluateMinimaxValue(ai, player, depth);
            long minimaxMs = (System.nanoTime() - t0) / 1_000_000;
            long minimaxMem = usedMemoryKb();

            System.gc();
            long memBeforeAb = usedMemoryKb();
            long t1 = System.nanoTime();
            AI.evaluateAlphaBetaValue(ai, player, depth);
            long alphaBetaMs = (System.nanoTime() - t1) / 1_000_000;
            long alphaBetaMem = usedMemoryKb();

            System.out.printf("%d,%d,%d,%d,%d%n", depth, minimaxMem, minimaxMs, alphaBetaMem, alphaBetaMs);
        }
    }
}
