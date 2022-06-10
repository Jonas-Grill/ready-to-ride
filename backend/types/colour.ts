export enum Colour {
    NONE
}

export const COLOURS = [
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
    return COLOURS.includes(object);
}