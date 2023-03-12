package donga.merchant.domain.entity.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemCode {

    private String code;
    private String displayName;
}
