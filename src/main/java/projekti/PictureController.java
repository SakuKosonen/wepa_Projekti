/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PictureController {

    @Autowired
    PictureRepository pictureRepo;

    @Autowired
    AccountRepository accountRepo;

    @GetMapping("/pictures")
    public String list(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepo.findByUsername(username);

        model.addAttribute("pictures", account.getPictures());
        model.addAttribute("user", username);
        return "pictures";
    }

    @PostMapping("/pictures")
    public String save(@RequestParam("file") MultipartFile file, @RequestParam("description") String description) throws IOException {
        if (file.getContentType().equals("image/png")) {
            Picture picture = new Picture();
            picture.setContent(file.getBytes());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            Account account = accountRepo.findByUsername(username);
            picture.setOwner(account);
            picture.setIsProfilePicture(Boolean.FALSE);
            picture.setDescription(description);
            picture.setLikes(0);

            pictureRepo.save(picture);
        }
        return "redirect:/pictures";
    }

    @GetMapping(path = "/gifs/{id}/content", produces = "image/png")
    @ResponseBody
    public byte[] get(@PathVariable Picture id) {
        return id.getContent();

    }

}
