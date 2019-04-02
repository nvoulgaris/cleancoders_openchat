package org.openchat.domain.user;

import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private UserIdGenerator userIdGenerator;

    public UserService(UserRepository userRepository, UserIdGenerator userIdGenerator) {
        this.userRepository = userRepository;
        this.userIdGenerator = userIdGenerator;
    }

    public User createUserFrom(RegistrationDto registrationDto) throws UsernameAlreadyInUseException {
        validateUsernameOf(registrationDto);
        User user = userFrom(registrationDto);
        userRepository.save(user);
        return user;
    }

    public List<User> allUsers() {
        return userRepository.all();
    }

    public void createFollowing(Following following) {
        if (userRepository.followingExists(following))
            throw new FollowingAlreadyExistsException();

        userRepository.saveFollowing(following);
    }

    private void validateUsernameOf(RegistrationDto registrationDto) {
        if (userRepository.alreadyInUse(registrationDto.getUsername()))
            throw new UsernameAlreadyInUseException();
    }

    private User userFrom(RegistrationDto registrationDto) {
        return new User(
                userIdGenerator.next(),
                registrationDto.getUsername(),
                registrationDto.getPassword(),
                registrationDto.getAbout()
        );
    }

    public List<User> followeesFor(String followerId) {
        return userRepository.followeesFor(followerId);
    }
}
