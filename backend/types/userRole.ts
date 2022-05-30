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

export const instanceOfUserRole = (object: UserRole): object is UserRole => {
    return Object.values(UserRole).includes(object);
}