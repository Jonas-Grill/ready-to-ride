export enum UserRole {
    NONE,
    USER = "User",
    ADMIN = "Admin",
    TRAINER = "Trainer",
}

export const USER_ROLES = [
    UserRole.USER,
    UserRole.ADMIN,
    UserRole.TRAINER,
]

// deno-lint-ignore no-explicit-any
export const instanceOfUserRole = (object: any): object is UserRole => {
    return Object.values(UserRole).includes(object);
}