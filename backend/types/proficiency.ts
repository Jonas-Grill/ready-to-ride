export enum Proficiency {
    COMPETITIVE = "Turnierreiter",
    BEGINNER = "Anfänger",
    INTERMEDIATE  = "Fortgeschrittener",
    INTERNAL_ERROR = "500",
}

export const instanceOfProficiency = (object: any): object is Proficiency => {
    return Object.values(Proficiency).includes(object);
}