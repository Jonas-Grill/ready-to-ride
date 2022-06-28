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

// Get date range from possible undefined parameters
export function getDateRange(fromDate?: string, toDate?: string): { fromDate: string, toDate: string } {
    // Define the date range
    if (!fromDate && !toDate) {
        fromDate = getCurrentDate();
        toDate = addDays(7, fromDate);
    } else if (!fromDate) {
        fromDate = addDays(-7, toDate);
    }
    if (!toDate) { /* No else if because if it is an else if deno thinks that toDate could be undefined */
        toDate = addDays(7, fromDate);
    }

    return {
        fromDate,
        toDate,
    }
}