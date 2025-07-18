package dev.torregrosa.app.domains.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
     
    @Autowired
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserBaseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }
    
        UserBaseDTO userBaseDTO = new UserBaseDTO();
        userBaseDTO.id = user.getId();
        userBaseDTO.name = user.getName();
        userBaseDTO.email = user.getEmail();

        return userBaseDTO;

    }

     public UserWithHashDTO getUserWithHashDTO(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        return new UserWithHashDTO(user.getId(), user.getName(), user.getEmail(), user.getPasswordHash());
     }

  
    public UserBaseDTO createUser(UserCreateDTO userCreateDTO) {
//        User user = new User(null, userCreateDTO.name, userCreateDTO.email, encodeToBcrypt(userCreateDTO.password));
        User user = new User();
        user.setName(userCreateDTO.name);
        user.setEmail(userCreateDTO.email);
        user.setPasswordHash(encodeToBcrypt(userCreateDTO.password));

        User savedUser = userRepository.save(user);
        if (savedUser == null) {
            throw new RuntimeException("Failed to create user");
        }

        UserBaseDTO userBaseDTO = new UserBaseDTO();
        userBaseDTO.id = savedUser.getId();
        userBaseDTO.name = savedUser.getName();
        userBaseDTO.email = savedUser.getEmail();
        
        return userBaseDTO;
    }

    private String encodeToBcrypt(String input) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(input);
    }
}
