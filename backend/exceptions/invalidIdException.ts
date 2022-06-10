class InvalidIdException extends Error {
    constructor() {
        super("Please provide a valid id")

        // Maintains proper stack trace for where our error was thrown (only available on V8)
        if (Error.captureStackTrace) {
            Error.captureStackTrace(this, InvalidIdException)
        }

        this.name = 'InvalidIdException'
    }
}

export default InvalidIdException;