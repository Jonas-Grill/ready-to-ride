export enum Focus {
    COMPETITIVE = "Turnierreiter",
    BEGINNER = "Anfänger",
    CHILDREN  = "Kinder",
    INTERMEDIATE  = "Fortgeschrittener ",
    ADULTS  = "Erwachsene ",
    INTERNAL_ERROR = "500",
}

export const instanceOfFocus = (object: any): object is Focus => {
    return Object.values(Focus).includes(object);
}