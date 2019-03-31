package org.openchat.domain.user;

import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private UserIdGenerator userIdGenerator;

    public UserService(UserRepository userRepository, UserIdGenerator userIdGenerator) {
        this.userRepository = userRepository;
        this.userIdGenerator = userIdGenerator;
    }

    public User createFrom(RegistrationDto registrationDto) throws UsernameAlreadyInUseException {
        validateUsernameOf(registrationDto);
        User user = userFrom(registrationDto);
        userRepository.save(user);
        return user;
    }

    public List<User> allUsers() {
        return userRepository.all();
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
}
