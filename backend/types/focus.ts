export enum Focus {
    NONE,
    COMPETITIVE = "Turnierreiter",
    BEGINNER = "Anfänger",
    CHILDREN  = "Kinder",
    INTERMEDIATE  = "Fortgeschrittener ",
    ADULTS  = "Erwachsene ",
}

export const FOCUSES = [
    Focus.COMPETITIVE,
    Focus.BEGINNER,
    Focus.CHILDREN,
    Focus.INTERMEDIATE,
    Focus.ADULTS,
]

export const instanceOfFocus = (object: any): object is Focus => {
    return Object.values(Focus).includes(object);
}