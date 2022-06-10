export enum Proficiency {
    NONE,
    COMPETITIVE = "Turnierreiter",
    BEGINNER = "Anfänger",
    INTERMEDIATE  = "Fortgeschrittener",
}

export const PROFINCIES = [
    Proficiency.COMPETITIVE,
    Proficiency.BEGINNER,
    Proficiency.INTERMEDIATE,
]

// deno-lint-ignore no-explicit-any
export const instanceOfProficiency = (object: any): object is Proficiency => {
    return Object.values(Proficiency).includes(object);
}