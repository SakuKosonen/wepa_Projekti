/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {

    private String username;
    //  private String jotain;
    private String password;
    
    @OneToMany(mappedBy = "account")
    private List<wallText> wallTexts;
    
    @OneToMany(mappedBy = "owner")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "theFriend")
    private List<Friend> friends = new ArrayList();

    @OneToMany(mappedBy = "sentTo")
    private List<Request> sentRequests = new ArrayList();

    @OneToMany(mappedBy = "sentFrom")
    private List<Request> receivedRequests = new ArrayList();

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.friends = new ArrayList();
    }

    public void addFriend(Friend username) {
        friends.add(username);
    }

    public void removeFriend(Friend username) {
        friends.remove(username);
    }

    public void addSentFriendRequest(Request request) {
        sentRequests.add(request);
    }

    public void removeSentFriendRequest(Request request) {
        sentRequests.remove(request);
    }

    public void addReceivedFriendRequest(Request request) {
        receivedRequests.add(request);
    }

    public void removeReceivedFriendRequest(Request request) {
        receivedRequests.remove(request);
    }

    public Picture getProfilePicture() {
        if (pictures.isEmpty()) {
            return new Picture();
        }
        for (Picture picture : pictures) {
            if (picture.getIsProfilePicture()) {
                return picture;
            }
        }
        return pictures.get(0);
    }

    public boolean areFriends(Account account1) {
        boolean eka = false;
        boolean toka = false;
        for (Friend friend : friends) {
            if (friend.getFriendUsername().equals(account1.username)) {
                eka = true;
            }
        }
        for (Friend friend : account1.getFriends()) {
            if (friend.getFriendUsername().equals(username)) {
                toka = true;
            }
        }
        if (toka && eka) {
            return true;
        } else {
            return false;
        }
    }
}
