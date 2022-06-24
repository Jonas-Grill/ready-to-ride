// Returns a string representation in the format YYYY-MM-DD of the current date
export function getCurrentDate() {
    return formatDate(new Date());
}

// Add days to a date
export function addDays(numOfDays: number, date: Date | string = new Date()): string {
    if (typeof date === "string") {
        date = new Date(date);
    }

    date.setDate(date.getDate() + numOfDays);

    return formatDate(date);
}

// Format a number to a string with a leading zero if necessary
function padTo2Digits(num: number) {
    return num.toString().padStart(2, '0');
}

// Format a date to a string in the format YYYY-MM-DD
export function formatDate(date: Date) {
    return `${date.getFullYear()}-${padTo2Digits(date.getMonth() + 1)}-${padTo2Digits(date.getDate())}`;
}