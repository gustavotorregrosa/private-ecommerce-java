package dev.torregrosa.app.domains.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
     

    @Autowired
    private final IUserRepository userRepository;


    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserBaseDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new UserBaseDTO(user.getId(), user.getName(), user.getEmail()))
                .orElse(null);
    }

    // public UserBaseDTO getUserById(UUID id) {
    //     return userRepository.findById(id)
    //             .map(user -> new UserBaseDTO(user.getId(), user.getName(), user.getEmail()))
    //             .orElse(null);
    // }

    // public UserBaseDTO createUser(UserCreateDTO userCreateDTO) {
    //     User user = new User(null, userCreateDTO.name, userCreateDTO.email, userCreateDTO.password);
    //     User savedUser = userRepository.save(user);
    //     return new UserBaseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    // }
}
