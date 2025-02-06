package com.hari.gatherspace.features.avatar;

import com.hari.gatherspace.features.user.User;
import com.hari.gatherspace.features.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvatarService {

    @Autowired
    AvatarRepository avatarRepository;
    @Autowired
    UserService userService;

    public List<Avatar> findAll() {
        return avatarRepository.findAll();
    }

    public List<AvatarUserDto> getAvatarsById(List<String> ids) {
        List<User> users = userService.getUsersById(ids);
        System.out.println(ids.stream().toList());
        List<AvatarUserDto> avatarUserDtos = users.stream().map(
                user -> {
                    if(user.getAvatar() == null)
                        return null;
                    AvatarUserDto dto = new AvatarUserDto(user.getAvatar().getName(), user.getAvatar().getImageUrl(), user.getId(),user.getAvatar().getId());
                    return dto;
                }
        ).filter(dto -> dto!= null).toList();

        return avatarUserDtos;


    }


    public Avatar createAvatar(CreateAvatarRequest request) {
        Avatar avatar = new Avatar();
        avatar.setImageUrl(request.getImageUrl());
        avatar.setName(request.getName());
        return avatarRepository.save(avatar);
    }
}
