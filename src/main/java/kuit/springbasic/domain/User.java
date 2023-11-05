package kuit.springbasic.domain;

import lombok.*;

// lombok annotatino으로 많은 것으 자동 생성
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 전체 생성자 자동 생성
@ToString
public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
    }

    public boolean matchPassword(String password) {
        if (password == null) {
            return false;
        }

        return this.password.equals(password);
    }

    public boolean isSameUser(User user) {
        return isSameUser(user.getUserId(),user.getPassword());
    }

    public boolean isSameUser(String userId, String password) {
        return userId.equals(this.userId) && matchPassword(password);
    }
}
