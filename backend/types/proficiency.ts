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

export const instanceOfProficiency = (object: any): object is Proficiency => {
    return Object.values(Proficiency).includes(object);
}