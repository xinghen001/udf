package com.xinghen.udf.core.except;

public class SystemRuntimeException extends RuntimeException {

    /**

     * <p>

     * Field serialVersionUID: 序列号

     * </p>

     */
    private static final long serialVersionUID = 1L;

    /**

     * <p>

     * Description: 构造函数

     * </p>

     *

     * @param message 异常信息

     */
    public SystemRuntimeException(String message) {
        super(message);
    }

    /**

     * <p>

     * Description: 构造函数

     * </p>

     *

     * @param cause 堆栈

     */
    public SystemRuntimeException(Throwable cause) {
        super(cause);
    }

    /**

     * <p>

     * Description: 构造函数

     * </p>

     *

     * @param message 异常信息

     * @param cause   堆栈

     */
    public SystemRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
