export enum Race {
    NONE
}

export const Races = [
    "Quarterhorse",
    "Deutsches Reitpony",
    "Friesenpferde, Haflinger",
    "Hannoveraner",
    "Holsteiner Pferde",
    "Islandpferde",
    "Oldenburger",
    "Pura Raza Espanola"
]

// deno-lint-ignore no-explicit-any
export const instanceOfRace = (object: any): object is Race => {
    return Races.includes(object);
}