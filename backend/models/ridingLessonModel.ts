export interface RidingLessonSchema {
    _id?: Bson.ObjectId;
    trainer?: string;
    booked?: boolean;
    arena: string;
    day: Date;
    startHour: number;
}