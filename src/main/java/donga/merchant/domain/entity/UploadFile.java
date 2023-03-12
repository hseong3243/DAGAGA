package donga.merchant.domain;

import donga.merchant.domain.entity.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class UploadFile {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "uploadFile_id")
    private Long id;

    private String uploadFileName;
    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile() {

    }
}
