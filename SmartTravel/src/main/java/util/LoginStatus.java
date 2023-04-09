package util;

/**
 * 判断是否为登录状态
 */
public class LoginStatus {
    // 默认 false 未登录
    private static boolean login = false;

    /**
     * 获取登录状态
     * @return false 未登录 true 登录
     */
    public static boolean isLogin() {
        return login;
    }

    /**
     * 设置登录状态
     * @param login true 为登录 ， false
     */
    public static void setLogin(boolean login) {
        LoginStatus.login = login;
    }
}
