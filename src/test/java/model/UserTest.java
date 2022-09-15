package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    @DisplayName("QueryString 에서 User 객체가 정상적으로 생성 된다")
    public void userOfTest() {
        String url = "/user/create?userId=javajigi&password=password&name=hello&email=javajigi%40slipp.net";

        User user = User.userOf(url);

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("hello");
        assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    @DisplayName("User 객체 생성 과정에서 url에 값이 없어도 정상적으로 생성 된다")
    public void userOfWithNullTest() {
        String url = "/user/create?&password=password&userId=javajigi&email=javajigi%40slipp.net";

        User user = User.userOf(url);

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isNull();
        assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");
    }
}
