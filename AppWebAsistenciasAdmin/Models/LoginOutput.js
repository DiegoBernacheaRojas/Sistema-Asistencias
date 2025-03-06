class LoginOutput {
    constructor(success, message, token = '') {
        this.success = success;
        this.message = message;
        this.token = token;
    }
}

export default LoginOutput;