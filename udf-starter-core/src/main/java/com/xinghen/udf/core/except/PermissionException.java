package com.xinghen.udf.core.except;

public class PermissionException extends RuntimeException {

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
    public PermissionException(String message) {
        super(message);
    }

    /**

     * <p>

     * Description: 构造函数

     * </p>

     *

     * @param cause 堆栈

     */
    public PermissionException(Throwable cause) {
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
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

}
