package com.hari.gatherspace.service;

import com.hari.gatherspace.dto.CreateAvatarRequest;
import com.hari.gatherspace.model.Avatar;
import com.hari.gatherspace.model.User;
import com.hari.gatherspace.repository.AvatarRepository;
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

    public List<Avatar> getAvatarsById(List<String> ids) {
        List<User> users = userService.getUsersById(ids);

        List<String> avatarIds = users.stream().map(User::getAvatarId).toList();
        return avatarRepository.findAllById(avatarIds);
    }


    public Avatar createAvatar(CreateAvatarRequest request) {
        Avatar avatar = new Avatar();
        avatar.setImageUrl(request.getImageUrl());
        avatar.setName(request.getName());
        return avatarRepository.save(avatar);
    }
}
