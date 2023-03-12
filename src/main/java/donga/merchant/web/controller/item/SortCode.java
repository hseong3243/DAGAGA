package donga.merchant.web.controller.item;

import lombok.Data;

@Data
public class SortCode {
    private String direction;
    private String code;

    public SortCode(String direction, String code) {
        this.direction = direction;
        this.code = code;
    }
}
