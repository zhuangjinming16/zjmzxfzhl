export default class CustomError extends Error {
    constructor(message) {
        super(message)
        this.name = 'CustomError',
            // 这一步可不写，默认会保存堆栈追踪信息到自定义错误构造函数之前，
            // 而如果写成 `Error.captureStackTrace(this)` 则自定义错误的构造函数也会被保存到堆栈追踪信息
            Error.captureStackTrace(this, this.constructor)
    }
}