export enum UserRole {
    USER = "User",
    ADMIN = "Admin",
    TRAINER = "Trainer",
}

export const instanceOfUserRole = (object: any): object is UserRole => {
    return Object.values(UserRole).includes(object);
}