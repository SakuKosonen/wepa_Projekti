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
    
    

    // private Picture profilePicture;
    // private List<Picture> Pictures;
    
    private List<String> friends = new ArrayList();

    @OneToMany(mappedBy = "sentTo")
    private List<Request> sentRequests = new ArrayList();

    @OneToMany(mappedBy = "sentFrom")
    private List<Request> receivedRequests = new ArrayList();
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        
    } 
    public void addFriend(String username) {
        friends.add(username);
    }

    public void removeFriend(String username) {
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
}
