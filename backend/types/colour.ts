export enum Colour {
    NONE
}

export const Colour = [
    "Red Roan",
    "Blue Roan",
    "Rappe",
    "Fuchs",
    "Palomino",
    "Braun",
    "Schimmel",
    "Schecke"
]

// deno-lint-ignore no-explicit-any
export const instanceOfColour = (object: any): object is Colour => {
    return Colour.includes(object);
}