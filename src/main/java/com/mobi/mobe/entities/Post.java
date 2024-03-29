package com.mobi.mobe.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mobi.mobe.enums.TypePost;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor @ToString
@Getter
@Setter
@Builder
public class Post {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated
    @Column(name = "type_post",nullable = false)
    private TypePost typePost;

//    @NotNull
    @Column(name = "url_content",nullable = false)
    private String urlContent;

    @Column(name = "content", updatable = false, nullable = false)
    private String content;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference("post-comment")
    private Set<Comment> comments;

    @ManyToOne
    @JsonBackReference // Add this annotation to prevent infinite loop
   // private User user;
    //@JsonBackReference("user-post")
    private User user;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    @JsonManagedReference("post-post_report")
    private Set<PostReport> postReports;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    private Set<Like> likes ;
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", typePost=" + typePost +
                ", urlContent='" + urlContent + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ",likes=" + likes +
                ",users=" + user +
                '}';
    }
}
