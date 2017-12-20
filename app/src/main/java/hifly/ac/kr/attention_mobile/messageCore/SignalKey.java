package hifly.ac.kr.attention_mobile.messageCore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by CYSN on 2017-12-19.
 */

@Setter
@Getter
@ToString
public class SignalKey {
    private String headerProtocol;
    private String bodyData;
}