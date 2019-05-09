package person.yang.study.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * @Author: yangyl
 * @Date: 2019/5/9 15:59
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface UseCase {
    public int id();
    public String description() default "no description";
}

class PasswordUtils {
    @UseCase(id = 47)
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }

    @UseCase(id = 48)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 49, description = "New passwords can't equal previously used ones")
    public boolean checkForNewPassword(List<String> prevPasswords,
                                       String password) {
        return !prevPasswords.contains(password);
    }
}

public class AnnotationsTest {
    public static void main(String args[]){
        PasswordUtils passwordUtils = new PasswordUtils();
        System.out.println(passwordUtils.validatePassword("12345"));
    }
}
