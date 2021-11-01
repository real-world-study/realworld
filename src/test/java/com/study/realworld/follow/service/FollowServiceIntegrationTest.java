package com.study.realworld.follow.service;

import com.study.realworld.follow.domain.FollowRepository;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "spring.h2.console.enabled=true")
@Transactional
class FollowServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private EntityManager entityManager;

    private User user;
    private User followee;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        followee = User.Builder()
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

    }

//    @Test
//    @DisplayName("following")
//    void testFollowing() {
//
//        // given
//        user = userRepository.save(user);
//        followee = userRepository.save(followee);
//        entityManager.flush();
//        entityManager.clear();
//        Long userId = user.id();
//        Username username = Username.of("jakefriend");
//
//        // when
//        FollowResponse result = followService.followUser(userId, username);
//
//        // then
//        List<Follow> findAll = followRepository.findAll();
//        System.out.println("result");
//        for (Follow follow : findAll) {
//            System.out.println(follow.follower().username().value());
//            System.out.println(follow.follwee().username().value());
//        }
//    }
//
//    @Test
//    @DisplayName("unfollowing")
//    void testUnfollowing() {
//
//        // given
//        user = userRepository.save(user);
//        followee = userRepository.save(followee);
//        followRepository.save(Follow.builder()
//            .follower(user)
//            .followee(followee)
//            .build());
//        entityManager.flush();
//        entityManager.clear();
//        Long userId = user.id();
//        Username username = Username.of("jakefriend");
//
//        // when
//        followService.unfollowUser(userId, username);
//
//        // then
//        Follow follow = followRepository.findByFollowerAndFollowee(user, followee)
//            .orElseThrow(() -> new RuntimeException("테스트"));
////        List<Follow> findAll = followRepository.findAll();
////        System.out.println("result");
////        for (Follow follow : findAll) {
////            System.out.println(follow.follower().username().value());
////            System.out.println(follow.follwee().username().value());
////            followRepository.delete(follow);
////        }
////
////        entityManager.flush();
////        entityManager.clear();
////        findAll = followRepository.findAll();
////        System.out.println("result2");
////        for (Follow follow : findAll) {
////            System.out.println(follow.follower().username().value());
////            System.out.println(follow.follwee().username().value());
////        }
//    }
}