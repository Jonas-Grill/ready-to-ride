class InvalidDataException extends Error {
    constructor(msg: string) {
        super(msg);

        // Maintains proper stack trace for where our error was thrown (only available on V8)
        if (Error.captureStackTrace) {
            Error.captureStackTrace(this, InvalidDataException)
        }

        this.name = 'InvalidDataException';
    }
}

export default InvalidDataException;